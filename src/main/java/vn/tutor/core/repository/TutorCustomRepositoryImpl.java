package vn.tutor.core.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.tutor.core.entity.Specialty_;
import vn.tutor.core.entity.Tutor;
import vn.tutor.core.entity.TutorSpecialty;
import vn.tutor.core.entity.TutorSpecialty_;
import vn.tutor.core.entity.Tutor_;

@Repository
@RequiredArgsConstructor
public class TutorCustomRepositoryImpl implements TutorCustomRepository {
  private final EntityManager entityManager;

  @Override
  public Page<Tutor> findTutorBySpecialtyIdsAndAddresses(List<String> specialtyIds, List<String> addresses, int pageNum,
                                                         int pageSize) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    // Use a new root for each query (including sub query)
    CriteriaQuery<Tutor> retrievalQuery = cb.createQuery(Tutor.class);
    Root<Tutor> retrievalRoot = retrievalQuery.from(Tutor.class);
    Predicate retrievalPredicate = null;

    CriteriaQuery<Long> countingQuery = cb.createQuery(Long.class);
    Root<Tutor> countingRoot = countingQuery.from(Tutor.class);
    Predicate countingPredicate = null;

    if (!addresses.isEmpty()) {
      List<Predicate> addressPredicatesForRetrieval = new ArrayList<>();
      List<Predicate> addressPredicatesForCounting = new ArrayList<>();
      for (String address : addresses) {
        addressPredicatesForRetrieval.add(cb.like(retrievalRoot.get(Tutor_.address), address + "%"));
        addressPredicatesForRetrieval.add(cb.like(retrievalRoot.get(Tutor_.address), "% " + address + "%"));

        addressPredicatesForCounting.add(cb.like(countingRoot.get(Tutor_.address), address + "%"));
        addressPredicatesForCounting.add(cb.like(countingRoot.get(Tutor_.address), "% " + address + "%"));
      }
      retrievalPredicate = cb.or(addressPredicatesForRetrieval.toArray(new Predicate[0]));
      countingPredicate = cb.or(addressPredicatesForCounting.toArray(new Predicate[0]));
    }

    if (!specialtyIds.isEmpty()) {
      Predicate retrievalSpecialtiesPredicate = retrievalRoot.join(Tutor_.tutorSpecialties, JoinType.LEFT)
          .join(TutorSpecialty_.specialty)
          .get(Specialty_.id)
          .in(specialtyIds);
      retrievalPredicate = retrievalPredicate == null ? retrievalSpecialtiesPredicate
          : cb.and(retrievalPredicate, retrievalSpecialtiesPredicate);

      Predicate countingSpecialtiesPredicate = countingRoot.join(Tutor_.tutorSpecialties, JoinType.LEFT)
          .join(TutorSpecialty_.specialty, JoinType.INNER)
          .get(Specialty_.id)
          .in(specialtyIds);
      countingPredicate = countingPredicate == null ? countingSpecialtiesPredicate
          : cb.and(countingPredicate, countingSpecialtiesPredicate);
    }

    retrievalQuery.where(retrievalPredicate);
    // Use entity graph to eagerly fetch related entities, join in where clause have no effect to fetching
    // Eagerly fetch to avoid (n+1) queries problem
    // Target a relation with both Root.fetch and Root.join in where clause results into duplication of join in the generated query
    EntityGraph<Tutor> entityGraph = entityManager.createEntityGraph(Tutor.class);
    Subgraph<TutorSpecialty> tutorSpecialtySubgraph = entityGraph.addSubgraph("tutorSpecialties");
    tutorSpecialtySubgraph.addSubgraph("specialty");
    List<Tutor> tutors = entityManager.createQuery(retrievalQuery)
        .setHint("jakarta.persistence.loadgraph", entityGraph)
        .setFirstResult(pageNum * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
    
    countingQuery.select(cb.countDistinct(countingRoot))
        .where(countingPredicate);
    long count = entityManager.createQuery(countingQuery).getSingleResult();

    return new PageImpl<>(tutors, PageRequest.of(pageNum, pageSize), count);
  }
}

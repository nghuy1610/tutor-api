package unit.vn.tutor.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.tutor.core.TutorCoreApplication;

@SpringBootTest(classes = TutorCoreApplication.class)
class TutorCoreApplicationTests {

  @Test
  void contextLoads() {
    assertThat(true).isTrue();
  }

}

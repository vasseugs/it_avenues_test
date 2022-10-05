package com.itavenues.initializer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource("classpath:/application-test.properties")
@ContextConfiguration(initializers = {
    PostgresInitializer.Initializer.class
})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public abstract class AbstractTestContainers {

  @Autowired
  protected TestRestTemplate testRestTemplate;

  @BeforeAll
  public static void init() {
    PostgresInitializer.container.start();
  }
}

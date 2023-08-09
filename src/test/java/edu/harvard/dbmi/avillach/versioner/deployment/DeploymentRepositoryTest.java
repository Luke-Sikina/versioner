package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.enviroment.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@Sql(scripts = {"/seed.sql"})
class DeploymentRepositoryTest {
    @Container
    static final MySQLContainer<?> databaseContainer =
        new MySQLContainer<>("mysql:8").withReuse(true);

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", databaseContainer::getJdbcUrl);
        registry.add("spring.datasource.username", databaseContainer::getUsername);
        registry.add("spring.datasource.password", databaseContainer::getPassword);
        registry.add("spring.datasource.db", databaseContainer::getDatabaseName);
    }

    @Autowired
    DeploymentRepository subject;

    @Test
    void getAllDeploymentsForEnvironment() {
        List<Deployment> actual =
            subject.getAllDeploymentsForEnvironment(new Environment("GIC BCH Dev", "", null, ""));
        List<Deployment> expected = List.of(
            new Deployment(2, "GIC BCH Dev", "My Cool Release 2", LocalDateTime.of(2022, 1, 2, 0, 0), 1),
            new Deployment(1, "GIC BCH Dev", "My Cool Release 1", LocalDateTime.of(2022, 1, 1, 0, 0), -1)
        );

        Assertions.assertEquals(expected, actual);
    }
}
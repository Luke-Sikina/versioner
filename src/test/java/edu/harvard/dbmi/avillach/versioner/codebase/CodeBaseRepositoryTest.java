package edu.harvard.dbmi.avillach.versioner.codebase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@Sql(scripts = {"/seed.sql"})
class CodeBaseRepositoryTest {
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
    CodeBaseRepository subject;

    @Test
    void shouldCreateCodeBases() {
        List<CodeBase> codeBases = List.of(
            new CodeBase(1, "PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"),
            new CodeBase(2, "HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO"),
            // auto increment is weird. it increments for the first two failed inserts
            new CodeBase(5, "pic-sure-gic-institution", "https://github.com/hms-dbmi/pic-sure-gic-institution-frontend", "HUH"),
            new CodeBase(6, "pic-sure-gic", "https://github.com/hms-dbmi/pic-sure-gic-common-frontend", "WOO")
        );
        List<CodeBase> actual = subject.createCodeBases(codeBases);

        Assertions.assertEquals(codeBases, actual);
    }
}
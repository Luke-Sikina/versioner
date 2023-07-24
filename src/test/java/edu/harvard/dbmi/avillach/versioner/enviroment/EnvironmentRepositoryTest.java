package edu.harvard.dbmi.avillach.versioner.enviroment;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@SpringBootTest
@Sql(scripts = {"/seed.sql"})
class EnvironmentRepositoryTest {

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
    EnvironmentRepository subject;

    @Test
    void shouldGetEnvironment() {
        Optional<Environment> actual = subject.getEnvironment("GIC BCH Dev");

        List<CodeBase> codeBases = List.of(
            new CodeBase("PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"),
            new CodeBase("HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO")
        );

        Environment expected = new Environment(
            "GIC BCH Dev", "gic-bch-dev.pl.hms.harvard.edu", new VPN("MY cool VPN", "foo.com", "Me"), codeBases, "{}"
        );

        Assertions.assertEquals(Optional.of(expected), actual);
    }

    @Test
    void shouldNotGetEnvironment() {
        Optional<Environment> actual = subject.getEnvironment("Made up environment");
        Optional<Environment> expected = Optional.empty();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllEnvironments() {
        List<Environment> expected = List.of(
            new Environment("GIC BCH Dev", "gic-bch-dev.pl.hms.harvard.edu", new VPN("MY cool VPN", "foo.com", "Me"), "{}"),
            new Environment("GIC Common Area DEv", "gic.hms.harvard.edu", new VPN("MY cool VPN", "foo.com", "Me"), "{}")
        );

        List<Environment> actual = subject.getAllEnvironments();

        Assertions.assertEquals(expected, actual);
    }
}
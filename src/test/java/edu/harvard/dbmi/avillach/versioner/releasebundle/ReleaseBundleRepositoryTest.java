package edu.harvard.dbmi.avillach.versioner.releasebundle;

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

import java.time.LocalDateTime;
import java.util.List;

@Testcontainers
@SpringBootTest
@Sql(scripts = {"/seed.sql"})
class ReleaseBundleRepositoryTest {

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
    ReleaseBundleRepository subject;

    @Test
    void shouldGetAllReleaseBundles() {
        List<ReleaseBundle> actual = subject.getAllReleaseBundles();
        List<ReleaseBundle> expected = List.of(
            new ReleaseBundle(2, "My Cool Release 2", LocalDateTime.of(2023, 7, 2, 0, 0),
                List.of(
                    new ReleasePart(new CodeBase("HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO"), "v2.0.1"),
                    new ReleasePart(new CodeBase("PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"), "v2.0.1")
                )
            ),
            new ReleaseBundle(1, "My Cool Release 1", LocalDateTime.of(2023, 7, 1, 0, 0),
                List.of(
                    new ReleasePart(new CodeBase("HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO"), "v2.0.0"),
                    new ReleasePart(new CodeBase("PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"), "v2.0.0")
                )
            )
        );

        Assertions.assertEquals(expected, actual);
    }
}
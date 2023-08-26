package edu.harvard.dbmi.avillach.versioner.releasebundle.part;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundle;
import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundleRepository;
import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundleStatus;
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
import java.util.Optional;

@Testcontainers
@SpringBootTest
@Sql(scripts = {"/seed.sql"})
class ReleaseBundlePartRepositoryTest {
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
    ReleaseBundlePartRepository subject;

    @Autowired
    ReleaseBundleRepository releaseBundleRepository;

    @Test
    void shouldCreateReleasePartsForBundle() {
        LocalDateTime now = LocalDateTime.now();
        releaseBundleRepository.createEmptyBundle(new ReleaseBundle(3, ReleaseBundleStatus.Development, "idk", now, now, List.of()));
        List<ReleasePart> parts = List.of(
            new ReleasePart(new CodeBase(1, "PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"), "v2.1.1"));

        subject.createReleasePartsForBundle(3, parts);

        Optional<ReleaseBundle> releaseBundle = releaseBundleRepository.getReleaseBundle(3);

        Assertions.assertTrue(releaseBundle.isPresent());
        Assertions.assertEquals(releaseBundle.get().parts(), parts);
    }
}
package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ReleaseBundleControllerTest {

    @Mock
    ReleaseBundleService service;

    @InjectMocks
    ReleaseBundleController subject;

    @Test
    void shouldGetAllReleaseBundles() {
        List<ReleaseBundle> bundles = List.of(
            new ReleaseBundle("My Cool Release", LocalDateTime.of(2023, 7, 2, 0, 0),
                List.of(
                    new ReleasePart(new CodeBase("HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO"), "v2.0.1"),
                    new ReleasePart(new CodeBase("PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"), "v2.0.1")
                )
            ),
            new ReleaseBundle("My Cool Release", LocalDateTime.of(2023, 7, 1, 0, 0),
                List.of(
                    new ReleasePart(new CodeBase("HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO"), "v2.0.0"),
                    new ReleasePart(new CodeBase("PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"), "v2.0.0")
                )
            )
        );
        Mockito.when(service.getAllReleaseBundles())
            .thenReturn(bundles);

        ResponseEntity<List<ReleaseBundle>> actual = subject.getAllReleaseBundles();

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(actual.getBody(), actual.getBody());
    }
}
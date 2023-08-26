package edu.harvard.dbmi.avillach.versioner.enviroment;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class EnvironmentControllerTest {

    @Mock
    EnvironmentService environmentService;

    @InjectMocks
    EnvironmentController environmentController;

    @Test
    void shouldGetEnvironment() {
        List<CodeBase> codeBases = List.of(
            new CodeBase(1, "PIC-SURE", "https://github.com/hms-dbmi/pic-sure", "IDK"),
            new CodeBase(2, "HPDS", "https://github.com/hms-dbmi/pic-sure-hpds", "WOO")
        );
        Environment expected = new Environment(
            "GIC BCH Dev", "gic-bch-dev.pl.hms.harvard.edu", new VPN("MY cool VPN", "foo.com", "Me"), codeBases, "{}"
        );

        Mockito.when(environmentService.getEnvironment("GIC BCH Dev"))
            .thenReturn(Optional.of(expected));

        ResponseEntity<Environment> actual = environmentController.getEnvironmentByName("GIC BCH Dev");

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(expected, actual.getBody());
    }

    @Test
    void shouldGetAllEnvironments() {
        Environment expected =
            new Environment("GIC BCH Dev", "gic-bch-dev.pl.hms.harvard.edu", new VPN("MY cool VPN", "foo.com", "Me"), "{}");

        Mockito.when(environmentService.getAllEnvironments())
            .thenReturn(List.of(expected));

        ResponseEntity<List<Environment>> actual = environmentController.getAllEnvironments();

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(List.of(expected), actual.getBody());
    }
}
package edu.harvard.dbmi.avillach.versioner.enviroment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class EnvironmentServiceTest {

    @Mock
    EnvironmentRepository repository;

    @InjectMocks
    EnvironmentService subject;

    @Test
    void shouldGetEnvironment() {
        Environment env = new Environment("GIC Common Area Dev", "foo.com", new VPN("none", "none", "none"), "{}");
        Mockito.when(repository.getEnvironment("GIC Common Area Dev"))
            .thenReturn(Optional.of(env));

        Optional<Environment> actual = subject.getEnvironment("GIC Common Area Dev");

        Assertions.assertEquals(Optional.of(env), actual);
    }

    @Test
    void shouldGetAllEnvironments() {
        Environment env = new Environment("GIC Common Area Dev", "foo.com", new VPN("none", "none", "none"), "{}");
        Mockito.when(repository.getAllEnvironments())
            .thenReturn(List.of(env));

        List<Environment> actual = subject.getAllEnvironments();

        Assertions.assertEquals(List.of(env), actual);
    }
}
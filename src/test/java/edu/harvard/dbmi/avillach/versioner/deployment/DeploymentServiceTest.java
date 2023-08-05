package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.enviroment.Environment;
import edu.harvard.dbmi.avillach.versioner.enviroment.EnvironmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DeploymentServiceTest {

    @Mock
    EnvironmentService environmentService;

    @Mock
    DeploymentRepository repository;

    @InjectMocks
    DeploymentService deploymentService;

    @Test
    void shouldGetDeploymentsForEnv() {
        Environment env = new Environment("env", "", null, "");
        Deployment deployment = new Deployment(1, "env", "release", LocalDateTime.now(), 1);
        Mockito.when(environmentService.getEnvironment("env"))
            .thenReturn(Optional.of(env));
        Mockito.when(repository.getAllDeploymentsForEnvironment(env))
            .thenReturn(List.of(deployment));

        Optional<List<Deployment>> actual = deploymentService.getAllDeploymentsForEnvironment("env");
        Optional<List<Deployment>> expected = Optional.of(List.of(deployment));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotGetDeploymentsForEnvThatDNE() {
        Mockito.when(environmentService.getEnvironment("env"))
            .thenReturn(Optional.empty());

        Optional<List<Deployment>> actual = deploymentService.getAllDeploymentsForEnvironment("env");
        Optional<List<Deployment>> expected = Optional.empty();

        Assertions.assertEquals(expected, actual);
    }
}
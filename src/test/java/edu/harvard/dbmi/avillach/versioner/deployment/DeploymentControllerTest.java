package edu.harvard.dbmi.avillach.versioner.deployment;

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
import java.util.Optional;

@SpringBootTest
class DeploymentControllerTest {

    @Mock
    DeploymentService deploymentService;

    @InjectMocks
    DeploymentController subject;

    @Test
    void shouldReturnDeployments() {
        List<Deployment> deployments = List.of(
            new Deployment(2, "GIC BCH Dev", "My Cool Release 2", LocalDateTime.of(2022, 1, 2, 0, 0), 1),
            new Deployment(1, "GIC BCH Dev", "My Cool Release 1", LocalDateTime.of(2022, 1, 1, 0, 0), -1)
        );
        Mockito.when(deploymentService.getAllDeploymentsForEnvironment("GIC BCH Dev"))
            .thenReturn(Optional.of(deployments));

        ResponseEntity<List<Deployment>> actual = subject.getAllDeploymentsForEnvironment("GIC BCH Dev");

        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(deployments, actual.getBody());
    }

    @Test
    void should404() {
        Mockito.when(deploymentService.getAllDeploymentsForEnvironment("GIC BCH Dev"))
            .thenReturn(Optional.empty());

        ResponseEntity<List<Deployment>> actual = subject.getAllDeploymentsForEnvironment("GIC BCH Dev");

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }
}
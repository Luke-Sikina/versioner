package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.enviroment.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeploymentService {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private DeploymentRepository repository;

    public Optional<List<Deployment>> getAllDeploymentsForEnvironment(String environmentName) {
        return environmentService.getEnvironment(environmentName)
            .map(repository::getAllDeploymentsForEnvironment);
    }
}

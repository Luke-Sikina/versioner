package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.config.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@ApiController
public class DeploymentController {
    @Autowired
    private DeploymentService service;

    @GetMapping("/deployment/environment/{environmentName}")
    public ResponseEntity<List<Deployment>> getAllDeploymentsForEnvironment(
        @PathVariable("environmentName")
        String environmentName
    ) {
        return service.getAllDeploymentsForEnvironment(environmentName)
            .map(deployments -> new ResponseEntity<>(deployments, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

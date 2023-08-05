package edu.harvard.dbmi.avillach.versioner.deployment;

import java.time.LocalDateTime;

public record Deployment(int deploymentNum, String environmentName, String releaseBundleName, LocalDateTime deploymentDate, int previousDeploymentNum) {
}

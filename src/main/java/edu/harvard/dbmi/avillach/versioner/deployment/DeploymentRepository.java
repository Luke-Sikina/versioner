package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.enviroment.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeploymentRepository {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private DeploymentMapper mapper;

    public List<Deployment> getAllDeploymentsForEnvironment(Environment environment) {
        String sql = """
            SELECT
                deployment.DEPLOYMENT_ID,
                deployment.DEPLOYMENT_DATE,
                coalesce(deployment.PREVIOUS_DEPLOYMENT_ID, -1) as PREVIOUS_DEPLOYMENT_ID,
                release_bundle.TITLE as RELEASE_TITLE,
                environment.NAME as ENVIRONMENT_NAME
            FROM
                deployment
                LEFT JOIN release_bundle ON deployment.RELEASE_BUNDLE_ID = release_bundle.RELEASE_BUNDLE_ID
                LEFT JOIN environment ON deployment.ENVIRONMENT_ID = environment.ENVIRONMENT_ID
            WHERE
                environment.NAME = ?
            ORDER BY DEPLOYMENT_DATE DESC
            """;

        return template.query(sql, mapper, environment.name());
    }
}

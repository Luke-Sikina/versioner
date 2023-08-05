package edu.harvard.dbmi.avillach.versioner.deployment;

import edu.harvard.dbmi.avillach.versioner.util.DateTimeUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeploymentMapper implements RowMapper<Deployment> {
    @Override
    public Deployment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Deployment(
            rs.getInt("DEPLOYMENT_ID"),
            rs.getString("ENVIRONMENT_NAME"),
            rs.getString("RELEASE_TITLE"),
            DateTimeUtil.fromSQL(rs.getDate("DEPLOYMENT_DATE")),
            rs.getInt("PREVIOUS_DEPLOYMENT_ID")
        );
    }
}

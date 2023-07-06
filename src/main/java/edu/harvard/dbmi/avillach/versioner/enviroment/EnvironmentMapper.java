package edu.harvard.dbmi.avillach.versioner.enviroment;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class EnvironmentMapper implements RowMapper<Environment> {
    @Override
    public Environment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Environment(
            rs.getString("NAME"), rs.getString("URL"),
            new VPN(rs.getString("VPN_NAME"), rs.getString("VPN_URL"), rs.getString("VPN_ORGANIZATION")),
            List.of()
        );
    }
}

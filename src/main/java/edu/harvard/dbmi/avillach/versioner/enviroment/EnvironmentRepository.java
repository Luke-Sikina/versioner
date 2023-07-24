package edu.harvard.dbmi.avillach.versioner.enviroment;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBaseService;
import edu.harvard.dbmi.avillach.versioner.config.OptionalJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnvironmentRepository {

    @Autowired
    OptionalJdbcTemplate template;

    @Autowired
    JdbcTemplate unsafeTemplate;

    @Autowired
    EnvironmentMapper mapper;

    @Autowired
    CodeBaseService codeBaseService;


    public Optional<Environment> getEnvironment(String name) {
        var sql = """
            SELECT
                environment.NAME,
                environment.DOMAIN,
                environment.EXTRA_FIELDS,
                vpn.NAME as VPN_NAME,
                vpn.URL as VPN_URL,
                vpn.ORGANIZATION as VPN_ORGANIZATION
            FROM
                environment
                LEFT JOIN vpn on environment.VPN_ID = vpn.VPN_ID
            WHERE
                environment.NAME = ?
            """;

        return template.queryForObject(sql, mapper, name)
            .map(e -> new Environment(e, codeBaseService.getCodeBasesForEnvironment(name)));
    }

    public List<Environment> getAllEnvironments() {
        var sql = """
            SELECT
                environment.NAME,
                environment.DOMAIN,
                environment.EXTRA_FIELDS,
                vpn.NAME as VPN_NAME,
                vpn.URL as VPN_URL,
                vpn.ORGANIZATION as VPN_ORGANIZATION
            FROM
                environment
                LEFT JOIN vpn on environment.VPN_ID = vpn.VPN_ID
            """;

        return unsafeTemplate.query(sql, mapper);
    }
}

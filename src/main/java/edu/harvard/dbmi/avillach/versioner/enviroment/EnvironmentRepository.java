package edu.harvard.dbmi.avillach.versioner.enviroment;

import com.sikina.recordtransformer.RecordTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnvironmentRepository {

    @Autowired
    JdbcTemplate template;

    @Autowired
    EnvironmentMapper mapper;

    @Autowired
    CodeBaseMapper codeBaseMapper;


    public Optional<Environment> getEnvironment(String name) {
        var sql = """
            SELECT
                NAME, URL,
                vpn.NAME as VPN_NAME,
                vpn.URL as VPN_URL,
                vpn.ORGANIZATION as VPN_ORGANIZATION
            FROM
                environment
                LEFT JOIN vpn on environment.VPN_ID = vpn.VPN_ID
            WHERE
                NAME = ?
            """;
        Environment environment = template.queryForObject(sql, mapper, name);
        if (environment == null) {
            return Optional.empty();
        }

        var codebaseSQL = """
            SELECT
                NAME, URL
            FROM codebase
                LEFT JOIN environment_codebase_pair ON environment_codebase_pair.CODEBASE_ID = codebase.CODEBASE_ID
                LEFT JOIN environment ON environment_codebase_pair.ENVIRONMENT_ID = environment.ENVIRONMENT_ID
            WHERE
                environment.NAME = ?
            """;
        List<CodeBase> codebases = template.query(codebaseSQL, codeBaseMapper, name);

        RecordTransformer<Environment> transformer = new RecordTransformer<>(environment);
        return Optional.ofNullable(transformer.with(transformer.rec()::codebases).as(codebases)
            .transform()
            .rec());
    }
}

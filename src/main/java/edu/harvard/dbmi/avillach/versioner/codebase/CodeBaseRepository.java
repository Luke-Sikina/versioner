package edu.harvard.dbmi.avillach.versioner.codebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CodeBaseRepository {

    @Autowired
    JdbcTemplate template;

    @Autowired
    CodeBaseMapper mapper;


    public List<CodeBase> getCodeBasesForEnvironment(String name) {
        var sql = """
            SELECT
                codebase.NAME,
                codebase.URL,
                codebase.PROJECT_CODE
            FROM
                codebase
                LEFT JOIN environment_codebase_pair ON codebase.CODEBASE_ID = environment_codebase_pair.CODEBASE_ID
                JOIN environment on environment_codebase_pair.ENVIRONMENT_ID = environment.ENVIRONMENT_ID
            WHERE
                environment.NAME = ?
            """;

        return template.query(sql, mapper, name);
    }
}

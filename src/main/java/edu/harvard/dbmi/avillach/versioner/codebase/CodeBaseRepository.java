package edu.harvard.dbmi.avillach.versioner.codebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CodeBaseRepository {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    private CodeBaseMapper mapper;


    public List<CodeBase> getCodeBasesForEnvironment(String name) {
        var sql = """
            SELECT
                codebase.CODEBASE_ID,
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

    public List<CodeBase> createCodeBases(List<CodeBase> codeBases) {
        // insert new codebases
        String sql = """
            INSERT IGNORE INTO codebase (NAME, URL, URL_SHA2, PROJECT_CODE)
            VALUES (?, ?, SHA2(?, 256), ?);
            """;
        template.batchUpdate(sql, new CodeBaseBatchSetter(codeBases));

        // return all matching codebases (url is unique)
        sql = """
            SELECT
                codebase.CODEBASE_ID,
                codebase.NAME,
                codebase.URL,
                codebase.PROJECT_CODE
            FROM
                codebase
            WHERE
                codebase.URL IN (:urls);
            """;
        SqlParameterSource parameters = new MapSqlParameterSource("urls", codeBases.stream().map(CodeBase::url).toList());
        return namedTemplate.query(sql, parameters, mapper);
    }


    private record CodeBaseBatchSetter(List<CodeBase> items) implements BatchPreparedStatementSetter {
        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, items.get(i).name());
            ps.setString(2, items.get(i).url());
            ps.setString(3, items.get(i).url());
            ps.setString(4, items.get(i).projectCode());
        }

        @Override
        public int getBatchSize() {
            return items.size();
        }
    }
}

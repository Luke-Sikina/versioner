package edu.harvard.dbmi.avillach.versioner.codebase;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CodeBaseMapper implements RowMapper<CodeBase> {
    @Override
    public CodeBase mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CodeBase(
            rs.getInt("CODEBASE_ID"),
            rs.getString("NAME"),
            rs.getString("URL"),
            rs.getString("PROJECT_CODE")
        );
    }
}

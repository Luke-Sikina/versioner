package edu.harvard.dbmi.avillach.versioner.config;

import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundle;
import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalJdbcTemplate {

    @Autowired
    private JdbcTemplate template;

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args) throws DataAccessException {
        try {
            return Optional.ofNullable(template.queryForObject(sql, rowMapper, args));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

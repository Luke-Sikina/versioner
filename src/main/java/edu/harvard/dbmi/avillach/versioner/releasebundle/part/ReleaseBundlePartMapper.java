package edu.harvard.dbmi.avillach.versioner.releasebundle.part;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReleaseBundlePartMapper implements RowMapper<ReleasePart> {
    @Autowired
    CodeBaseMapper codeBaseMapper;

    @Override
    public ReleasePart mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReleasePart(codeBaseMapper.mapRow(rs, rowNum), rs.getString("GIT_IDENTIFIER"));
    }
}

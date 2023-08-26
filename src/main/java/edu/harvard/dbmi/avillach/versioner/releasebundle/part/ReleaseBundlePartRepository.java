package edu.harvard.dbmi.avillach.versioner.releasebundle.part;

import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReleaseBundlePartRepository {

    @Autowired
    private ReleaseBundlePartMapper mapper;

    @Autowired
    private JdbcTemplate template;

    public List<ReleasePart> getAllReleasePartsForBundle(ReleaseBundle bundle) {
        String sql = """
            SELECT
                release_bundle_part.GIT_IDENTIFIER,
                codebase.NAME,
                codebase.URL,
                codebase.PROJECT_CODE
            FROM
                release_bundle_part
                JOIN codebase ON release_bundle_part.CODEBASE_ID = codebase.CODEBASE_ID
            WHERE
                release_bundle_part.RELEASE_BUNDLE_ID = ?
            ORDER BY codebase.NAME;
            """;

        return template.query(sql, mapper, bundle.id());
    }

    public void createReleasePartsForBundle(int id, List<ReleasePart> parts) {
        String sql = """
            INSERT INTO release_bundle_part (GIT_IDENTIFIER, CODEBASE_ID, RELEASE_BUNDLE_ID)
            VALUES (?, ?, ?);
            """;

        template.batchUpdate(sql, new ReleaseBundleBatchUpdater(parts, id));
    }

    private record ReleaseBundleBatchUpdater(List<ReleasePart> parts, int releaseBundleId) implements BatchPreparedStatementSetter {

        @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, parts().get(i).gitIdentifier());
                ps.setInt(2, parts().get(i).codeBase().id());
                ps.setInt(3, releaseBundleId());
            }

            @Override
            public int getBatchSize() {
                return parts().size();
            }
        }
}

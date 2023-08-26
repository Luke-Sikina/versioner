package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.config.OptionalJdbcTemplate;
import edu.harvard.dbmi.avillach.versioner.enviroment.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ReleaseBundleRepository {

    @Autowired
    private ReleaseBundleMapper mapper;

    @Autowired
    private OptionalJdbcTemplate optionalJdbcTemplate;

    @Autowired
    private JdbcTemplate template;

    public List<ReleaseBundle> getAllReleaseBundles() {
        String sql = """
            SELECT
                release_bundle.RELEASE_BUNDLE_ID,
                release_bundle.TITLE,
                release_bundle.STATUS,
                release_bundle.CREATION_DATE,
                release_bundle.UPDATE_DATE,
                release_bundle_part.GIT_IDENTIFIER,
                codebase.CODEBASE_ID,
                codebase.NAME,
                codebase.URL,
                codebase.PROJECT_CODE
            FROM
                release_bundle
                JOIN release_bundle_part ON release_bundle.RELEASE_BUNDLE_ID = release_bundle_part.RELEASE_BUNDLE_ID
                JOIN codebase ON release_bundle_part.CODEBASE_ID = codebase.CODEBASE_ID
            ORDER BY release_bundle.CREATION_DATE DESC, release_bundle.RELEASE_BUNDLE_ID, codebase.NAME;
            """;

        return template.query(sql, mapper);
    }

    public Optional<ReleaseBundle> getReleaseBundle(int id) {
        String sql = """
            SELECT
                release_bundle.RELEASE_BUNDLE_ID,
                release_bundle.TITLE,
                release_bundle.STATUS,
                release_bundle.CREATION_DATE,
                release_bundle.UPDATE_DATE,
                release_bundle_part.GIT_IDENTIFIER,
                codebase.CODEBASE_ID,
                codebase.NAME,
                codebase.URL,
                codebase.PROJECT_CODE
            FROM
                release_bundle
                JOIN release_bundle_part ON release_bundle.RELEASE_BUNDLE_ID = release_bundle_part.RELEASE_BUNDLE_ID
                JOIN codebase ON release_bundle_part.CODEBASE_ID = codebase.CODEBASE_ID
            WHERE
                release_bundle.RELEASE_BUNDLE_ID = ?
            """;

        //noinspection DataFlowIssue
        return template.query(sql, mapper, id).stream().findFirst();
    }

    public synchronized int createEmptyBundle(ReleaseBundle bundle) {
        String sql = """
            INSERT INTO release_bundle (TITLE, CREATION_DATE, UPDATE_DATE)
            VALUES (?, now(), now())
            """;
        template.update(sql, bundle.title());
        sql = """
            SELECT RELEASE_BUNDLE_ID as ID
            FROM release_bundle
            ORDER BY CREATION_DATE DESC
            LIMIT 1;
            """;
        //noinspection DataFlowIssue
        return template.queryForObject(sql, (rs, rowNum) -> rs.getInt(1));
    }
}

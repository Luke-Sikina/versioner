package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBaseMapper;
import edu.harvard.dbmi.avillach.versioner.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReleaseBundleMapper implements ResultSetExtractor<List<ReleaseBundle>> {
    @Autowired
    CodeBaseMapper codeBaseMapper;

    private static final ReleaseBundle EMPTY = new ReleaseBundle("", null, null);

    @Override
    public List<ReleaseBundle> extractData(ResultSet rs) throws SQLException, DataAccessException {
        int currentBundleId = -1;
        List<ReleaseBundle> bundles = new ArrayList<>();
        List<ReleasePart> parts = null;
        ReleaseBundle currentBundle = EMPTY;
        while (rs.next()) {
            if (currentBundleId != rs.getInt("RELEASE_BUNDLE_ID")) {
                // if this is not the first iteration and the bundles do not match, this is
                // just past the end of a bundle, so add the old one
                if (currentBundle != EMPTY) {
                    // add the updated parts list to the old bundle first
                    currentBundle = new ReleaseBundle(currentBundle, parts);
                    bundles.add(currentBundle);
                }
                currentBundle = new ReleaseBundle(
                    rs.getString("TITLE"), DateTimeUtil.fromSQL(rs.getDate("CREATION_DATE")), null
                );
                currentBundleId = rs.getInt("RELEASE_BUNDLE_ID");
                parts = new ArrayList<>();

            }
            parts.add(new ReleasePart(codeBaseMapper.mapRow(rs, -1), rs.getString("GIT_IDENTIFIER")));

        }
        if (currentBundle != EMPTY) {
            currentBundle = new ReleaseBundle(currentBundle, parts);
            bundles.add(currentBundle);
        }
        return bundles;
    }
}

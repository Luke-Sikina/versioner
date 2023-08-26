package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.releasebundle.part.ReleasePart;

import java.time.LocalDateTime;
import java.util.List;

public record ReleaseBundle(int id, ReleaseBundleStatus status, String title, LocalDateTime creationDate, List<ReleasePart> parts) {
    ReleaseBundle(ReleaseBundle from, List<ReleasePart> parts) {
        this(from.id(), from.status(), from.title(), from.creationDate(), parts);
    }
}

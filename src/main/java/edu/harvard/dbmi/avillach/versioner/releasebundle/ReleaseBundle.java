package edu.harvard.dbmi.avillach.versioner.releasebundle;

import java.time.LocalDateTime;
import java.util.List;

public record ReleaseBundle(String title, LocalDateTime creationDate, List<ReleasePart> parts) {
    protected ReleaseBundle(ReleaseBundle from, List<ReleasePart> parts) {
        this(from.title(), from.creationDate(), parts);
    }
}

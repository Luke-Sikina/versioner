package edu.harvard.dbmi.avillach.versioner.releasebundle;

import java.time.LocalDateTime;
import java.util.List;

public record ReleaseBundle(int id, String title, LocalDateTime creationDate, List<ReleasePart> parts) {
    ReleaseBundle(ReleaseBundle from, List<ReleasePart> parts) {
        this(from.id(), from.title(), from.creationDate(), parts);
    }
}

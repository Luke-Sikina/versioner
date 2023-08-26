package edu.harvard.dbmi.avillach.versioner.releasebundle;

import java.util.Arrays;

public enum ReleaseBundleStatus {
    Development, Testing, Released, Deprecated;

    public static ReleaseBundleStatus from(String from) {
        return Arrays.stream(values())
            .filter(e -> e.name().equalsIgnoreCase(from))
            .findFirst()
            .orElse(Deprecated);
    }
}

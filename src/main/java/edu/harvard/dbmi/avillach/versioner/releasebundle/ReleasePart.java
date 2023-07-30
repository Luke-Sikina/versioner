package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;

public record ReleasePart(CodeBase codeBase, String gitIdentifier) {
}

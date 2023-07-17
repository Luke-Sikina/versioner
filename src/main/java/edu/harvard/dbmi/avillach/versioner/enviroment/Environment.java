package edu.harvard.dbmi.avillach.versioner.enviroment;

import java.util.List;

public record Environment(String name, String url, VPN vpn, List<CodeBase> codebases, String extraFields) {
}

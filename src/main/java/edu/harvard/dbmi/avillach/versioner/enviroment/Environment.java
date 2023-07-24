package edu.harvard.dbmi.avillach.versioner.enviroment;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;

import java.util.List;

public record Environment(String name, String url, VPN vpn, List<CodeBase> codebases, String extraFields) {

    public Environment(String name, String url, VPN vpn, String extraFields) {
        this(name, url, vpn, List.of(), extraFields);
    }

    public Environment(Environment before, List<CodeBase> codebases) {
        this(before.name(), before.url(), before.vpn(), codebases, before.extraFields());
    }
}

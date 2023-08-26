package edu.harvard.dbmi.avillach.versioner.releasebundle.part;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import edu.harvard.dbmi.avillach.versioner.codebase.CodeBaseService;
import edu.harvard.dbmi.avillach.versioner.releasebundle.ReleaseBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReleaseBundlePartService {

    @Autowired
    private ReleaseBundlePartRepository repository;

    @Autowired
    private CodeBaseService codeBaseService;

    public List<ReleasePart> getAllReleasePartsForBundle(ReleaseBundle bundle) {
        return repository.getAllReleasePartsForBundle(bundle);
    }

    public void createReleasePartsForBundle(int id, List<ReleasePart> parts) {
        List<CodeBase> codeBases = parts.stream().map(ReleasePart::codeBase).toList();
        Map<String, CodeBase> codeBasesByUrl = codeBaseService.createCodeBases(codeBases).stream()
                .collect(Collectors.toMap(CodeBase::url, (c) -> c));
        parts = parts.stream()
            .map(part -> new ReleasePart(codeBasesByUrl.get(part.codeBase().url()), part.gitIdentifier()))
            .toList();
        repository.createReleasePartsForBundle(id, parts);
    }
}

package edu.harvard.dbmi.avillach.versioner.codebase;

import edu.harvard.dbmi.avillach.versioner.codebase.CodeBase;
import edu.harvard.dbmi.avillach.versioner.codebase.CodeBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeBaseService {

    @Autowired
    CodeBaseRepository repository;

    public List<CodeBase> getCodeBasesForEnvironment(String environmentName) {
        return repository.getCodeBasesForEnvironment(environmentName);
    }

    public List<CodeBase> createCodeBases(List<CodeBase> codeBases) {
        return repository.createCodeBases(codeBases);
    }
}

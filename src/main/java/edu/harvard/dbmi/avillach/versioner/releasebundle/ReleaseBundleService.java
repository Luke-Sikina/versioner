package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.enviroment.Environment;
import edu.harvard.dbmi.avillach.versioner.enviroment.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseBundleService {

    @Autowired
    private ReleaseBundleRepository repository;

    @Autowired
    private EnvironmentService environmentService;

    public List<ReleaseBundle> getAllReleaseBundles() {
        return repository.getAllReleaseBundles();
    }

    public Optional<ReleaseBundle> getReleaseBundle(int id) {
        return repository.getReleaseBundle(id);
    }
}

package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.enviroment.EnvironmentService;
import edu.harvard.dbmi.avillach.versioner.releasebundle.part.ReleaseBundlePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseBundleService {

    @Autowired
    private ReleaseBundleRepository repository;

    @Autowired
    private ReleaseBundlePartService partService;

    public List<ReleaseBundle> getAllReleaseBundles() {
        return repository.getAllReleaseBundles();
    }

    public Optional<ReleaseBundle> getReleaseBundle(int id) {
        return repository.getReleaseBundle(id);
    }

    public Optional<Integer> createReleaseBundle(ReleaseBundle bundle) {
        int id = repository.createEmptyBundle(bundle);
        partService.createReleasePartsForBundle(id, bundle.parts());
        return id == -1 ? Optional.empty() : Optional.of(id);
    }
}

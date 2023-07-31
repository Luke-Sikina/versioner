package edu.harvard.dbmi.avillach.versioner.releasebundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseBundleService {

    @Autowired
    private ReleaseBundleRepository repository;

    public List<ReleaseBundle> getAllReleaseBundles() {
        return repository.getAllReleaseBundles();
    }
}

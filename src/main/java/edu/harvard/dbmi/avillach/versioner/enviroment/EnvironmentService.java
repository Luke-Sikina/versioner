package edu.harvard.dbmi.avillach.versioner.enviroment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvironmentService {

    @Autowired
    private EnvironmentRepository repository;

    public Optional<Environment> getEnvironment(String name) {
        return repository.getEnvironment(name);
    }

    public List<Environment> getAllEnvironments() {
        return repository.getAllEnvironments();
    }
}

package edu.harvard.dbmi.avillach.versioner.enviroment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnvironmentService {

    @Autowired
    EnvironmentRepository repository;

    public Optional<Environment> getEnvironment(String name) {
        return repository.getEnvironment(name);
    }
}

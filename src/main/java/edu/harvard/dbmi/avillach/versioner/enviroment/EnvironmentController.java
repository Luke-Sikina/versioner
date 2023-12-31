package edu.harvard.dbmi.avillach.versioner.enviroment;

import edu.harvard.dbmi.avillach.versioner.config.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@ApiController
public class EnvironmentController {
    @Autowired
    EnvironmentService service;

    @GetMapping("/environment/{name}")
    public ResponseEntity<Environment> getEnvironmentByName(
        @PathVariable String name
    ) {
        return service.getEnvironment(name)
            .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/environment/")
    public ResponseEntity<List<Environment>> getAllEnvironments() {
        return new ResponseEntity<>(service.getAllEnvironments(), HttpStatus.OK);
    }
}

package edu.harvard.dbmi.avillach.versioner.releasebundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReleaseBundleController {
    @Autowired
    private ReleaseBundleService releaseBundleService;

    @GetMapping("/release-bundle/")
    public ResponseEntity<List<ReleaseBundle>> getAllReleaseBundles() {
        return new ResponseEntity<>(releaseBundleService.getAllReleaseBundles(), HttpStatus.OK);
    }
}

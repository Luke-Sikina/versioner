package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.config.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Controller
@ApiController
public class ReleaseBundleController {
    @Autowired
    private ReleaseBundleService releaseBundleService;

    @GetMapping("/release-bundle/")
    public ResponseEntity<List<ReleaseBundle>> getAllReleaseBundles() {
        return new ResponseEntity<>(releaseBundleService.getAllReleaseBundles(), HttpStatus.OK);
    }

    @PatchMapping("/release-bundle/{id}")
    public ResponseEntity<ReleaseBundle> updateReleaseBundle(ReleaseBundle releaseBundle) {
        return releaseBundleService.getReleaseBundle(releaseBundle.id())
            .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}

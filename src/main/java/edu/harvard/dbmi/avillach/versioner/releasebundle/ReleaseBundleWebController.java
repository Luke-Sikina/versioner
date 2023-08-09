package edu.harvard.dbmi.avillach.versioner.releasebundle;

import edu.harvard.dbmi.avillach.versioner.enviroment.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ReleaseBundleWebController {
    @Autowired
    ReleaseBundleService releaseBundleService;


    @RequestMapping(value = "/release-bundle/")
    public String releaseBundle(Model model) {
        model.addAttribute("firstBundle", releaseBundleService.getAllReleaseBundles().get(0).title());
        return "release_bundle";
    }

    @RequestMapping(value = "/release-bundle/{id}")
    public String releaseBundlesForEnv(Model model, @PathVariable("id") int id) {
        return releaseBundleService.getReleaseBundle(id).map((b) -> {
            model.addAttribute("bundle", b);
            return "release_bundle";
        }).orElse("index");
    }
}

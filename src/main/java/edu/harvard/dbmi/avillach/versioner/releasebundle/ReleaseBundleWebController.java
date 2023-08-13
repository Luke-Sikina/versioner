package edu.harvard.dbmi.avillach.versioner.releasebundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReleaseBundleWebController {
    @Autowired
    ReleaseBundleService releaseBundleService;


    @RequestMapping(value = "/release-bundles")
    public String releaseBundle(Model model) {
        model.addAttribute("releaseBundles", releaseBundleService.getAllReleaseBundles());
        return "release_bundles";
    }

    @RequestMapping(value = "/release-bundle/{id}")
    public String releaseBundlesForEnv(Model model, @PathVariable("id") int id) {
        return releaseBundleService.getReleaseBundle(id).map((b) -> {
            model.addAttribute("releaseBundle", b);
            return "release_bundle";
        }).orElse("index");
    }
}

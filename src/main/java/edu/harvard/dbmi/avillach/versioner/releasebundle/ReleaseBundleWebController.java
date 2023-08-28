package edu.harvard.dbmi.avillach.versioner.releasebundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.harvard.dbmi.avillach.versioner.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReleaseBundleWebController {

    @Autowired
    ReleaseBundleService releaseBundleService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(value = "/release-bundles")
    public String releaseBundle(Model model) {
        model.addAttribute("releaseBundles", releaseBundleService.getAllReleaseBundles());
        return "release_bundles";
    }

    @RequestMapping(value = "/release-bundle/{id}")
    public String releaseBundleById(Model model, @PathVariable("id") int id) {
        return releaseBundleService.getReleaseBundle(id).map((b) -> {
            model.addAttribute("releaseBundle", b);
            model.addAttribute("releaseDate", DateTimeUtil.formatDate(b.creationDate()));
            model.addAttribute("updateDate", DateTimeUtil.formatDate(b.updateDate()));
            return "release_bundle";
        }).orElse("index");
    }

    @RequestMapping(value = "/release-bundle/edit/{id}")
    public String exitReleaseBundle(Model model, @PathVariable("id") int id) {
        return releaseBundleService.getReleaseBundle(id).map((b) -> {
            model.addAttribute("releaseBundle", b);
            model.addAttribute("releaseDate", DateTimeUtil.formatDate(b.creationDate()));
            model.addAttribute("updateDate", DateTimeUtil.formatDate(b.updateDate()));
            model.addAttribute("releaseBundleJson", writeValue(b));
            return "update_release_bundle.html";
        }).orElse("index");
    }

    private String writeValue(Object o) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

package com.techdev.sdg.Project;

import com.techdev.sdg.Entity.Entity;
import com.techdev.sdg.Entity.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private EntityService entityService;

    @RequestMapping(value = Router.ADDPROJECT, method = RequestMethod.POST)
    public ResponseEntity<Object> addProject(@RequestBody Map<String, Object> body) {
        ResponseEntity<Object> res;
        try {
            String user = service.currentUserName();
            Entity e = entityService.findByUsername(user);
            List<Entity> viewers = entityService.findProjectViewers(body);
            Project p = service.save(body, e, viewers);
            res = new ResponseEntity<Object>(p.toMap(), HttpStatus.OK);
        } catch (Exception e) {
            res = new ResponseEntity<Object>("Unexpected error occured: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = Router.GETPROJECT, method = RequestMethod.GET)
    public ResponseEntity<Object> getProject(@PathVariable Long projectId) {
        ResponseEntity<Object> res;
        try {
            String viewerName = service.currentUserName();
            Entity entity = entityService.findByUsername(viewerName);
            Project viewerProject = service.findByViewer(entity, projectId);
            res = new ResponseEntity<Object>(viewerProject.toMap(), HttpStatus.OK);
        } catch (Exception e) {
            res = new ResponseEntity<Object>("Unexpected error occured: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }

    @RequestMapping(value = Router.GETPROJECTS, method = RequestMethod.GET)
    public ResponseEntity<Object> getProjects() {
        ResponseEntity<Object> res = null;
        try {
            List<Project> projects = service.findAll();
            res = new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            res = new ResponseEntity<>("Unexpected error occured: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}

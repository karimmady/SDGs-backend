package com.techdev.sdg.PrivateSector;

import com.techdev.sdg.PrivateSector.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PrivateSectorController {

    @Autowired
    private PrivateSectorService service;

    @RequestMapping(value = Router.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody Map<String, Object> body) {
        ResponseEntity<Object> res;
        try {
            PrivateSector ps = service.save(body);
            res = new ResponseEntity<Object>(ps, HttpStatus.OK);
        } catch (Exception e) {
            res = new ResponseEntity<Object>("Unexpected error occured: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return res;
    }
}

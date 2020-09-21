package com.Controllers;

import com.Entities.Department;
import com.Entities.Position;
import com.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;
    @CrossOrigin
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Position>> getPositionsByDepartment(@PathVariable("id") Integer id){
        try {
            List<Position> positionList = positionService.getPositionsByDepartment(id);
            return new ResponseEntity<List<Position>>(positionList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<Position>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


package com.Controllers;

import com.Entities.Department;
import com.Entities.Position;
import com.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Position> getPositionsByDepartment(@PathVariable("id") Integer id){
        return positionService.getPositionsByDepartment(id);
    }
}


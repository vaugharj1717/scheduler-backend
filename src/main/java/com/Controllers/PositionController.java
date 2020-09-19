package com.Controllers;

import com.Entities.Department;
import com.Entities.Position;
import com.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;

    @RequestMapping(value = "/department/{id}/position", method = RequestMethod.GET)
    public List<Position> getPositionsByDepartement(@PathVariable("id") Integer id){
        return positionService.getPositionsByDepartement(id);
    }
}


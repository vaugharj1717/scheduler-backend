package com.Controllers;

import com.Entities.ScheduleGroup;
import com.Services.ScheduleGroupService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule-group")
public class ScheduleGroupController {

    @Autowired
    ScheduleGroupService scheduleGroupService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleGroup>> getAll(){
        try {
            List<ScheduleGroup> scheduleGroupList = scheduleGroupService.getAll();
            return new ResponseEntity<List<ScheduleGroup>>(scheduleGroupList, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<ScheduleGroup>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ScheduleGroup> createScheduleGroup(@RequestBody JsonNode body){
        try{
            Integer positionId = body.get("positionId").asInt();
            ScheduleGroup newScheduleGroup = scheduleGroupService.createScheduleGroup(positionId);
            if(newScheduleGroup == null){
                return new ResponseEntity<ScheduleGroup>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<ScheduleGroup>(newScheduleGroup, HttpStatus.OK);
            }
        }
        catch(Exception e){
            return new ResponseEntity<ScheduleGroup>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

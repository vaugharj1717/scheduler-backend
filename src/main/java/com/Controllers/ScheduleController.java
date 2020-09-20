package com.Controllers;

import com.Entities.ScheduleGroup;
import com.Services.ScheduleService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    JsonParser parser = JsonParserFactory.getJsonParser();

    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleGroup>> getAllSchedules(){
        try{
            List<ScheduleGroup> scheduleGroupList = scheduleService.getAllSchedules();
            return new ResponseEntity<List<ScheduleGroup>>(scheduleGroupList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<ScheduleGroup>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/scheduleGroup", method = RequestMethod.POST)
    public ResponseEntity<ScheduleGroup> createNewScheduleGroup(@RequestBody JsonNode body){
        try{
            Integer positionId = body.get("positionId").asInt();
            ScheduleGroup newScheduleGroup = scheduleService.createNewScheduleGroup(positionId);
            return new ResponseEntity<ScheduleGroup>(newScheduleGroup, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ScheduleGroup>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

package com.Controllers;

import com.Entities.Candidacy;
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
    public ResponseEntity<List<Candidacy>> getAll(){
        try {
            List<Candidacy> candidacyList = scheduleGroupService.getAll();
            return new ResponseEntity<List<Candidacy>>(candidacyList, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Candidacy>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Candidacy> createScheduleGroup(@RequestBody JsonNode body){
        try{
            Integer positionId = body.get("positionId").asInt();
            Candidacy newCandidacy = scheduleGroupService.createScheduleGroup(positionId);
            if(newCandidacy == null){
                return new ResponseEntity<Candidacy>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<Candidacy>(newCandidacy, HttpStatus.OK);
            }
        }
        catch(Exception e){
            return new ResponseEntity<Candidacy>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

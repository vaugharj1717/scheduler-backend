package com.Controllers;

import com.Entities.Schedule;
import com.Services.ScheduleService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(path = "/{scheduleId}", method = RequestMethod.GET)
    public ResponseEntity<Schedule> getSchedule(@PathVariable Integer scheduleId){
        try{
            Schedule schedule = scheduleService.getSchedule(scheduleId);
            if(schedule == null){
                return new ResponseEntity<Schedule>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
            }
        }
        catch(Exception e){
            return new ResponseEntity<Schedule>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

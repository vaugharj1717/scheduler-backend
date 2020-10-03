package com.Controllers;

import com.Entities.Meeting;
import com.Services.MeetingService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @RequestMapping(path = "/{meetingId}", method = RequestMethod.GET)
    public ResponseEntity<Meeting> getMeeting(@PathVariable Integer meetingId){
        try{
            Meeting meeting = meetingService.getMeeting(meetingId);
            if(meeting == null){
                return new ResponseEntity<Meeting>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Meeting> createMeeting(@RequestBody JsonNode body){
        try{
            Integer scheduleId = body.get("scheduleId").asInt();
            Integer locationId = body.get("locationId").asInt();
            String startTime = body.get("startTime").asText();
            String endTime = body.get("endTime").asText();
            Meeting newMeeting = meetingService.createMeeting(scheduleId, locationId, startTime, endTime);
            if(newMeeting == null) return new ResponseEntity<Meeting>(HttpStatus.BAD_REQUEST);
            else{
                return new ResponseEntity<Meeting>(newMeeting, HttpStatus.OK);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @RequestMapping(path = "/{meetingId}", method = RequestMethod.DELETE)
    public ResponseEntity<Meeting> deleteMeeting(@PathVariable Integer meetingId) {
        try {
            meetingService.deleteMeeting(meetingId);
            return new ResponseEntity<Meeting>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

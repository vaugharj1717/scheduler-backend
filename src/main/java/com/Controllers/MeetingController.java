package com.Controllers;

import com.Entities.Meeting;
import com.Services.MeetingService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//When we view a meeting box we need
//participants (users)
//participation of logged in user
//location
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
}

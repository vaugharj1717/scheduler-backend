package com.Controllers;

import com.Entities.Participation;
import com.Entities.UserMessage;
import com.Exceptions.ErrorResponse;
import com.Services.ParticipationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/participation")
public class ParticipationController {

    @Autowired
    ParticipationService participationService;

    @RequestMapping(path = "/setFeedback/{participationId}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    @Transactional
    public ResponseEntity<?> setFeedback(@PathVariable Integer participationId, @RequestBody JsonNode body){
        try {
            String feedback = body.get("feedback").asText();
            participationService.setFeedback(participationId, feedback);
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Could not leave feedback"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/getAllParticipation/{meetingId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    @Transactional
    public ResponseEntity<?> getAllParticipation(@PathVariable Integer meetingId){
        try {
            List<Participation> participationList =  participationService.getAllParticipationByMeetingId(meetingId);
            return new ResponseEntity<>(participationList, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Could not leave feedback"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/patchParticipantAlert", method = RequestMethod.PATCH)
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    @Transactional
    public ResponseEntity<?> patchParticipantAlert(@RequestBody JsonNode body){
        try {
            Integer participationId = Integer.valueOf(body.get("participationId").asText());
            Boolean alert = Boolean.valueOf(body.get("alert").asText());
            participationService.patchParticipantAlert(participationId, alert);
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Could not change alert for participant"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

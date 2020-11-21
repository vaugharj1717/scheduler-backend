package com.Controllers;

import com.Entities.Meeting;
import com.Entities.Participation;
import com.Entities.enumeration.MeetingType;
import com.Exceptions.ConflictingLocationException;
import com.Exceptions.ConflictingUserException;
import com.Exceptions.ErrorResponse;
import com.Services.MeetingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @RequestMapping(path = "/{meetingId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCHEDULER')")
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

    @RequestMapping(path = "/getUpcoming", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<?> getUpcomingMeetings(){
        try{
            List<Meeting> meetings = meetingService.getUpcomingMeetings();
            return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{userId}/getUpcoming", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('PARTICIPANT') OR hasAuthority('CANDIDATE')")
    public ResponseEntity<?> getUpcomingMeetingsById(@PathVariable Integer userId){
        try{
            List<Meeting> meetings = meetingService.getUpcomingMeetingsById(userId);
            return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/getPast", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<?> getPastMeetings(){
        try{
            List<Meeting> meetings = meetingService.getPastMeetings();
            return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{userId}/getPast", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('PARTICIPANT') OR hasAuthority('CANDIDATE')")
    public ResponseEntity<?> getPastMeetingsById(@PathVariable Integer userId){
        try{
            List<Meeting> meetings = meetingService.getPastMeetingsById(userId);
            return new ResponseEntity<List<Meeting>>(meetings, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Meeting>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path="/{scheduleId}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<?> createMeeting(@RequestBody JsonNode body, @PathVariable Integer scheduleId){
        try{
            //pull data from request
            Integer locationId = body.get("locationId").asInt();
            MeetingType meetingType = body.get("meetingType").asText()
                    .equals("MEET_FACULTY") ? MeetingType.MEET_FACULTY : MeetingType.PRESENTATION;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date startTime = formatter.parse(body.get("startTime").asText());
            Date endTime = formatter.parse(body.get("endTime").asText());

            //iterate through and pull participant data from request
            ArrayList<Boolean> canLeaveFeedbackList = new ArrayList<>();
            ArrayList<Boolean> canViewFeedbackList = new ArrayList<>();
            ArrayList<Integer> participantList = new ArrayList<>();
            ArrayNode getParticipants =  (ArrayNode) body.get("participations");
            Iterator<JsonNode> itr = getParticipants.elements();
            while(itr.hasNext()) {
                JsonNode nextNode = itr.next();
                canLeaveFeedbackList.add(nextNode.get("canLeaveFeedback").asBoolean());
                canViewFeedbackList.add(nextNode.get("canViewFeedback").asBoolean());
                participantList.add(nextNode.get("participantId").asInt());
            }

            //Call on meetingService to create meeting
            Meeting newMeeting = meetingService.createMeeting(scheduleId, locationId, startTime, endTime, meetingType,
                    canViewFeedbackList, canLeaveFeedbackList, participantList);

            return new ResponseEntity<Meeting>(newMeeting, HttpStatus.OK);

        }

        //If server error
        catch(ConflictingLocationException cle){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(cle.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        catch(ConflictingUserException cue){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(cue.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        catch(IllegalStateException ise){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Received invalid data"),
                    HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was an error creating meeting"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @RequestMapping(path = "/{meetingId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Integer> deleteMeeting(@PathVariable Integer meetingId) {
        try {
            meetingService.deleteMeeting(meetingId);
            return new ResponseEntity<Integer>(meetingId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

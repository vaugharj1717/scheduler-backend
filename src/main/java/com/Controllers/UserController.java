package com.Controllers;

import com.Entities.Candidacy;
import com.Entities.User;
import com.Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/participant")
    public ResponseEntity<List<User>> getAllParticipants(){
        try{
            List<User> participantList = userService.getAllParticipants();

            //success case
            return new ResponseEntity<List<User>>(participantList, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/candidate", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllCandidates(){
        try{
            List<User> candidateList = userService.getAllCandidates();
            //success case
            return new ResponseEntity<List<User>>(candidateList, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/candidate/{positionId}", method = RequestMethod.POST)
    public ResponseEntity<Candidacy> createCandidate(@PathVariable Integer positionId, @RequestBody JsonNode body){
        try{
          String name = body.get("name").asText();
          String email = body.get("email").asText();

          Candidacy newCandidacy = userService.createCandidate(positionId, name, email);
          return new ResponseEntity<Candidacy>(newCandidacy, HttpStatus.OK);

        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Candidacy>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

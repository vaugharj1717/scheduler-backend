package com.Controllers;

import com.Entities.Candidacy;
import com.Entities.Department;
import com.Entities.Position;
import com.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;

    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Position>> getPositionsByDepartment(@PathVariable("id") Integer id){
        try {
            List<Position> positionList = positionService.getPositionsByDepartment(id);
            return new ResponseEntity<List<Position>>(positionList, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Position>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<List<Position>> getAllPositions(){
        try {
            List<Position> positionList = positionService.getAllPositions();
            return new ResponseEntity<List<Position>>(positionList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<Position>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{positionId}/candidate/{candidateId}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Candidacy> assignCandidateToPosition(
            @PathVariable("positionId") Integer positionId,
            @PathVariable("candidateId") Integer candidateId){
        try {
            Candidacy candidacy = positionService.assignCandidateToPosition(positionId, candidateId);
            return new ResponseEntity<Candidacy>(candidacy, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Candidacy>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/candidacy/{candidacyId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Integer> unassignCandidateFromPosition(
            @PathVariable("candidacyId") Integer candidacyId){
        try {
            positionService.unassignCandidateFromPosition(candidacyId);
            return new ResponseEntity<Integer>(candidacyId, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/createPosition/{positionName}/{idDepartement}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Position> createPositionToDepartment(@PathVariable("positionName") String positionName, @PathVariable("idDepartement") Integer idDepartement) {
        try {
            Position position = positionService.createPositionToDepartment(positionName, idDepartement);
            if (position == null) return new ResponseEntity<Position>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Position>(position, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Position>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{positionId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Integer> removePosition(@PathVariable("positionId") Integer positionId){
        try {
            positionService.removePosition(positionId);
            return new ResponseEntity<Integer>(positionId, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


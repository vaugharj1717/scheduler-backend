package com.Controllers;

import com.Entities.Candidacy;
import com.Entities.User;
import com.Entities.UserFile;
import com.Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/participant")
    @PreAuthorize("hasAuthority('SCHEDULER')")
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
    @PreAuthorize("hasAuthority('SCHEDULER')")
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
    @PreAuthorize("hasAuthority('SCHEDULER')")
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

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> adminControlDeleteUser(@PathVariable Integer userId){
        try{
            userService.adminControlDeleteUser(userId);
            return new ResponseEntity<Integer>(userId, HttpStatus.OK);

        }

        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> adminControlsCreateUser(@RequestBody JsonNode body){
        try{
            String name = body.get("name").asText();
            String email = body.get("email").asText();
            String role = body.get("role").asText();

            User newUser = userService.adminControlsCreateUser(name, email, role);
            if(newUser == null){
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<User>(newUser, HttpStatus.OK);

        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer userId) {
        try {
            UserFile newUserFile = userService.storeUserFile(file, userId);
            if(newUserFile == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<UserFile>(newUserFile, HttpStatus.OK);
        }
        catch(IOException ioe){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer fileId, HttpServletRequest request){
        try {
            // Load file as Resource
            Resource resource = userService.loadUserFileAsResource(fileId);
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("Could not determine resource content type");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

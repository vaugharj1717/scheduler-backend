package com.Controllers;

import com.Entities.Candidacy;
import com.Entities.User;
import com.Entities.UserFile;
import com.Entities.UserMessage;
import com.Entities.enumeration.Role;
import com.Exceptions.InvalidUserDeletionException;
import com.Security.UserDetailsImpl;
import com.Services.UserService;
import com.Exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.Principal;
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
    public ResponseEntity<?> createCandidate(@PathVariable Integer positionId, @RequestBody JsonNode body){
        try{
          String name = body.get("name").asText();
          String email = body.get("email").asText();

          Candidacy newCandidacy = userService.createCandidate(positionId, name, email);
          return new ResponseEntity<Candidacy>(newCandidacy, HttpStatus.OK);

        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was an error creating candidate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody JsonNode body) {
        try{
            String logUser = principal.getName();
            Integer userId = body.get("userId").asInt();
            String oldPassword = body.get("oldPassword").asText();
            String newPassword = body.get("newPassword").asText();
            String newPassword2 = body.get("newPassword2").asText();

            userService.changePassword(logUser, userId, oldPassword, newPassword, newPassword2);
            return new ResponseEntity<Integer>(1, HttpStatus.OK);

        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was an error creating candidate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> adminControlDeleteUser(@PathVariable Integer userId, Principal loggedUser){
        try{
            String loggedUserEmail = loggedUser.getName();
            userService.adminControlDeleteUser(userId, loggedUserEmail);
            return new ResponseEntity<Integer>(userId, HttpStatus.OK);
        }

        catch(InvalidUserDeletionException iude){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Invalid user"), HttpStatus.BAD_REQUEST);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was a problem deleting user"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> adminControlsCreateUser(@RequestBody JsonNode body){
        try{
            System.out.println("HERE!!!");
            String name = body.get("name").asText();
            String email = body.get("email").asText();
            String role = body.get("role").asText();

            User newUser = userService.adminControlsCreateUser(name, email, role);
            if(newUser == null){
                System.out.println("ERROR RESPONSE!!!");
                return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was a problem creating user"), HttpStatus.BAD_REQUEST);
            }
            System.out.println("GOOD RESPONSE!!!");
            System.out.println(newUser);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);

        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(
                    new ErrorResponse("There was a problem creating user"), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{userId}/files")
    public ResponseEntity<?> getUserFiles(@PathVariable Integer userId){
            List<UserFile> userFileList = userService.getUserFiles(userId);
            return new ResponseEntity<List<UserFile>>(userFileList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer userId) {
        try {
            UserFile newUserFile = userService.storeUserFile(file, userId);
            if(newUserFile == null) return new ResponseEntity<ErrorResponse>(new ErrorResponse("Invalid user id"), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("There was a problem downloading the file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteFile/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Integer fileId){
        try{
            userService.deleteFile(fileId);
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        catch(NoSuchFileException nsfe){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("File does not exist"), HttpStatus.BAD_REQUEST);
        }
        catch(IOException ioe){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Could not delete file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers(Principal principal){
        try {
            String loggedUserEmail = principal.getName();
            List<User> userList = userService.getAllUsers(loggedUserEmail);
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Could not get users"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(path = "/{userId}/changeRole", method = RequestMethod.PATCH)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeRole(@PathVariable Integer userId, @RequestBody JsonNode body ){
        try{
            Role role = Role.getFromName(body.get("role").asText());
            userService.changeRole(role, userId);
            //success case
            return new ResponseEntity<String>(role.toString(), HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Role>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getCandidatesAndParticipants")
    @PreAuthorize("hasAuthority('CANDIDATE') or hasAuthority('PARTICIPANT')")
    public ResponseEntity<?> getCandidatesAndParticipants(Principal principal){
        try {
            String loggedUserEmail = principal.getName();
            List<User> userList = userService.getCandidatesAndParticipants(loggedUserEmail);
            return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Could not get users"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{userId}/getMessages/{isViewing}")
    @PreAuthorize("hasAuthority('CANDIDATE') or hasAuthority('PARTICIPANT')")
    public ResponseEntity<?> getMessages(@PathVariable Integer userId, @PathVariable Boolean isViewing){
        try {
            List<UserMessage> messages = userService.getMessages(userId, isViewing);
            return new ResponseEntity<List<UserMessage>>(messages, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Could not get messages"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{senderId}/sendMessage/{recipientId}")
    @PreAuthorize("hasAuthority('CANDIDATE') or hasAuthority('PARTICIPANT')")
    public ResponseEntity<?> sendMessage(@PathVariable Integer senderId, @PathVariable Integer recipientId,
                                         @RequestBody JsonNode body){
        try {
            String message = body.get("message").asText();
            UserMessage newMessage = userService.sendMessage(senderId, recipientId, message);
            return new ResponseEntity<UserMessage>(newMessage, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("Could not get messages"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.MailDetail;
import com.example.entity.Song;
import com.example.service.SongService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:8082") // allow requests from UserService frontend

public class SongController {

    @Autowired
    private SongService songService;
    
    @Autowired
    private RestTemplate restTemplate;
    
//    @Autowired
//    private UserService userService;



    //  GET ALL SONGS
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    //  GET SONG BY ID 
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    //  CREATE SONG 
   @PostMapping
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
        Song createdSong = songService.createSong(song);
        MailDetail mailDetail = new MailDetail();
        mailDetail.setRecipient("user@example.com"); // Replace with actual user emails later
        mailDetail.setSubject("New Song Added");
        mailDetail.setMsgBody("A new song '" + song.getName() + "' has been added!");

        // Send email to EmailNotificationService
        String emailServiceUrl = "http://localhost:8888/sendMail";
        restTemplate.postForObject(emailServiceUrl, mailDetail, String.class);

        return ResponseEntity.ok(createdSong);
    }
    
    
//    @PostMapping
//    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
//        Song createdSong = songService.createSong(song);
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        System.out.println("Authenticated username: " + username);
//
//        // Fetch user details from UserService via REST
//        String userServiceUrl = "http://localhost:8082/api/users/" + username;
//        ResponseEntity<User> userResponse = restTemplate.getForEntity(userServiceUrl, User.class);
//        User user = userResponse.getBody();
//
//        if (user == null) {
//            System.out.println("User not found!");
//            throw new RuntimeException("User not found");
//        }
//
//        String email = user.getEmail();
//        System.out.println("Sending email to: " + email);
//
//        MailDetail mailDetail = new MailDetail();
//        mailDetail.setRecipient(email);
//        mailDetail.setSubject("New Song Added");
//        mailDetail.setMsgBody("A new song '" + song.getName() + "' has been added!");
//
//        String emailServiceUrl = "http://localhost:8888/sendMail";
//        try {
//            String response = restTemplate.postForObject(emailServiceUrl, mailDetail, String.class);
//            System.out.println("Email service response: " + response);
//        } catch (Exception e) {
//            System.out.println("Error sending email: " + e.getMessage());
//        }
//
//        return ResponseEntity.ok(createdSong);
//    }
//
    
    
    //  UPDATE SONG 
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @Valid @RequestBody Song songDetails) {
        Song updatedSong = songService.updateSong(id, songDetails);
        return ResponseEntity.ok(updatedSong);
    }

    //  DELETE SONG 
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);  // Delete the song
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    
    
    @GetMapping("/search")
    public ResponseEntity<List<Song>> searchSongs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String singer,
            @RequestParam(required = false) String musicDirector,
            @RequestParam(required = false) String albumName
    ) {
        List<Song> songs = songService.searchSongs(name, singer, musicDirector, albumName);
        return ResponseEntity.ok(songs);
    }
}

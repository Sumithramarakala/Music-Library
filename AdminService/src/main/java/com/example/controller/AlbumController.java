package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Album;
import com.example.service.AlbumService;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    //  GET ALL ALBUMS 
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    //  GET ALBUM BY ID 
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Album album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    //  CREATE ALBUM 
    @PostMapping
    public ResponseEntity<Album> createAlbum(@Valid @RequestBody Album album) {
        Album createdAlbum = albumService.createAlbum(album);
        return ResponseEntity.ok(createdAlbum);
    }

    //  UPDATE ALBUM 
    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @Valid @RequestBody Album albumDetails) {
        Album updatedAlbum = albumService.updateAlbum(id, albumDetails);
        return ResponseEntity.ok(updatedAlbum);
    }

    //  DELETE ALBUM 
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

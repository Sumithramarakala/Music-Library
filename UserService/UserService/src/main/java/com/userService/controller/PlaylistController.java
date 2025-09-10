package com.userService.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userService.entity.Playlist;
import com.userService.entity.PlaylistSong;
import com.userService.service.PlaylistService;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    //  GET ALL PLAYLISTS FOR A USER 
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Playlist>> getPlaylistsByUser(@PathVariable Long userId) {
        List<Playlist> playlists = playlistService.getPlaylistsByUserId(userId);
        return ResponseEntity.ok(playlists);
    }

    //  CREATE NEW PLAYLIST 
    @PostMapping("/user/{userId}")
    public ResponseEntity<Playlist> createPlaylist(@PathVariable Long userId, @RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.createPlaylist(userId, playlist);
        return ResponseEntity.ok(createdPlaylist);
    }

    //  UPDATE PLAYLIST 
    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long playlistId, @RequestBody Playlist playlist) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(playlistId, playlist);
        return ResponseEntity.ok(updatedPlaylist);
    }

    //  DELETE PLAYLIST 
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Map<String, Boolean>> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //  ADD SONG TO PLAYLIST 
    @PostMapping("/{playlistId}/songs")
    public ResponseEntity<PlaylistSong> addSongToPlaylist(@PathVariable Long playlistId, @RequestBody Map<String, Long> request) {
        Long songId = request.get("songId");
        PlaylistSong playlistSong = playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok(playlistSong);
    }

    //  REMOVE SONG FROM PLAYLIST 
    @DeleteMapping("/songs/{playlistSongId}")
    public ResponseEntity<Map<String, Boolean>> removeSongFromPlaylist(@PathVariable Long playlistSongId) {
        playlistService.removeSongFromPlaylist(playlistSongId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("removed", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //  GET ALL SONGS IN A PLAYLIST 
    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<List<PlaylistSong>> getSongsInPlaylist(@PathVariable Long playlistId) {
        List<PlaylistSong> songs = playlistService.getSongsInPlaylist(playlistId);
        return ResponseEntity.ok(songs);
    }
}


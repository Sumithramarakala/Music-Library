package com.userService.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.exception.ResourceNotFoundException;
import com.userService.entity.Playlist;
import com.userService.entity.PlaylistSong;
import com.userService.entity.User;
import com.userService.repository.PlaylistRepository;
import com.userService.repository.PlaylistSongRepository;
import com.userService.repository.UserRepository;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistSongRepository playlistSongRepository,
                           UserRepository userRepository,
                           RestTemplate restTemplate) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    // Get all playlists for a user
    public List<Playlist> getPlaylistsByUserId(Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    // Create a new playlist for a user
    public Playlist createPlaylist(Long userId, Playlist playlist) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        playlist.setUser(user);
        return playlistRepository.save(playlist);
    }

    // Update playlist details
    public Playlist updatePlaylist(Long playlistId, Playlist updatedPlaylist) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));
        playlist.setName(updatedPlaylist.getName());
        playlist.setDescription(updatedPlaylist.getDescription());
        return playlistRepository.save(playlist);
    }

    // Delete a playlist
    public void deletePlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));
        playlistRepository.delete(playlist);
    }

    // Add a song to a playlist
    public PlaylistSong addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSongId(songId);
        playlistSong.setAddedDate(LocalDateTime.now());
        return playlistSongRepository.save(playlistSong);
    }

    // Remove a song from a playlist
    public void removeSongFromPlaylist(Long playlistSongId) {
        PlaylistSong playlistSong = playlistSongRepository.findById(playlistSongId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist song not found with id " + playlistSongId));
        playlistSongRepository.delete(playlistSong);
    }

    // Get all songs in a playlist
    public List<PlaylistSong> getSongsInPlaylist(Long playlistId) {
        return playlistSongRepository.findByPlaylistId(playlistId);
    }

    // Get playlist with detailed song information
    public Map<String, Object> getPlaylistWithSongs(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));

        List<Map<String, Object>> songs = playlist.getPlaylistSongs() != null
                ? playlist.getPlaylistSongs().stream()
                    .map(playlistSong -> {
                        String url = "http://admin-service/api/songs/" + playlistSong.getSongId();
                        try {
                            return restTemplate.getForObject(url, Map.class);
                        } catch (Exception e) {
                            // Handle exception or log 
                            return Map.of("songId", playlistSong.getSongId(), "error", "Details not available");
                        }
                    })
                    .filter(song -> song != null)
                    .collect(Collectors.toList())
                : List.of();

        Map<String, Object> result = new HashMap<>();
        result.put("playlistId", playlist.getId());
        result.put("name", playlist.getName());
        result.put("songs", songs);

        return result;
    }
}

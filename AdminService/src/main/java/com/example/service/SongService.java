package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Album;
import com.example.entity.Notification;
import com.example.entity.Song;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AlbumRepository;
import com.example.repository.NotificationRepository;
import com.example.repository.SongRepository;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private RestTemplate restTemplate;


    //  GET ALL SONGS 
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    //  GET SONG BY ID 
    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found with id " + id));
    }

    //  CREATE SONG 
    public Song createSong(Song song) {
        // Ensure album exists if provided
        if (song.getAlbum() != null && song.getAlbum().getId() != null) {
            Album album = albumRepository.findById(song.getAlbum().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + song.getAlbum().getId()));
            song.setAlbum(album);
        }
        Song savedSong = songRepository.save(song);

        // Create notification
        Notification notification = new Notification("New song added: " + song.getName(), LocalDateTime.now());
        notificationRepository.save(notification);

        return savedSong;
    }

    //  UPDATE SONG 
    public Song updateSong(Long id, Song songDetails) {
        Song song = getSongById(id);

        song.setName(songDetails.getName());
        song.setSinger(songDetails.getSinger());
        song.setMusicDirector(songDetails.getMusicDirector());
        song.setReleaseDate(songDetails.getReleaseDate());
        song.setVisible(songDetails.getVisible());

        if (songDetails.getAlbum() != null && songDetails.getAlbum().getId() != null) {
            Album album = albumRepository.findById(songDetails.getAlbum().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + songDetails.getAlbum().getId()));
            song.setAlbum(album);
        }

        Song updatedSong = songRepository.save(song);

        // Create notification
        Notification notification = new Notification("Song updated: " + song.getName(), LocalDateTime.now());
        notificationRepository.save(notification);

        return updatedSong;
    }

    //  DELETE SONG
    public void deleteSong(Long id) {
        Song song = getSongById(id);
        songRepository.delete(song);

        Notification notification = new Notification("Song deleted: " + song.getName(), LocalDateTime.now());
        notificationRepository.save(notification);
    }

    //  SEARCH SONGS
    public List<Song> searchSongs(String name, String singer, String musicDirector, String albumName) {
        // If all parameters are null, return all songs
        if (name == null && singer == null && musicDirector == null && albumName == null) {
            return songRepository.findAll();
        }
        // Use custom repository method 
        return songRepository.findByNameContainingIgnoreCaseAndSingerContainingIgnoreCaseAndMusicDirectorContainingIgnoreCaseAndAlbum_NameContainingIgnoreCase(
                name != null ? name : "",
                singer != null ? singer : "",
                musicDirector != null ? musicDirector : "",
                albumName != null ? albumName : ""
        );
    }
    
    @SuppressWarnings("unchecked")
	public void notifyUsers(Song newSong) {
        // Fetch emails from UserService
        List<String> userEmails = restTemplate.getForObject(
                "http://localhost:8082/api/users/emails", List.class);

        for (String email : userEmails) {
            String url = "http://localhost:8083/api/email/send"
                    + "?to=" + email
                    + "&subject=New Song Added"
                    + "&message=" + "A new song " + newSong.getName() + " has been added!";

            restTemplate.postForObject(url, null, String.class);
        }
    }

}

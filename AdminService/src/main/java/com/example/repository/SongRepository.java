package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    // Custom search method
    List<Song> findByNameContainingIgnoreCaseAndSingerContainingIgnoreCaseAndMusicDirectorContainingIgnoreCaseAndAlbum_NameContainingIgnoreCase(
            String name, String singer, String musicDirector, String albumName
    );
}

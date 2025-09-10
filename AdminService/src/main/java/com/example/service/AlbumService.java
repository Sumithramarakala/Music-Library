package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Album;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AlbumRepository;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + id));
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album updateAlbum(Long id, Album albumDetails) {
        Album album = getAlbumById(id);

        album.setName(albumDetails.getName());
        album.setReleaseDate(albumDetails.getReleaseDate());

        return albumRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        Album album = getAlbumById(id);
        albumRepository.delete(album);
    }
}

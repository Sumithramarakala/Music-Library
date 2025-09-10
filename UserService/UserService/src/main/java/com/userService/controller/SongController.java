package com.userService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.userService.dto.SongDTO;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:8082")
public class SongController {

    @Autowired
    private RestTemplate restTemplate;

    private final String ADMIN_SERVICE_URL = "http://localhost:8080/api/songs";

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                ADMIN_SERVICE_URL,
                HttpMethod.GET,
                entity,
                List.class
        );

        List<Map<String, Object>> songsMap = response.getBody();

        List<SongDTO> songs = songsMap.stream().map(map -> {
            Map<String, Object> albumMap = (Map<String, Object>) map.get("album");
            SongDTO.AlbumDTO album = null;
            if (albumMap != null) {
                album = new SongDTO.AlbumDTO(
                        ((Number) albumMap.get("id")).longValue(),
                        (String) albumMap.get("name"),
                        (String) albumMap.get("releaseDate")
                );
            }

            return new SongDTO(
                    ((Number) map.get("id")).longValue(),
                    (String) map.get("name"),
                    (String) map.get("singer"),
                    (String) map.get("musicDirector"),
                    album
            );
        }).toList();

        return ResponseEntity.ok(songs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        String url = ADMIN_SERVICE_URL + "/" + id;
        Map<String, Object> map = restTemplate.getForObject(url, Map.class);

        if (map == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> albumMap = (Map<String, Object>) map.get("album");
        SongDTO.AlbumDTO album = null;
        if (albumMap != null) {
            album = new SongDTO.AlbumDTO(
                    ((Number) albumMap.get("id")).longValue(),
                    (String) albumMap.get("name"),
                    (String) albumMap.get("releaseDate")
            );
        }

        SongDTO song = new SongDTO(
                ((Number) map.get("id")).longValue(),
                (String) map.get("name"),
                (String) map.get("singer"),
                (String) map.get("musicDirector"),
                album
        );

        return ResponseEntity.ok(song);
    }

}

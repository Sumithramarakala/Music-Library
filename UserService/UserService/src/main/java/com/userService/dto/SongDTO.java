package com.userService.dto;

public class SongDTO {
    private Long id;
    private String name;
    private String singer;
    private String musicDirector;
    private AlbumDTO album;

    public static class AlbumDTO {
        private Long id;
        private String name;
        private String releaseDate;

        public AlbumDTO() {}

        public AlbumDTO(Long id, String name, String releaseDate) {
            this.id = id;
            this.name = name;
            this.releaseDate = releaseDate;
        }

        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getReleaseDate() { return releaseDate; }
        public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    }

    public SongDTO() {}

    public SongDTO(Long id, String name, String singer, String musicDirector, AlbumDTO album) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.musicDirector = musicDirector;
        this.album = album;
    }

    // getters and setters methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSinger() { return singer; }
    public void setSinger(String singer) { this.singer = singer; }
    public String getMusicDirector() { return musicDirector; }
    public void setMusicDirector(String musicDirector) { this.musicDirector = musicDirector; }
    public AlbumDTO getAlbum() { return album; }
    public void setAlbum(AlbumDTO album) { this.album = album; }
}

package com.learning.topdownloadedapps;

public class Entry {
    private String name, genre, artist, image, album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "name=" + name + '\n' +
                ", album=" + album + '\n' +
                ", artist=" + artist + '\n' +
                ", genre=" + genre + '\n' +
                ", image=" + image + '\n';
    }
}

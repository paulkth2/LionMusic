package com.madcamp.lionmusic;

import android.net.Uri;

public class SongItem {
    private String title;
    private String artist;
    private Uri songUri;
    private boolean liked;


    public SongItem(String title, String artist, Uri songUri, boolean liked) {
        this.title = title;
        this.artist = artist;
        this.songUri = songUri;
        this.liked = liked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public void setSongUri(Uri songUri) {
        this.songUri = songUri;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}

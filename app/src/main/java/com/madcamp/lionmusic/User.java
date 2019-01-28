package com.madcamp.lionmusic;

import java.util.HashMap;
import java.util.List;

public class User {
    private String email;
    private String nickname;
    private String profile;

    private HashMap<String, String> friends;
    private HashMap<String, String> likedArtist;
    private HashMap<String, String> tags;

    private List<HashMap<String, String>> likedSongs;
    private HashMap<String, String> songItem;

    public User() {
    }

    public User(String email, String nickname, String profile, HashMap<String, String> friends, HashMap<String, String> likedArtist, HashMap<String, String> tags, List<HashMap<String, String>> likedSongs, HashMap<String, String> songItem) {
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.friends = friends;
        this.likedArtist = likedArtist;
        this.tags = tags;
        this.likedSongs = likedSongs;
        this.songItem = songItem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public HashMap<String, String> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<String, String> friends) {
        this.friends = friends;
    }

    public HashMap<String, String> getLikedArtist() {
        return likedArtist;
    }

    public void setLikedArtist(HashMap<String, String> likedArtist) {
        this.likedArtist = likedArtist;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public List<HashMap<String, String>> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(List<HashMap<String, String>> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public HashMap<String, String> getSongItem() {
        return songItem;
    }

    public void setSongItem(HashMap<String, String> songItem) {
        this.songItem = songItem;
    }
}

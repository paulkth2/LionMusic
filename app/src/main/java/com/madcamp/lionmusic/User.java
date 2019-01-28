package com.madcamp.lionmusic;

import java.util.HashMap;
import java.util.List;

public class User {
    private String email;
    private String nickname;
    private String profile;

    private List<String> friends;
    private List<String> likedArtist;
    private List<String> tags;

    private List<HashMap<String, String>> likedSongs;
    private HashMap<String, String> songItem;

    public User() {
    }

    public User(String email, String nickname, String profile, List<String> friends, List<String> likedArtist, List<String> tags, List<HashMap<String, String>> likedSongs, HashMap<String, String> songItem) {
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getLikedArtist() {
        return likedArtist;
    }

    public void setLikedArtist(List<String> likedArtist) {
        this.likedArtist = likedArtist;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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

package com.madcamp.lionmusic;

public class FriendItem {
    private String friendEmail;

    public FriendItem(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public String getFriendEmail(){return friendEmail;}
    public void setFriendEmail(String newEmail){friendEmail = newEmail;}
}

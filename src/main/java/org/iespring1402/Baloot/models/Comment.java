package org.iespring1402.Baloot.models;

import java.util.HashMap;
import java.util.UUID;

public class Comment {
    private String id;
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;

    private HashMap<String, Integer> votes;

    public Comment() {
        super();
        this.votes = new HashMap<>();
        id = UUID.randomUUID().toString();
    }

    public Comment(String userEmail, int commodityId, String text, String date) {
        this.userEmail = userEmail;
        this.commodityId = commodityId;
        this.text = text;
        this.date = date;
        this.votes = new HashMap<>();
        id = UUID.randomUUID().toString();
    }

    public void voteComment(String username, int vote) {
        if (votes.get(username) != null && votes.get(username) == vote) {
            votes.put(username, 0);
        } else {
            votes.put(username, vote);
        }
    }

    public String getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public int likesCount() {
        int count = 0;
        for (Integer vote : votes.values()) {
            if (vote == 1) count++;
        }
        return count;
    }

    public int dislikesCount() {
        int count = 0;
        for (Integer vote : votes.values()) {
            if (vote == -1) count++;
        }
        return count;
    }
}

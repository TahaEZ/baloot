package org.iespring1402.Baloot.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;

import java.util.HashMap;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private int commodityId;
    private String text;
    private String date;

    @ElementCollection
    @CollectionTable(name = "comment_vote_mapping", joinColumns = {
            @JoinColumn(name = "comment_id", referencedColumnName = "id") })
    @MapKeyColumn(name = "username")
    @Column(name = "vote")
    private HashMap<String, Integer> votes;

    public Comment() {
        super();
        this.votes = new HashMap<>();
        id = UUID.randomUUID().toString();
    }

    public Comment(String username, int commodityId, String text, String date) {
        this.username = username;
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

    public String getUsername() {
        return username;
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
            if (vote == 1)
                count++;
        }
        return count;
    }

    public int dislikesCount() {
        int count = 0;
        for (Integer vote : votes.values()) {
            if (vote == -1)
                count++;
        }
        return count;
    }
}

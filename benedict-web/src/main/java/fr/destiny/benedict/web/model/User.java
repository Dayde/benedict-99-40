package fr.destiny.benedict.web.model;

import fr.destiny.api.model.DestinyResponsesDestinyProfileResponse;
import fr.destiny.api.model.UserUserInfoCard;

public class User {
    private String username;
    private long userId;
    private int platform;

    public User(UserUserInfoCard player) {
        this.username = player.getDisplayName();
        this.userId = player.getMembershipId();
        this.platform = player.getMembershipType();
    }

    public User(DestinyResponsesDestinyProfileResponse profile) {
        this(profile.getProfile().getData().getUserInfo());


    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public int getPlatform() {
        return platform;
    }
}

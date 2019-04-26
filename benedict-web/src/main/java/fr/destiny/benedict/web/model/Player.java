package fr.destiny.benedict.web.model;

public class Player {
    private String username;
    private int platform;

    public Player(String username, int platform) {
        this.username = username;
        this.platform = platform;
    }

    public String getUsername() {
        return username;
    }

    public int getPlatform() {
        return platform;
    }
}

package fr.destiny.benedict.web.model;

public class TokenResponse {

    private String token_type;

    private String access_token;
    private String expires_in;

    private String refresh_token;
    private String refresh_expires_in;

    private String membership_id;

    public TokenResponse() {
    }

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public String getMembership_id() {
        return membership_id;
    }
}

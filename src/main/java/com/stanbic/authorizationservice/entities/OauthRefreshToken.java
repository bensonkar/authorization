package com.stanbic.authorizationservice.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * @author bkariuki
 */
@Entity
@Table(name = "OAUTH_REFRESH_TOKEN")
public class OauthRefreshToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token_id;
    @Lob
    private Blob token;
    @Lob
    private Blob authentication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public Blob getToken() {
        return token;
    }

    public void setToken(Blob token) {
        this.token = token;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return "OauthRefreshToken{" +
                "id=" + id +
                ", token_id='" + token_id + '\'' +
                ", token='" + token + '\'' +
                ", authentication='" + authentication + '\'' +
                '}';
    }
}

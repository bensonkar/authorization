/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanbic.authorizationservice.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author bkariuki
 */
@Entity
@Table(name = "OAUTH_ACCESS_TOKEN")
public class OauthAccessToken implements Serializable {

    @Column(name = "TOKEN_ID")
    private String tokenId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "CLIENT_ID")
    private String clientId;
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "AUTHENTICATION_ID")
    private String authenticationId;
    @Lob
    @Column(name = "TOKEN")
    private Serializable token;
    @Lob
    @Column(name = "AUTHENTICATION")
    private Serializable authentication;

    public OauthAccessToken() {
    }

    public OauthAccessToken(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Serializable getToken() {
        return token;
    }

    public void setToken(Serializable token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Serializable getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Serializable authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

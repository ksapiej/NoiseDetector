package com.example.noisedetector.OAuth;

import com.example.noisedetector.Listener.AuthenticationListener;
import com.example.noisedetector.Util.AuthorizationUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class OAuthSession implements Session {

    @Getter
    @Setter
    private AuthenticationListener authenticationListener;

    public OAuthSession(AuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    @Override
    public boolean isLoggedIn() {

        String token = AuthorizationUtil.getRefreshToken();
        if(!token.equals(AuthorizationUtil.NO_REFRESH_FOUND)) return true;
        else return false;

    }

    @Override
    public void saveToken(String token) {
        AuthorizationUtil.putToken(token);
    }

    @Override
    public String getToken() {
        return AuthorizationUtil.getToken();
    }

    @Override
    public void saveRefreshToken(String token) {
        AuthorizationUtil.putRefreshToken(token);
    }

    @Override
    public String getRefreshToken() {
        return AuthorizationUtil.getRefreshToken();
    }



    @Override
    public void invalidate() {

        saveRefreshToken(AuthorizationUtil.NO_REFRESH_FOUND);
        saveToken(AuthorizationUtil.NO_TOKEN_FOUND);


        if(authenticationListener!=null) authenticationListener.onUserLoggedOut();
    }
}

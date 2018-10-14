package com.example.noisedetector.OAuth;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Krzysiek on 09.10.2018.
 */
@Getter
@Setter
public class OAuthResponse {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}

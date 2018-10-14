package com.example.noisedetector.OAuth;

import android.util.Log;

import com.example.noisedetector.Util.AuthorizationUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class TokenInterceptor implements Interceptor {

    private static final String TAG = TokenInterceptor.class.getSimpleName();
    private Session session;
    private static Object synchronizer = new Object();

    public TokenInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String token = session.getToken();
        Request.Builder builder = request.newBuilder();
        builder.header("Authorization", String.format("Bearer %s", token));
        request = builder.build();

        Log.i(TAG, String.format("Bearer %s", token));

        Response response = chain.proceed(chain.request());
        if(response.code() == 401) {
            synchronized (synchronizer) {
                String currentToken = session.getToken();
                if(currentToken != null && currentToken.equals(token)) {
                    //TODO odświeżanie tokena
                    int code = refreshToken();
                }

                if(session.getToken() != null && !session.getToken().equals(AuthorizationUtil.NO_TOKEN_FOUND)) {
                    builder.header("Authorization", String.format("Bearer %s", session.getToken()));
                    request = builder.build();
                    return chain.proceed(request);
                }
            }
        }
        Log.i(TAG, response.header("Authorization") +  "-asd");
        return response;
    }



    private int refreshToken() {
        return 0;
    }
}

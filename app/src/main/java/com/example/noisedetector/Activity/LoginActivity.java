package com.example.noisedetector.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.noisedetector.Fragment.auth.AuthorizationFragment;
import com.example.noisedetector.Listener.UserListener;
import com.example.noisedetector.Model.User;
import com.example.noisedetector.R;
import com.example.noisedetector.RetrofitConnector;
import com.example.noisedetector.Util.UserUtil;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class LoginActivity extends AppCompatActivity implements UserListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RetrofitConnector.getInstance().getUser(this);
        //onUserNotFound();
    }

    private void replaceFragment(Fragment fragment, boolean addToBackstack) {
        if(addToBackstack) getSupportFragmentManager().beginTransaction().replace(R.id.login_activity_content, fragment).addToBackStack(null).commit();
        else getSupportFragmentManager().beginTransaction().replace(R.id.login_activity_content, fragment).commit();
    }

    @Override
    public void onUserFound(User user) {
        UserUtil.setUser(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserNotFound() {
        replaceFragment(new AuthorizationFragment(), false);
    }
}

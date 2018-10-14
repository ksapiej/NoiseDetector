package com.example.noisedetector.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noisedetector.DetectNoise;
import com.example.noisedetector.Fragment.main.MainFragment;
import com.example.noisedetector.R;

public class MainActivity extends AppCompatActivity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Defined SoundLevelView in main.xml file
        setContentView(R.layout.activity_main);
        replaceFragment(new MainFragment(), false);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackstack) {
        animateReplaceFragment(fragment, addToBackstack);
    }

    private void animateReplaceFragment(Fragment fragment, boolean addToBackstack) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_fragment_horizontal, R.anim.exit_fragment_horizontal, R.anim.pop_enter_fragment_horizontal, R.anim.pop_exit_fragment_horizontal);
        fragmentTransaction.replace(R.id.content, fragment);
        if(addToBackstack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}

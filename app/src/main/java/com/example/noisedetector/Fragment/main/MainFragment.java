package com.example.noisedetector.Fragment.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noisedetector.DetectNoise;
import com.example.noisedetector.Model.Result;
import com.example.noisedetector.NoiseDetector;
import com.example.noisedetector.R;
import com.example.noisedetector.Util.Constants;
import com.example.noisedetector.Util.UIUpdateHandler;
import com.example.noisedetector.service.ResultService;
import com.example.noisedetector.service.TimerService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Krzysiek on 12.10.2018.
 */

public class MainFragment extends Fragment implements MainView {

    private final static String TAG = MainFragment.class.getSimpleName();
    private static final int POLL_INTERVAL = 300;
    // Handler to update the UI every second when the timer is running
    private final Handler mUpdateTimeHandler = new UIUpdateHandler(this);
    SharedPreferences prefs;
    @BindView(R.id.login_component)
    LinearLayout loginComponent;
    int RECORD_AUDIO = 0;
    @BindView(R.id.status)
    TextView mStatusView;
    @BindView(R.id.tv_noice)
    TextView tv_noice;
    @BindView(R.id.progressBar1)
    ProgressBar bar;
    @BindView(R.id.start_button)
    Button startBTN;
    @BindView(R.id.stop_button)
    Button stopBTN;
    @BindView(R.id.time)
    TextView timerTextView;
    ResultService resultService;
    private MainPresenter presenter;
    private Unbinder unbinder;
    private boolean mRunning = false;
    private int mThreshold;
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler();
    private DetectNoise mSensor;
    private TimerService timerService = new TimerService();
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            //Log.i("Noise", "runnable mPollTask");
            updateDisplay("Monitoring Voice...", amp);

            if ((amp > mThreshold)) {
                callForHelp(amp);
                //Log.i("Noise", "==== onCreate ===");
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };
    private Runnable mSleepTask = new Runnable() {
        public void run() {
            //Log.i("Noise", "runnable mSleepTask");
            start();
        }
    };

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        mSensor = new DetectNoise();
        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        presenter.attachView(this);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mRunning) {
                    mRunning = true;
                    start();
                }
                //Create an intent that will start TimerService.
                Intent startIntent = new Intent(getActivity(), TimerService.class);
                startIntent.setAction(Constants.ACTION.START_SERVICE);
                getActivity().startService(startIntent);
                timerService.startTimer();
                updateUIStartRun();
            }
        });
        stopBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
                Intent stopIntent = new Intent(getActivity(), TimerService.class);
                stopIntent.setAction(Constants.ACTION.STOP_SERVICE);
                getActivity().startService(stopIntent);
                timerService.stopTimer();
                updateUIStopRun();
                /* get training results*/
                Date date = new Date();
                long trainingDate = date.getTime();
                String login = prefs.getString(NoiseDetector.getAppContext().getResources().getString(R.string.email_key), "");
                //TODO  przypisac wartosci do tablicy wynikow

                ArrayList<Long> snapTimes = null;
                sendResult(trainingDate, snapTimes, login);
                timerTextView.setText("00:00:00");
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i("Noise", "==== onResume ===");
        initializeApplicationConstants();

    }

    private void start() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_AUDIO);
        }
        mSensor.start();
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        Log.d("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        bar.setProgress(0);
        updateDisplay("stopped...", 0.0);
        mRunning = false;

    }


    /**
     * Updates the UI when a run starts
     */
    private void updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(Constants.NOTIFICATION.MSG_UPDATE_TIME);
    }

    /**
     * Updates the UI when a run stops
     */
    private void updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(Constants.NOTIFICATION.MSG_UPDATE_TIME);
    }

    public void updateUITimer() {
        timerTextView.setText(timerService.formatMilliSecondsToTime(timerService.elapsedTime()));
    }

    private void initializeApplicationConstants() {
        // Set Noise Threshold
        mThreshold = 8;

    }

    private void updateDisplay(String status, double signalEMA) {
        mStatusView.setText(status);
        //
        bar.setProgress((int) signalEMA);
        Log.d("SONUND", String.valueOf(signalEMA));
        tv_noice.setText(signalEMA + "dB");
    }


    private void callForHelp(double signalEMA) {
        //stop();
        // Show alert when noise thersold crossed
        Toast.makeText(getActivity().getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
                Toast.LENGTH_LONG).show();
        Log.d("SONUND", String.valueOf(signalEMA));
        tv_noice.setText(signalEMA + "dB");
    }

    //TODO uzupelnic ciało metody

    private void sendResult(long date, ArrayList<Long> snapTimes, String login) {
        Result result = new Result(date, snapTimes, login);
        Call<Void> call = resultService.sendResult(result);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        Log.i(TAG, "udalo sie wyslac result");
                    }
                } else {
                    Log.i(TAG, "result sie wypierdolil w kosmos on response");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "result sie wypierdolil w kosmos onFailure");
            }
        });
    }
}

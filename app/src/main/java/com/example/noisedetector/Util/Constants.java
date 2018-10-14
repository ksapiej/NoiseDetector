package com.example.noisedetector.Util;

/**
 * Created by Krzysiek on 14.10.2018.
 */

public class Constants {
    public interface ACTION {
        public static final String MAIN_ACTION = "mainAction";
        public static final String START_SERVICE = "startService";
        public static final String STOP_SERVICE = "stopService";
    }

    public interface NOTIFICATION {
        public static final int TIMER_SERVICE_ID = 120;
        public final static int MSG_UPDATE_TIME = 0;
    }
}

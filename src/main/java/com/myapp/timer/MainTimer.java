package com.myapp.timer;

import com.myapp.main.main;

/**
 * Created by gaorui on 16/12/31.
 */
public class MainTimer {
    public static void main(String[] args) {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new main(), 1000 * 60 );
    }
}


package com.example.noisedetector.Fragment.main;

/**
 * Created by Krzysiek on 12.10.2018.
 */

public class MainPresenter implements MainPresenterInterface{
    MainView view;

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}

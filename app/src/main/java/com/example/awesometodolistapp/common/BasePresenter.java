package com.example.awesometodolistapp.common;

/**
 * Created by sahil-mac on 13/05/18.
 */


/**
 * Every presenter should have a subscribe() and
 * unsubscribe() method which should be called when
 * the view is not ready to take requests.
 *
 * Therefore, defining these methods in base class makes sense.
 */
public interface BasePresenter {

    void subscribe();
    void unsubscribe();
}

package com.ninthsemester.ui.common;

/**
 * Created by sahil-mac on 13/05/18.
 */

/**
 * Base class for View. If the View has
 * child View which then implements view, then setPresenter() will
 * be useful.
 * @param <T>
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
}

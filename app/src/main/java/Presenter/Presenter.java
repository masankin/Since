package Presenter;

import ViewInterface.SinceInterface;

/**
 * Created by SHLSY on 2015/6/2.
 */
public class Presenter {
    public static final String TAG =Presenter.class.getSimpleName();
    SinceInterface since;
    public Presenter(SinceInterface since){
        this.since=since;
    }

    public void Start_Add(){
        since.Add();
    }


}

package Presenter;

import android.database.sqlite.SQLiteDatabase;

import Bean.SinceBean;
import Model.SinceModel;
import ViewInterface.SinceInterface;

/**
 * Created by SHLSY on 2015/6/2.
 */
public class Presenter {
    public static final String TAG =Presenter.class.getSimpleName();
    SinceInterface sinceInterface;
    SinceModel sinceModel;
    public Presenter(SinceInterface sinceInterface){
        this.sinceInterface=sinceInterface;sinceModel=new SinceModel();
    }

    public void Add_ResultHandle(SinceBean sinceBean, SQLiteDatabase DB) {
        sinceModel.InsertSince(sinceBean,DB);
    }
    public void Start_Add(){
        sinceInterface.Add();
    }


}

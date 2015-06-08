package Model;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import Bean.SinceBean;
import DAO.SinceDAO;

/**
 * Created by SHLSY on 2015/6/2.
 */
public class SinceModel {
    getSinceListener listener;
    ArrayList<SinceBean> list;

    public void setListener(getSinceListener listener) {
        this.listener = listener;
    }


    public void InsertSince(SinceBean sinceBean, SQLiteDatabase DB) {
        new SinceThread(sinceBean, DB).start();
    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            list= (ArrayList<SinceBean>) msg.obj;
            listener.getFinished(list);
        }
    };
    //开始时获取所有的SinceBean
    public void GetAllSince(final SQLiteDatabase DB){

        new Thread(){
            @Override
            public void run() {
                ArrayList<SinceBean> list=new ArrayList<SinceBean>();
                list =SinceDAO.QueryAll(DB);
                Message message =Message.obtain();
                message.obj=list;
                handler.sendMessage(message);
            }
        }.start();
    }
    //添加Since的Thread
    public class SinceThread extends Thread {
        public SinceBean sinceBean;
        private SQLiteDatabase DB;

        public SinceThread(SinceBean sinceBean, SQLiteDatabase DB) {
            this.sinceBean = sinceBean;
            this.DB = DB;
        }

        @Override
        public void run() {
            super.run();
            SinceDAO.insert(DB, sinceBean);
        }
    }

    public interface  getSinceListener{
       void getFinished (ArrayList<SinceBean> list);
    }
}

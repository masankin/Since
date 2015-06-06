package Model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import Bean.SinceBean;
import DAO.SinceDAO;

/**
 * Created by SHLSY on 2015/6/2.
 */
public class SinceModel {


    public void InsertSince(SinceBean sinceBean, SQLiteDatabase DB) {
        new SinceThread(sinceBean, DB).start();
    }


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
            Log.v("sqk", "插入数据库完成");
        }
    }
}

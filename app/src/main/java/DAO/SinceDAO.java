package DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Bean.SinceBean;

/**
 * Created by SHLSY on 2015/6/1.
 */
public class SinceDAO {

    public static void insert (SQLiteDatabase DB,SinceBean SB){
        ContentValues values =new ContentValues();
        values.put("img_url",SB.getImg_url());
        values.put("content",SB.getContent());
        values.put("days_num",SB.getDays_num());
        values.put("is_forever",SB.getIs_forever());
        values.put("year",String.valueOf(SB.getDate().getYear() + 1900));
        values.put("month",String.valueOf(SB.getDate().getMonth()+1));
        values.put("day",String.valueOf(SB.getDate().getDate()));
//        DB.insert("Since",null,values);
    }

    public static ArrayList<SinceBean> QueryAll (SQLiteDatabase DB){
        ArrayList<SinceBean> list =new ArrayList<SinceBean>();
        SinceBean sinceBean;
        Cursor cursor =DB.query("Since",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            sinceBean =new SinceBean();
            sinceBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
            sinceBean.setDays_num(cursor.getInt(cursor.getColumnIndex("days_num")));
            sinceBean.setImg_url(cursor.getString(cursor.getColumnIndex("img_url")));
            sinceBean.setIs_forever(cursor.getInt(cursor.getColumnIndex("is_forever")));
            list.add(sinceBean);
        }
        cursor.close();
        return  list;
    }

    public static void delete (SQLiteDatabase DB,SinceBean SB){
        DB.delete("Since","content = ?",new String[]{SB.getContent()});

    }

    public static void Update(SQLiteDatabase DB,SinceBean SB,String OldContent){
        ContentValues values = new ContentValues();
        values.put("img_url",SB.getImg_url());
        values.put("content",SB.getContent());
        values.put("days_num",SB.getDays_num());
        values.put("is_forever",SB.getIs_forever());
        DB.update("Since",values,"content = ?",
                new String[]{OldContent});
    }




}

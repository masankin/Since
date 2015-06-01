package DAO;

import android.content.ContentValues;
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
        DB.insert("Since",null,values);
    }
    public static ArrayList<SinceBean> QueryAll (SQLiteDatabase DB){
        ArrayList<SinceBean> list =new ArrayList<SinceBean>();

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

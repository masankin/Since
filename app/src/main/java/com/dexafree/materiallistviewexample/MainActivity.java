package com.dexafree.materiallistviewexample;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicButtonsCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.gc.materialdesign.widgets.Dialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import Bean.SinceBean;
import DB.DBHelper;
import Presenter.Presenter;
import Utils.CalendarUtils;
import ViewInterface.SinceInterface;


public class MainActivity extends Activity implements SinceInterface,View.OnClickListener{
    ArrayList<SinceBean> list;
    private MaterialListView mListView;
    private DBHelper dbHelper;
    private SQLiteDatabase DB;
    private Presenter presenter;
    FloatingActionButton AddButton,ShareButton;
    static final int AddRequestCode = 1;
    static final int ModifyRequestCode=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        InitView();
        SetListeners();

    }

    private void SetListeners() {
        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {
                Log.v("sqk","删除了"+position);
                presenter.delete_since(list.get(list.size()-position-1),DB);
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
              presenter.Modify();
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {
              //Doing Nothing
            }
        });
    }

    private void InitView() {
        presenter=new Presenter(this);
        dbHelper =new DBHelper(this);
        DB=dbHelper.getWritableDatabase();
        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);
        AddButton = (FloatingActionButton) findViewById(R.id.action_a);
        AddButton.setTitle("添加Past");
        AddButton.setOnClickListener(this);
        ShareButton= (FloatingActionButton) findViewById(R.id.action_b);
        ShareButton.setOnClickListener(this);
        ShareButton.setTitle("分享");
        // Fill the array with mock content
        fillArray();
    }

    private void fillArray() {
       presenter.getALLSince(DB);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_clear:
                final Dialog dialog=new Dialog(this,"Past","将清除所有非永恒的Past，是否继续？");
                dialog.addCancelButton("CANCEL");
                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListView.clearDismiss();
                        Toast.makeText(MainActivity.this,"done",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Add() {
        Intent i =new Intent(this,AddActivity.class);
        startActivityForResult(i,AddRequestCode);
    }

    @Override
    public void Share() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.app_name));
        share.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.app_description)+"---"
                        + "\n"+"Past，一款充满回忆的APP"+"\n"
                        + "个人博客：sqk.pub");
        startActivity(Intent.createChooser(share,
                getString(R.string.app_name)));

    }

    @Override
    public void Modify() {
        Intent i =new Intent(this,ModifyActivity.class);
        startActivityForResult(i,ModifyRequestCode);
    }

    @Override
    public void Display(ArrayList<SinceBean> list) {
        this.list=list;
        if(list.size()==0){
            Toast.makeText(this,"您在这的回忆空空如也~",Toast.LENGTH_SHORT).show();
        }else{
            for(int i=0;i<list.size();i++){
                SinceBean since=list.get(i);
                GenerateCards(since);
            }
        }
    }

    private void GenerateCards(SinceBean since) {
        BasicButtonsCard card = new BasicButtonsCard(this);
        card.setTitle(CalendarUtils.get_between_days(new Date(), since.getDate()) + "   DAYS!");
        card.setDescription("The  "+since.getContent() + " has passed ");
        card.setLeftButtonText("");
        card.setRightButtonText("");
        SetDisMissibleAndDivide(card, since);
        mListView.addAtStart(card);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AddRequestCode:if(resultCode==RESULT_OK) {
                SinceBean since = (SinceBean) data.getSerializableExtra("Since");
                GenerateCards(since);
                presenter.Add_ResultHandle(since,DB);
            } break;
        }

    }
    private void SetDisMissibleAndDivide(BasicButtonsCard card,SinceBean since){

            card.setDismissible(PastIsDismissible(since));
            card.setDividerVisible(PastIsDismissible(since));

    }
    private boolean PastIsDismissible(SinceBean since){
        //为永恒，不能删除
        if(since.getIs_forever()==1){
            return false;
        }
        //默认为可以删除
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_a:  presenter.Start_Add();   break;
            case R.id.action_b:  presenter.Share();  break;
        }
    }
}

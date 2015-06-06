package com.dexafree.materiallistviewexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dexafree.materialList.cards.BasicImageButtonsCard;
import com.dexafree.materialList.cards.BigImageCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import Bean.SinceBean;
import DB.DBHelper;
import Presenter.Presenter;
import Utils.BitmapUtils;
import Utils.CalendarUtils;
import ViewInterface.SinceInterface;


public class MainActivity extends Activity implements SinceInterface,View.OnClickListener{

    private Context mContext;
    private MaterialListView mListView;
    private DBHelper dbHelper;
    private SQLiteDatabase DB;
    private Presenter presenter;
    FloatingActionButton AddButton,ShareButton;
    static final int AddRequestCode = 1;
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

            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {

            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });
    }

    private void InitView() {
        mContext = this;
        presenter=new Presenter(this);
        dbHelper =new DBHelper(this);
        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);
        AddButton = (FloatingActionButton) findViewById(R.id.action_a);
        AddButton.setTitle("加条紫虫");
        AddButton.setOnClickListener(this);
        ShareButton= (FloatingActionButton) findViewById(R.id.action_b);
        ShareButton.setOnClickListener(this);
        ShareButton.setTitle("分享");
        // Fill the array with mock content
        fillArray();
    }

    private void fillArray() {
        for (int i = 0; i < 4; i++) {
            BigImageCard card = new BigImageCard(this);
            card.setTitle("Your title");
            card.setDescription("Your description");
            card.setDrawable(R.drawable.photo);
            card.setDismissible(true);
            mListView.add(card);
        }
    }



    private void addMockCardAtStart(){
        BasicImageButtonsCard card = new BasicImageButtonsCard(this);
        card.setDrawable(R.drawable.ic_launcher);
        card.setTitle("Hi there");
        card.setDescription("I've been added on top!");
        card.setLeftButtonText("LEFT");
        card.setRightButtonText("RIGHT");
        card.setTag("BASIC_IMAGE_BUTTONS_CARD");

        card.setDismissible(true);

        mListView.addAtStart(card);

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
                mListView.clear();
                break;
            case R.id.action_add_at_start:
                addMockCardAtStart();
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

    }

    @Override
    public void Modify() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AddRequestCode:if(resultCode==RESULT_OK) {
                SinceBean since = (SinceBean) data.getSerializableExtra("Since");
                BasicImageButtonsCard card = new BasicImageButtonsCard(this);
                card.setDrawable(new BitmapDrawable(BitmapUtils.getBitmapFromPath(since.getImg_url(),300,500)));
                card.setTitle(since.getContent()+" has passed ");
                card.setDescription(CalendarUtils.get_between_days(new Date(),since.getDate())+"days");
                card.setLeftButtonText("编辑");
                card.setRightButtonText("删除");
                card.setTag(since.getImg_url());
                card.setDismissible(since.getIs_forever()==1?true:false);
                mListView.addAtStart(card);
                DB=dbHelper.getWritableDatabase();
                presenter.Add_ResultHandle(since,DB);
            } break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_a:  presenter.Start_Add();   break;
            case R.id.action_b:     break;
        }
    }
}

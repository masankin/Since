package com.dexafree.materiallistviewexample;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Date;

import Bean.SinceBean;
import Utils.CalendarUtils;
import Utils.UriUtils;


public class AddActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
    final Calendar calendar = Calendar.getInstance();
    final static int SELECT_PICTURE=1;
    public static final String DATEPICKER_TAG = "datepicker";
    ButtonRectangle DatePicker;
    MaterialEditText DateEdit, ContentEdit, ImgEdit;
    ButtonFlat SureButton, CencelButton;
    ContentResolver mContentResolver;
    private String ImgPath;
    private static boolean hasChooseImg=false;
    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        getActionBar().setDisplayShowHomeEnabled(false);
        mContentResolver = getContentResolver();
        InitViews();
        SetListeners();
    }

    private void InitViews() {
        ContentEdit = (MaterialEditText) findViewById(R.id.ContentEdit);
        DateEdit = (MaterialEditText) findViewById(R.id.DateEdit);
        DateEdit.setText(CalendarUtils.format.format(new Date()));
        ImgEdit = (MaterialEditText) findViewById(R.id.ImgEdit);
        SureButton = (ButtonFlat) findViewById(R.id.button);
        CencelButton = (ButtonFlat) findViewById(R.id.button2);
    }

    private void SetListeners() {
        ImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(1985, calendar.get(Calendar.YEAR));
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        SureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(hasChooseImg){
                SinceBean since = new SinceBean();
                since.setContent(ContentEdit.getText().toString());
                since.setDays_num(0);
                since.setIs_forever(0);
                since.setImg_url(ImgPath);
                since.setDate(new Date());
                Intent i = new Intent();
                i.putExtra("Since", since);
                setResult(RESULT_OK, i);
                finish();}else{
                   String Content =ContentEdit.getText().toString();
                   String message=Content.equals("") ? "请输入描述!" :"请选择图片!";
                   final Dialog dialog=new Dialog(AddActivity.this,"Past",message);

                   dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();

               }
            }
        });
        CencelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ImgPath=UriUtils.getImageAbsolutePath(this, uri);
            ImgEdit.setText(ImgPath);
            hasChooseImg=true;
        }
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {
        DateEdit.setText(new StringBuffer().append(i).append("-").append(i2 + 1).append("-").append(i3).toString());
    }
}

package com.dexafree.materiallistviewexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Date;

import Bean.SinceBean;


public class AddActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{
    final Calendar calendar = Calendar.getInstance();
    public static final String DATEPICKER_TAG = "datepicker";
    ButtonRectangle DatePicker;
    MaterialEditText DateEdit,ContentEdit;
    ButtonRectangle SureButton,CencelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        getActionBar().setDisplayShowHomeEnabled(false);
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        ContentEdit = (MaterialEditText) findViewById(R.id.ContentEdit);
        DateEdit = (MaterialEditText) findViewById(R.id.DateEdit);
        SureButton= (ButtonRectangle) findViewById(R.id.button);
        CencelButton = (ButtonRectangle) findViewById(R.id.button2);
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
                SinceBean since =new SinceBean();
                since.setContent(ContentEdit.getText().toString());
                since.setDays_num(0);
                since.setIs_forever(0);
                since.setImg_url("");
                since.setDate(new Date());
                Intent i =new Intent();
                i.putExtra("Since",since);
                setResult(RESULT_OK,i);
                finish();
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
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {
        DateEdit.setText(new StringBuffer().append(i).append("-").append(i2+1).append("-").append(i3).toString());
    }
}

package com.dexafree.materiallistviewexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dexafree.materialList.cards.BasicImageButtonsCard;
import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.cards.SimpleCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;

import DB.DBHelper;


public class MainActivity extends Activity {
    private Context mContext;
    private MaterialListView mListView;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowHomeEnabled(false);
        // Save a reference to the context
        mContext = this;

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);
        // Fill the array with mock content
        fillArray();

        dbHelper =new DBHelper(this);
        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {

                // Recover the tag linked to the Card
                String tag = card.getTag().toString();

                // Show a toast
                Toast.makeText(mContext, "You have dismissed a "+tag, Toast.LENGTH_SHORT).show();
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView view, int position) {
                Log.d("CARD_TYPE", view.getTag().toString());
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {
                Log.d("LONG_CLICK", view.getTag().toString());
            }
        });

    }

    private void fillArray() {
        for (int i = 0; i < 4; i++) {
            BasicImageButtonsCard card = new BasicImageButtonsCard(this);
            card.setDescription("描述"+i);
            card.setTitle("标题" + i);
            card.setDrawable(R.drawable.dog);
            card.setTag("BASIC_IMAGE_BUTTON_CARD");
            card.setLeftButtonText("LEFT");
            card.setRightButtonText("RIGHT");

            if (i % 2 == 0)
                card.setDividerVisible(true);

            card.setOnLeftButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                    ((SimpleCard) card).setTitle("CHANGED ON RUNTIME");
                }
            });

            card.setOnRightButtonPressedListener(new OnButtonPressListener() {
                @Override
                public void onButtonPressedListener(View view, Card card) {
                    Toast.makeText(mContext, "You have pressed the right button on card " + ((SimpleCard) card).getTitle(), Toast.LENGTH_SHORT).show();
                    mListView.remove(card);
                }
            });
            card.setDismissible(true);
            mListView.add(card);
        }
    }



    private void addMockCardAtStart(){
        BasicImageButtonsCard card = new BasicImageButtonsCard(this);
        card.setDrawable(R.drawable.dog);
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
}

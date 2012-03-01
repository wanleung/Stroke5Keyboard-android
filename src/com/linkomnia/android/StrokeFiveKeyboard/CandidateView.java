package com.linkomnia.android.StrokeFiveKeyboard;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CandidateView extends LinearLayout {
    
    private Context ctx;
    private ScrollView scrollView;
    private Button leftButton;
    private Button rightButton;
    private LinearLayout wordsView;
    private StrokeFiveKeyboard mDelegate;

    public CandidateView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.ctx = context;
        
        this.wordsView = new LinearLayout(ctx);
        this.leftButton = new Button(ctx);
        this.leftButton.setText("<");
        //this.leftButton.
        this.rightButton = new Button(ctx);
        this.rightButton.setText(">");
        this.scrollView = new ScrollView(ctx);
        this.scrollView.addView(wordsView);
        //this.setNumColumns(3);
        this.addView(this.leftButton);
        this.addView(scrollView);
        //this.addView(wordsView);
        this.addView(rightButton);
        this.setBackgroundColor(R.color.candidate_normal);
        Log.d("WANLEUNG", getHeight()+"");
    }
    
    public void setDelegate(StrokeFiveKeyboard target) {
        mDelegate = target;
    }
    
    public void setSuggestion(ArrayList<String> list) {
        wordsView.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            Button button = new Button(ctx);
            button.setText(list.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mDelegate.onChooseWord(((Button)v).getText().toString());
                }
            });

            wordsView.addView(button);
        }
        this.invalidate();
    }
}

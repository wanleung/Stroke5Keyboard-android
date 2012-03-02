package com.linkomnia.android.StrokeFiveKeyboard;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.math.*;

public class CandidateView extends LinearLayout {
    
    private Context ctx;
    private Button leftButton;
    private Button rightButton;
    private StrokeFiveKeyboard mDelegate;
    
    private ArrayList<Button> wordbuttonList;
    private int wordlevel;

    private ArrayList<String> wordList;
    
    private int show_div;
    private int show_rem;
    private int show_max;

    
    public CandidateView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.ctx = context;
        
        this.setGravity(Gravity.FILL);
        
        this.leftButton = new Button(ctx);
        this.leftButton.setText("<");
        this.leftButton.setOnClickListener(new ButtonOnClickListener(this, 0));
        

        this.rightButton = new Button(ctx);
        this.rightButton.setText(">");
        this.rightButton.setOnClickListener(new ButtonOnClickListener(this, 1));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        
        this.addView(this.leftButton, lp);

        
        this.wordbuttonList = new ArrayList<Button>();
        for (int i = 0; i < 5; i++) {
            Button b = new Button(ctx);
            this.wordbuttonList.add(b);
            this.addView(b, lp);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String str = ((Button)v).getText().toString();
                    if (!(str.isEmpty() || str.equals("\u3000"))) {
                        mDelegate.onChooseWord(((Button)v).getText().toString());
                        wordlevel = 0;
                    }
                }
            });

        }
        
        this.addView(rightButton, lp);
        this.setBackgroundColor(R.color.candidate_normal);
    }
    
    public void setDelegate(StrokeFiveKeyboard target) {
        mDelegate = target;
    }
    
    public void setSuggestion(ArrayList<String> list) {
        wordlevel = 0;
        this.wordList = list;
        show_div = (this.wordList.size()-1) / 5;
        show_rem = (this.wordList.size()-1) % 5;
        
        show_max = show_div;//show_rem == 0?show_div-1:show_div;
        Log.d("WANLEUNG", this.wordList.size() + " " + show_div + " " + show_rem + " " + show_max);
        this.showWords();
    }
    
    protected void showWords() {
        int show = (this.wordlevel == show_max)?show_rem:5;
        for (int i = 0; i < 5; i++) {
            Log.d("WANLEUNG", this.wordList.size() + " wordlevel " + wordlevel + " show " + show + " index " + (this.wordlevel * 5 + i));
            Button b = this.wordbuttonList.get(i);
            if (i > show) {
                b.setText("\u3000");
            } else {
                Log.d("WANLEUNG", (this.wordlevel * 5 + i) + "");
                b.setText(this.wordList.get(this.wordlevel * 5 + i));
            }
        }
        this.invalidate();
    }
    
    protected int getWordlevel() {
        return this.wordlevel;
    }
    
    protected void setWordlevel(int i) {
        this.wordlevel = Math.max(i,0);
        this.wordlevel = Math.min(this.show_div, this.wordlevel);
    }
}

class ButtonOnClickListener implements View.OnClickListener {
    private CandidateView parnet;
    private int type;
    
    public ButtonOnClickListener(CandidateView p, int t) {
        this.type = t; //0 = left, 1 = right
        this.parnet = p;
    }
    
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (this.type == 0) {
            parnet.setWordlevel(parnet.getWordlevel()-1);
        } else {
            parnet.setWordlevel(parnet.getWordlevel()+1);
        }
        parnet.showWords();
    }
    
}
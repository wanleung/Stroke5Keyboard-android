package com.linkomnia.android.StrokeFiveKeyboard;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.math.*;

public class CandidateView extends LinearLayout {
    
    private Context ctx;
    private Button leftButton;
    private Button rightButton;
    private LinearLayout topbar;
    private LinearLayout bottombar;
    private LinearLayout wordbar;
    private StrokeFiveKeyboard mDelegate;
    
    private int result_max;
    
    private ArrayList<Button> inputBox;
    private ArrayList<Button> wordbuttonList;
    private int wordlevel;

    private ArrayList<String> wordList;
    
    private int show_div;
    private int show_rem;
    private int show_max;

    public CandidateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        result_max = Integer.parseInt(this.getResources().getString(R.string.result_max));
        
        this.setBackgroundColor(getResources().getColor(R.color.candidate_background));
        
        this.leftButton = (Button) findViewById(R.id.arrow_left);
        this.leftButton.setText("<");
        this.leftButton.setOnClickListener(new ButtonOnClickListener(this, 0));
        //this.leftButton.setBackgroundColor(R.color.candidate_background);
        //this.leftButton.setTextColor(R.color.candidate_normal);

        this.rightButton = (Button) findViewById(R.id.arrow_right);
        this.rightButton.setText(">");
        this.rightButton.setOnClickListener(new ButtonOnClickListener(this, 1));
        //this.rightButton.setTextColor(R.color.candidate_normal);
        //this.rightButton.setBackgroundColor(R.color.candidate_background);

        this.wordbuttonList = new ArrayList<Button>();
        this.wordbar = (LinearLayout)this.findViewById(R.id.wordbar);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < result_max; i++) {
            Button b = new Button(ctx);
            //b.setTextColor(R.color.candidate_normal);
            //b.setBackgroundColor(getResources().getColor(R.color.candidate_background));
            b.setTextSize(this.getResources().getDimension(R.dimen.keychar_size));
            this.wordbuttonList.add(b);
            this.wordbar.addView(b, lp);
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
        
        this.bottombar = (LinearLayout)this.findViewById(R.id.bottombar);
        this.inputBox = new ArrayList<Button>();
        for (int i = 0; i < 5; i++) {
            Button b = new Button(ctx);
            //b.setTextColor(R.color.candidate_normal);
            b.setText("\u3000");
            b.setBackgroundColor(getResources().getColor(R.color.candidate_background));
            b.setTextSize(this.getResources().getDimension(R.dimen.keychar_size));
            this.inputBox.add(b);
            this.bottombar.addView(b, lp);
        }
    }

    
    public void setDelegate(StrokeFiveKeyboard target) {
        mDelegate = target;
    }
    
    public void setSuggestion(ArrayList<String> list) {
        wordlevel = 0;
        this.wordList = list;
        show_div = (this.wordList.size()-1) / result_max;
        show_rem = (this.wordList.size()-1) % result_max;
        
        show_max = show_div;//show_rem == 0?show_div-1:show_div;
        Log.d("WANLEUNG", this.wordList.size() + " " + show_div + " " + show_rem + " " + show_max);
        this.showWords();
    }
    
    public void updateInputBox(String input) {
        for (int i = 0; i < 5; i++) {
            if (i<input.length()) {
                this.inputBox.get(i).setText(String.format("%c", input.charAt(i)));
            } else {
                this.inputBox.get(i).setText("\u3000");
            }
        }
    }
    
    protected void showWords() {
        int show = (this.wordlevel == show_max)?show_rem:result_max;
        for (int i = 0; i < result_max; i++) {
            Log.d("WANLEUNG", this.wordList.size() + " wordlevel " + wordlevel + " show " + show + " index " + (this.wordlevel * 5 + i));
            Button b = this.wordbuttonList.get(i);
            if (i > show) {
                b.setText("\u3000");
            } else {
                Log.d("WANLEUNG", (this.wordlevel * result_max + i) + "");
                b.setText(this.wordList.get(this.wordlevel * result_max + i));
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
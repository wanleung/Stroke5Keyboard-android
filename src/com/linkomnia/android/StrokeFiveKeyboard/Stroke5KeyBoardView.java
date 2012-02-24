package com.linkomnia.android.StrokeFiveKeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;
import android.util.Log;

public class Stroke5KeyBoardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;

    public Stroke5KeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Stroke5KeyBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onLongPress(Key key) {
    	Log.d("WANLEUNG", "onLongPress");
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }
}
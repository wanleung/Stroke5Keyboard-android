/*
    Stroke5 Chinese Input Method for Android
    Copyright (C) 2012 LinkOmnia Ltd.  

    Author: Wan Leung Wong (wanleung@linkomnia.com)

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.linkomnia.android.StrokeFiveKeyboard;

import java.util.ArrayList;
import java.util.List;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class StrokeFiveKeyboard extends InputMethodService implements
        KeyboardView.OnKeyboardActionListener {
    /** Called when the activity is first created. */
    private KeyboardView mInputView;

    private Stroke5KeyBoard mStroke5;
    private Stroke5Table mKeyData;
    
    private boolean strokemode;
    private char [] charbuffer = new char[5];
    private int strokecount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mKeyData = new Stroke5Table(this, false);
        mKeyData.open();
    }

    public void onInitializeInterface() {
        // ArrayList<String> aaa = mKeyData.searchRecord(",");
        mStroke5 = new Stroke5KeyBoard(this, R.xml.stroke5);
        // Log.d("WANLEUNG", getFilesDir());
        // Log.d("WANLEUNG", aaa.size()+"");

    }

    public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(R.layout.main,
                null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mStroke5);
        strokemode = true;
        return mInputView;
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Log.d("WANLEUNG", "onStartInput");
        strokemode = true;
        this.stroktreset();
        //this.mInputView.closing();
    }
    
    public void onFinishInput() {
        super.onFinishInput();
        Log.d("WANLEUNG", "onFinishInput");
    }
    
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        Log.d("WANLEUNG", "onStartInputView");
    }
    
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
            int newSelStart, int newSelEnd,
            int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        Log.d("WANLEUNG", "onUpdateSelection");
    }
    
    public void onDisplayCompletions(CompletionInfo[] completions) {
        Log.d("WANLEUNG", "onDisplayCompletions");
    }
    
    public void onKey(int primaryCode, int[] keyCodes) {
        // TODO Auto-generated method stub
        //Log.d("WANLEUNG", "onkey " + primaryCode + " " + keyCodes.toString());
        //ArrayList<String> list = mKeyData.searchRecord(".n.");
        //Log.d("WANLEUNG", list.get(0) + "");
        //InputConnection ic = getCurrentInputConnection();
        //ic.commitText(list.get(0), 1);
        if (this.strokemode) {
            this.handleStrokeMode(primaryCode);
        }
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(INPUT_METHOD_SERVICE, "onKeyDown");
        return true;
    }
    public void onPress(int primaryCode) {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "onPress " + primaryCode);

    }

    public void onRelease(int primaryCode) {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "onRelease " + primaryCode);
    }

    public void onText(CharSequence text) {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "onText " + text.toString());
    }

    public void swipeDown() {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "swipeDown ");
    }

    public void swipeLeft() {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "swipeLeft ");
    }

    public void swipeRight() {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "swipeRight ");
    }

    public void swipeUp() {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "swipeUp ");
    }

    public void onDestroy() {
        this.mKeyData.close();
    }
    
    private void stroktreset() {
        this.charbuffer = new char[5];
        this.strokecount = 0;
    }
    
    private void handleStrokeMode(int keyCode){
        switch (keyCode) {
            case -5: {
                this.handleBackspace();
                break;
            }
            case 65292:
            case 12290:
            case 12289:
            case 65281:
            case 65311:
            case 12288: {
                this.handleChinesePunctuation(keyCode);
                break;
            }
            case 3000:
            case 3001:
            case 3002:
            case 3003: {
                break;
            }
            default: {
                this.typingStroke(keyCode);
                break;
            }
        }
    }
    
    private void typingStroke(int keycode) {
        char c;
        switch (keycode) {
            case 1001: {
                c = 'm';
                break;
            }
            case 1002: {
                c = '/';
                break;
            }
            case 1003: {
                c = ',';
                break;
            }
            case 1004: {
                c = '.';
                break;
            }
            case 1005: {
                c = 'n';
                break;
            }
            default: {
                c = '*';
            }
        }
        this.charbuffer[this.strokecount++] = c;
        Log.d("WANLEUNG", this.mKeyData.searchRecord(new String(this.charbuffer,0,this.strokecount)).get(0));
    }
    
    private void handleBackspace() {
        if (this.strokecount > 1) {
            this.strokecount -= 1;
            //getCurrentInputConnection().setComposingText(mComposing, 1);
            //updateCandidates();
        } else if (this.strokecount > 0) {
            this.stroktreset();
            //getCurrentInputConnection().commitText("", 0);
            //updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
    }
    
    private void handleChinesePunctuation(int keycode) {
        this.stroktreset();
        InputConnection ic = getCurrentInputConnection();        
        char c = (char)keycode;
        String str = String.format("%c", c);
        ic.commitText(str, 1);
    }
    
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
    
}
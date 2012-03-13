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
import android.inputmethodservice.Keyboard.Key;
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
    private CandidateView mCandidateView;

    //private Keyboard mCurrentKeyboard;
    private Keyboard mLastKeyboard;
    
    private Stroke5Table mKeyData;

    private Stroke5KeyBoard mStroke5Keyboard;
    private Stroke5KeyBoard mChineseSymbolKeyboard;
    private LatinKeyboard mQwertKeyboard;
    private LatinKeyboard mEnglishSymbolKeyboard;
    
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
        mStroke5Keyboard = new Stroke5KeyBoard(this, R.xml.stroke5);
        mChineseSymbolKeyboard = new Stroke5KeyBoard(this, R.xml.symbols_ch);
        mQwertKeyboard = new LatinKeyboard(this, R.xml.qwert);
        mEnglishSymbolKeyboard = new LatinKeyboard(this, R.xml.symbols_en);
        // Log.d("WANLEUNG", getFilesDir());
        // Log.d("WANLEUNG", aaa.size()+"");

    }

    public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(R.layout.main,
                null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mStroke5Keyboard);
        mInputView.setPreviewEnabled(false);
        strokemode = true;
        return mInputView;
    }
    
    public View onCreateCandidatesView() {
        //mCandidateView = new CandidateView(this);
        mCandidateView = (CandidateView) getLayoutInflater().inflate(
                R.layout.candidates, null);
        mCandidateView.setDelegate(this);
        return mCandidateView;
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Log.d("WANLEUNG", "onStartInput");
        strokemode = true;
        this.stroktreset();
        //this.mInputView.closing();
    }
    
    public void onFinishInput() {
        if (mInputView != null) {
            mInputView.closing();
        }
        Log.d("WANLEUNG", "onFinishInput");
        super.onFinishInput();

    }
    
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        Log.d("WANLEUNG", "onStartInputView");
        if (this.mStroke5Keyboard == this.mInputView.getKeyboard()) {
            this.strokemode = true;
        } else {
            this.strokemode = false;
        }
    }
    
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
            int newSelStart, int newSelEnd,
            int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        Log.d("WANLEUNG", "onUpdateSelection");
    }
    
    public void onDisplayCompletions(CompletionInfo[] completions) {
        super.onDisplayCompletions(completions);
        Log.d("WANLEUNG", "onDisplayCompletions");
    }
    
    public void onKey(int primaryCode, int[] keyCodes) {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "onkey " + primaryCode + " " + keyCodes.toString());
        for (int i = 0; i<keyCodes.length; i++) {
            Log.d("WANLEUNG", "onkey " + primaryCode + " " + keyCodes[i]);
        }
        //ArrayList<String> list = mKeyData.searchRecord(".n.");
        //Log.d("WANLEUNG", list.get(0) + "");
        //InputConnection ic = getCurrentInputConnection();
        //ic.commitText(list.get(0), 1);
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            this.handleClose();
            return;
        }
        if (this.strokemode) {
            this.handleStrokeMode(primaryCode);
        } else {
            this.handleLatinMode(primaryCode, keyCodes);
        }
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(INPUT_METHOD_SERVICE, "onKeyDown");
        return super.onKeyDown(keyCode, event);
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
        this.mInputView.closing();
        this.mKeyData.close();
        super.onDestroy();
    }
    
    private void stroktreset() {
        this.charbuffer = new char[5];
        this.strokecount = 0;
        if (this.mCandidateView != null) {
            this.updateCandidates();
        }
    }
    
    private void handleStrokeMode(int keyCode){
        switch (keyCode) {
            case Keyboard.KEYCODE_DELETE: {
                this.handleBackspace();
                break;
            }
            case 1001:
            case 1002:
            case 1003:
            case 1004:
            case 1005: {
                this.typingStroke(keyCode);
                break;
            }
            case 3001:
            case 3002: {
                this.switchKeyboard(keyCode);
                break;
            }
            case 3000:
            case 3003:
            case 3004: {
                break;
            }
            default: {
                this.handleChinesePunctuation(keyCode);
                break;
            }
        }
    }
    
    private void handleLatinMode(int keycode, int[] keyCodes) {
        switch (keycode) {
            case Keyboard.KEYCODE_SHIFT: {
                //TODO:: handle shift
                this.handleShiftKey();
                break;
            }
            case Keyboard.KEYCODE_DELETE: {
                this.handleBackspace();
                break;
            }
            case 3001:
            case 3002: {
                this.switchKeyboard(keycode);
                break;
            }
            default: {
                this.handleLatinCharacter(keycode, keyCodes);
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
        if (this.strokecount < 5) {
            this.charbuffer[this.strokecount++] = c;
        }
        //getCurrentInputConnection().setComposingText(new String(this.charbuffer,0,this.strokecount), 1);
        this.mCandidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
        Log.d("WANLEUNG", new String(this.charbuffer,0,this.strokecount));
        //Log.d("WANLEUNG", this.mKeyData.searchRecord(new String(this.charbuffer,0,this.strokecount)).get(0));
        //this.mCandidateView.invalidate();
        updateCandidates();
    }
    
    private void handleBackspace() {
        if (this.strokemode) {
            if (this.strokecount > 1) {
                this.strokecount -= 1;
                //getCurrentInputConnection().setComposingText(new String(this.charbuffer,0,this.strokecount), 1);
                this.mCandidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
                updateCandidates();
            } else if (this.strokecount > 0) {
                this.stroktreset();
                //getCurrentInputConnection().setComposingText(new String(this.charbuffer,0,this.strokecount), 1);
                this.mCandidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
                this.setCandidatesViewShown(false);
            } else {
                this.setCandidatesViewShown(false);
                keyDownUp(KeyEvent.KEYCODE_DEL);
            }
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
    
    private void handleLatinCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                //mInputView.setShifted(false);
            }
        }
        getCurrentInputConnection().commitText(String.format("%c", primaryCode), 1);
    }
    
    private void handleShiftKey() {
        if (this.mInputView == null) {
            return;
        }
        if (this.mQwertKeyboard == this.mInputView.getKeyboard()) {
            // Alphabet keyboard
            mInputView.setShifted(!mInputView.isShifted());
        } 
    }

    
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
    
    private void updateCandidates() {
        ArrayList<String> words;
        if (strokecount > 0) {
            words = this.mKeyData.searchRecord(new String(this.charbuffer,0,this.strokecount));
        } else {
            words = new ArrayList<String>();
        }
        if (words.isEmpty()) {
            setCandidatesViewShown(false);
        } else {
            this.mCandidateView.setSuggestion(words);
            setCandidatesViewShown(true);
        }   
    }
    
    public void onChooseWord(String word) {
        this.stroktreset();
        InputConnection ic = getCurrentInputConnection();        
        ic.commitText(word, 1);
        setCandidatesViewShown(false);
    }
    
    public void switchKeyboard(int keyCode) {
        Keyboard currentKeyboard = this.mInputView.getKeyboard();
        if (keyCode == 3002) {
            if (this.mStroke5Keyboard == currentKeyboard) {
                this.strokemode = false;
                this.stroktreset();
                mInputView.setKeyboard(this.mQwertKeyboard);
                this.mInputView.getKeyboard().setShifted(false);
            } else if (this.mEnglishSymbolKeyboard == currentKeyboard) {
                this.strokemode = false;
                this.stroktreset();
                mInputView.setKeyboard(this.mQwertKeyboard);
                this.mInputView.getKeyboard().setShifted(false);
            } else {
                this.strokemode = true;
                this.stroktreset();
                mInputView.setKeyboard(this.mStroke5Keyboard);
                this.mInputView.getKeyboard().setShifted(false);
            }
        } else if (keyCode == 3001) {
            if (this.mStroke5Keyboard == currentKeyboard) {
                this.strokemode = true;
                this.stroktreset();
                mInputView.setKeyboard(this.mChineseSymbolKeyboard);
                this.mInputView.getKeyboard().setShifted(false);
            } else if (this.mChineseSymbolKeyboard == currentKeyboard) {
                this.strokemode = true;
                this.stroktreset();
                mInputView.setKeyboard(this.mStroke5Keyboard);
                this.mInputView.getKeyboard().setShifted(false);
            } else if (this.mQwertKeyboard == currentKeyboard) {
                this.strokemode = false;
                this.stroktreset();
                mInputView.setKeyboard(this.mEnglishSymbolKeyboard);
                this.mInputView.getKeyboard().setShifted(false);
            } else if (this.mEnglishSymbolKeyboard == currentKeyboard) {
                this.strokemode = false;
                this.stroktreset();
                mInputView.setKeyboard(this.mQwertKeyboard);
                this.mInputView.getKeyboard().setShifted(false);
            }
        }
                        
    }
    
    private void handleClose() {
        this.stroktreset();
        requestHideSelf(0);
        mInputView.closing();
    }
}
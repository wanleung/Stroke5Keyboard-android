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

package com.linkomnia.android.Stroke5;

import java.util.ArrayList;
import java.util.List;

import com.linkomnia.android.StrokeFiveKeyboard.R;

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
    private IMEKeyboardView inputView;
    private CandidateView candidateView;
    
    private Stroke5Table stroke5WordDictionary;

    private Stroke5KeyBoard mStroke5Keyboard;
    private Stroke5KeyBoard mChineseSymbolKeyboard;
    private LatinKeyboard mQwertKeyboard;
    private LatinKeyboard mEnglishSymbolKeyboard;
    
    private IMESwitch imeSwitch;
    
    private char [] charbuffer = new char[5];
    private int strokecount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        stroke5WordDictionary = new Stroke5Table(this, false);
        stroke5WordDictionary.open();
    }

    public void onInitializeInterface() {
        imeSwitch = new IMESwitch(this);
    }

    public View onCreateInputView() {
        inputView = (IMEKeyboardView) getLayoutInflater().inflate(R.layout.main, null);
        inputView.setPreviewEnabled(false);
        inputView.setOnKeyboardActionListener(this);
        return inputView;
    }
    
    public View onCreateCandidatesView() {
        candidateView = (CandidateView) getLayoutInflater().inflate(R.layout.candidates, null);
        candidateView.setDelegate(this);
        return candidateView;
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        Log.d("WANLEUNG", "onStartInput");
        this.stroktreset();
        //this.mInputView.closing();
    }
    
    public void onFinishInput() {
        if (inputView != null) {
            inputView.closing();
        }
        Log.d("WANLEUNG", "onFinishInput");
        super.onFinishInput();

    }
    
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        this.imeSwitch.init();
        this.inputView.setKeyboard(this.imeSwitch.getCurrentKeyboard());
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
        if (imeSwitch.handleKey(primaryCode)) {
            this.stroktreset();
            this.inputView.setKeyboard(imeSwitch.getCurrentKeyboard());
            return;
        }
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            this.handleClose();
            return;
        }
        if (this.imeSwitch.isChinese()) {
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
        this.inputView.closing();
        this.stroke5WordDictionary.close();
        super.onDestroy();
    }
    
    private void stroktreset() {
        this.charbuffer = new char[5];
        this.strokecount = 0;
        if (this.candidateView != null) {
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
        this.candidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
        Log.d("WANLEUNG", new String(this.charbuffer,0,this.strokecount));
        //Log.d("WANLEUNG", this.mKeyData.searchRecord(new String(this.charbuffer,0,this.strokecount)).get(0));
        //this.mCandidateView.invalidate();
        updateCandidates();
    }
    
    private void handleBackspace() {
        if (imeSwitch.isChinese()) {
            if (this.strokecount > 1) {
                this.strokecount -= 1;
                this.candidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
                updateCandidates();
            } else if (this.strokecount > 0) {
                this.stroktreset();
                this.candidateView.updateInputBox(new String(this.charbuffer,0,this.strokecount));
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
            if (inputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                //mInputView.setShifted(false);
            }
        }
        getCurrentInputConnection().commitText(String.format("%c", primaryCode), 1);
    }
    
    private void handleShiftKey() {
        if (this.inputView == null) {
            return;
        }
        if (this.mQwertKeyboard == this.inputView.getKeyboard()) {
            // Alphabet keyboard
            inputView.setShifted(!inputView.isShifted());
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
            words = this.stroke5WordDictionary.searchRecord(new String(this.charbuffer,0,this.strokecount));
        } else {
            words = new ArrayList<String>();
        }
        if (words.isEmpty()) {
            setCandidatesViewShown(false);
        } else {
            this.candidateView.setSuggestion(words);
            setCandidatesViewShown(true);
        }   
    }
    
    public void onChooseWord(String word) {
        this.stroktreset();
        InputConnection ic = getCurrentInputConnection();        
        ic.commitText(word, 1);
        setCandidatesViewShown(false);
    }
    
    private void handleClose() {
        this.stroktreset();
        requestHideSelf(0);
        inputView.closing();
    }
}
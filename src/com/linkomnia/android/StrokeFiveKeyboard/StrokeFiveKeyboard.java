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
        return mInputView;
    }

    public void onKey(int primaryCode, int[] keyCodes) {
        // TODO Auto-generated method stub
        Log.d("WANLEUNG", "onkey " + primaryCode + " " + keyCodes.toString());
        ArrayList<String> list = mKeyData.searchRecord(".n.");
        Log.d("WANLEUNG", list.get(0) + "");
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
}
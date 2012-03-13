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

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;
import android.util.Log;

public class IMEKeyboardView extends KeyboardView {

    public IMEKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IMEKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == IMEKeyboard.KEYCODE_SHIFT) {
            getOnKeyboardActionListener().onKey(IMEKeyboard.KEYCODE_CAPLOCK, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }
    
}
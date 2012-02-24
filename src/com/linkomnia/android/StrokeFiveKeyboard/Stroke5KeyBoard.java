package com.linkomnia.android.StrokeFiveKeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.Keyboard.Row;
import android.view.inputmethod.EditorInfo;

public class Stroke5KeyBoard extends Keyboard {
    public Stroke5KeyBoard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public Stroke5KeyBoard(Context context, int layoutTemplateResId, 
            CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }
    
    
}

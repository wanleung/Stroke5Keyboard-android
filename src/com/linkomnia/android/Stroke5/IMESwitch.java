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

import com.linkomnia.android.StrokeFiveKeyboard.R;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.Log;

public class IMESwitch {
    
    private Context ctx;
    
    private IMEKeyboard currentKeyboard;
    private IMEKeyboard englishKeyboard;
    private IMEKeyboard enSymbolNumberKeyboard;
    private IMEKeyboard enSymoblShiftKeyboard;
    private IMEKeyboard chineseKeyboard;
    
    private boolean isFromChinese;
    
    public IMESwitch(Context ctx) {
        this.ctx = ctx;
        this.englishKeyboard = new IMEKeyboard(this.ctx, R.xml.qwert);
        this.enSymbolNumberKeyboard = new IMEKeyboard(this.ctx, R.xml.symbols_en);
        this.enSymoblShiftKeyboard = new IMEKeyboard(this.ctx, R.xml.symbols_ch);
        this.chineseKeyboard = new IMEKeyboard(this.ctx, R.xml.stroke5);
    }
    
    public void init() {
        if (this.currentKeyboard == null) {
            this.currentKeyboard = this.chineseKeyboard;
        } else {
            
        }
    }
    
    public IMEKeyboard getCurrentKeyboard() {
        return this.currentKeyboard;
    }
    
    public boolean isChinese() {
        return this.chineseKeyboard == this.currentKeyboard;
    }

    public boolean isEnglish() {
        return this.englishKeyboard == this.currentKeyboard;
    }

    public boolean isSymbol() {
        return this.enSymbolNumberKeyboard == this.currentKeyboard;
    }

    public boolean isPunctuation() {
        return this.enSymoblShiftKeyboard == this.currentKeyboard;
    }
    
    public boolean isNotCharKeyboard() {
        return (this.isSymbol() || this.isPunctuation());
    }
       
    public boolean handleKey(int keyCode) {
        boolean result = false;
        switch (keyCode) {
            case IMEKeyboard.KEYCODE_CAPLOCK: {
                this.currentKeyboard.setCapLock(! this.currentKeyboard.isCapLock());
                result = true;
                break;
            }
            case IMEKeyboard.KEYCODE_SHIFT: {
                if (this.currentKeyboard.isCapLock()) {
                    this.currentKeyboard.setCapLock(false);
                } else {
                    this.currentKeyboard.setShifted(! this.currentKeyboard.isShifted());
                }
                if (this.isNotCharKeyboard()) {
                    this.switchBetweenSymbolShift();
                }
                result = true; 
                break;
            }
            case IMEKeyboard.KEYCODE_MODE_CHANGE_CHAR: {
                this.switchToCharKeyboard();
                result = true;
                break;
            }
            case IMEKeyboard.KEYCODE_MODE_CHANGE: {
                this.switchToSymbol();
                result = true;
                break;
            }
            default: {
                result = false;
            }
        }
        return result;
    }
    
    public void switchToCharKeyboard() {
        if (this.isNotCharKeyboard()) {
            if (this.isFromChinese) {
                this.currentKeyboard = this.chineseKeyboard;
            } else {
                this.currentKeyboard = this.englishKeyboard;
            }
        } else {
            if (this.isChinese()) {
                this.currentKeyboard = this.englishKeyboard;
            } else {
                this.currentKeyboard = this.chineseKeyboard;
            }
        }
        this.currentKeyboard.setCapLock(false);
    }
    
    public void switchToSymbol() {
        this.isFromChinese = this.isChinese();
        this.currentKeyboard = this.enSymbolNumberKeyboard; 
        this.currentKeyboard.setCapLock(false);
    }
    
    public void switchBetweenSymbolShift() {
        if (this.currentKeyboard.isShifted()) {
            this.currentKeyboard = this.enSymoblShiftKeyboard;
            this.currentKeyboard.setCapLock(true);
        } else {
            this.currentKeyboard = this.enSymbolNumberKeyboard;
            this.currentKeyboard.setCapLock(false);
        }
    }
    
}

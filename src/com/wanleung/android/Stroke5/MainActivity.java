/*
    Stroke5 Chinese Input Method for Android
    Copyright (C) 2012 Wanleung's Workshop

    Author: Wan Leung Wong (info@wanleung.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.wanleung.android.Stroke5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * Launcher Activity for Stroke5 Keyboard.
 *
 * Guides the user through the two steps required to activate the IME:
 *   1. Enable the keyboard in system Input Method Settings.
 *   2. Select it as the active input method.
 *
 * Also provides access to the About page and Privacy Policy.
 */
public class MainActivity extends AppCompatActivity {

    private TextView stepOneStatus;
    private TextView stepTwoStatus;
    private Button btnEnable;
    private Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepOneStatus = findViewById(R.id.step_one_status);
        stepTwoStatus = findViewById(R.id.step_two_status);
        btnEnable = findViewById(R.id.btn_enable);
        btnSelect = findViewById(R.id.btn_select);

        btnEnable.setOnClickListener(v ->
                startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)));

        btnSelect.setOnClickListener(v -> {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showInputMethodPicker();
        });

        findViewById(R.id.btn_about).setOnClickListener(v -> showWebDialog(
                getString(R.string.ime_name) + " v" + getString(R.string.ime_version),
                "file:///android_asset/about.html"));

        findViewById(R.id.btn_privacy).setOnClickListener(v -> showWebDialog(
                getString(R.string.privacy_policy),
                "file:///android_asset/privacy_policy.html"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSetupStatus();
    }

    /** Refreshes the status indicators and button states based on current IME state. */
    private void updateSetupStatus() {
        boolean enabled = isImeEnabled();
        boolean selected = isImeSelected();

        int okColor = ContextCompat.getColor(this, R.color.status_ok);
        int pendingColor = ContextCompat.getColor(this, R.color.status_pending);

        stepOneStatus.setText(enabled ? R.string.status_enabled : R.string.status_not_enabled);
        stepOneStatus.setTextColor(enabled ? okColor : pendingColor);
        btnEnable.setText(enabled ? R.string.btn_reopen_settings : R.string.btn_enable);

        stepTwoStatus.setText(selected ? R.string.status_selected : R.string.status_not_selected);
        stepTwoStatus.setTextColor(selected ? okColor : pendingColor);
        // Step 2 only makes sense after step 1 is done
        btnSelect.setEnabled(enabled);
        btnSelect.setAlpha(enabled ? 1.0f : 0.5f);
    }

    /** Returns true if our IME package is in the system's enabled input methods list. */
    private boolean isImeEnabled() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> enabledList = imm.getEnabledInputMethodList();
        for (InputMethodInfo info : enabledList) {
            if (info.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /** Returns true if our IME is the currently selected default input method. */
    private boolean isImeSelected() {
        String current = Settings.Secure.getString(
                getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        return current != null && current.startsWith(getPackageName());
    }

    /** Shows a dialog containing a WebView loaded with the given asset URL. */
    private void showWebDialog(String title, String url) {
        View view = LayoutInflater.from(this).inflate(R.layout.setting_about, null);
        WebView webView = view.findViewWithTag("webview");
        webView.loadUrl(url);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}

/*
    Stroke5 Chinese Input Method for Android
    Copyright (C) 2012 Wanleung's Workshop

    Author: Wan Leung Wong (wanleung@wanleung.com)

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
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new SettingFragment())
                    .commit();
        }
    }

    public static class SettingFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref, rootKey);
            Preference about = findPreference("about");
            if (about != null) {
                about.setOnPreferenceClickListener(preference -> {
                    showAboutBox();
                    return true;
                });
            }
        }

        private void showAboutBox() {
            View aboutView = View.inflate(requireContext(), R.layout.setting_about, null);
            WebView webView = aboutView.findViewWithTag("webview");
            webView.loadUrl("file:///android_asset/about.html");
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.ime_name) + " v" + getString(R.string.ime_version))
                    .setView(aboutView)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
package com.hyphenated.pokerplayerclient.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hyphenated.pokerplayerclient.R;
import com.hyphenated.pokerplayerclient.manager.PreferencesManager;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox vibrateCheckbox = (CheckBox) findViewById(R.id.vibrate_checkbox);
        vibrateCheckbox.setChecked(PreferencesManager.isVibrateEnabled(this));
        vibrateCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                PreferencesManager.setVibrateEnabled(isChecked, SettingsActivity.this);
            }
        });
    }
}

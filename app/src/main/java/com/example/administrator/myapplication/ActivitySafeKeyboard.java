package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.widgetviews.SafeKeyboard;

public class ActivitySafeKeyboard extends AppCompatActivity {

    private SafeKeyboard safeKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_keyboard);

        EditText safeEdit = findViewById(R.id.safeEditText);
        LinearLayout keyboardContainer = findViewById(R.id.keyboardViewPlace);
        safeKeyboard = new SafeKeyboard(getApplicationContext(), keyboardContainer, safeEdit);
    }

    // 以下代码是实现软件盘所加的
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (safeKeyboard.isShow()) {
                safeKeyboard.hideKeyboard();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_TAB || keyCode == KeyEvent.KEYCODE_ENTER) {
            return super.onKeyDown(keyCode, event);
        } else
            return super.onKeyDown(keyCode, event);
    }
}

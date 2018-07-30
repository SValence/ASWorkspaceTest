package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ActivityAddBottom extends Activity {

    private ViewGroup decorView;
    private View container;
    private boolean isShow = false;
    private LinearLayout layout;
    private TranslateAnimation mShowAction = null;
    private TranslateAnimation mHiddenAction = null;
    private RelativeLayout keyboardHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bottom);

        decorView = (ViewGroup) getWindow().getDecorView();
        layout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);
        layout.setGravity(Gravity.BOTTOM);
    }

    public void onBottomAdd(View view) {
        container = LayoutInflater.from(this).inflate(R.layout.layout_keyboard_containor, layout, true);

        Keyboard keyboardLetter = new Keyboard(this, R.xml.keyboard_letter);         //实例化字母键盘
        KeyboardView keyboardLetterView = container.findViewById(R.id.safeKeyboardLetter);
        keyboardHeader = container.findViewById(R.id.keyboardHeader);
        keyboardLetterView.setKeyboard(keyboardLetter);                         //给键盘View设置键盘

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        int height = wm.getDefaultDisplay().getHeight();
        Log.e("keyboardHeader",""+keyboardHeader.getHeight());
        if (mShowAction == null) {
            mShowAction = new TranslateAnimation(0, 0, height,
                    height - keyboardLetter.getHeight() - keyboardHeader.getHeight());
            mShowAction.setDuration(500);
        }

        if (mHiddenAction == null) {
            mHiddenAction = new TranslateAnimation(0, 0,
                    height - keyboardLetter.getHeight() - keyboardHeader.getHeight(), height);
            mHiddenAction.setDuration(500);
        }
        if (!isShow) {
            decorView.addView(layout);
            layout.startAnimation(mShowAction);
            isShow = true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isShow) {
                layout.startAnimation(mHiddenAction);
                decorView.removeView(layout);
                isShow = false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return false;
        } else
            return super.onKeyDown(keyCode, event);
    }
}

package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.base.BaseActivity;

import static com.example.administrator.myapplication.ActivityFileSystem.getExtSDCardPath;

public class MainActivity extends BaseActivity {

    private TextView exMem, inMem;
    private static final String TAG = "MainActivity";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = getApplicationContext();

        exMem = findViewById(R.id.externalMem);
        inMem = findViewById(R.id.internalMem);

        boolean inBoo = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        inMem.setText((inBoo ? "存在内置内存, 路径为: " + Environment.getExternalStorageDirectory().getAbsolutePath() : "不存在内置内存"));
        exMem.setText((TextUtils.isEmpty(getExtSDCardPath()) ? "不存在外置内存卡" : "存在外置内存卡, 路径为: " + getExtSDCardPath()));
        Toast.makeText(this.mContext, "从父类中继承的mContext,还是需要重新赋值才能使用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onKeyBoardClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityKeyboard.class);
        startActivity(intent);
    }

    public void onFileClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityFileSystem.class);
        startActivity(intent);
    }

    public void onSafeKeyBoardClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivitySafeKeyboard.class);
        startActivity(intent);
    }

    public void onSafeKeyBatteryClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityBattery.class);
        startActivity(intent);
    }

    public void onBottomAdd(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityAddBottom.class);
        startActivity(intent);
    }

    public void onShutDownClick(View view) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
    }

    public void onRelativeClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityRelativeTest.class);
        startActivity(intent);
    }

    public void onCircleImageClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityCircleImage.class);
        startActivity(intent);
    }

    private long exitTime = 0;

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, String.format("keyDown:%d", keyCode));
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再点击一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

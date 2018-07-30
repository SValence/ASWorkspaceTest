package com.example.administrator.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.base.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class ActivityFileSystem extends BaseActivity {
    private static String ALLPATHSTR;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_system);

        this.mContext = getApplicationContext();

        TextView allMemPath = findViewById(R.id.allMemPath);

        getExtSDCardPath();
        allMemPath.setText(ALLPATHSTR);
        //Toast.makeText(this.mContext,"从父类中继承的mContext,还是需要重新赋值才能使用",Toast.LENGTH_SHORT).show();
    }

    public static String getExtSDCardPath() {
        ALLPATHSTR = "";
        int i = 1;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("df");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                ALLPATHSTR += String.valueOf(i) + ".  " + line + "\n";
                i++;
                if (line.contains("sdcard")) {
                    String[] arr = line.split(" ");
                    String path = arr[0];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        return path;
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

package com.example.meitu.patchutilsdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("bspatch");
    }
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        button = (Button)findViewById(R.id.button);
        tv.setText("hello");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBspatch();
            }
        });
        //tv.setText(Integer.toString(BsPatchJNI.patch("","","")));
    }
    /**
     * 合成新的apk
     */
    private  void doBspatch() {
        final File deskApk = new File(Environment.getExternalStorageDirectory(),"desk.apk");//新的apk路径
        final File patch = new File(Environment.getExternalStorageDirectory(),"PATCH.patch");//patch
        //pu.genPatch(getApkPath(this),deskApk.getAbsolutePath(),patch.getAbsolutePath());//合成deskApk
        final File oldApk = new File(Environment.getExternalStorageDirectory(),"app_1.0.apk");//旧的apk
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdCardDir = Environment.getExternalStorageDirectory();
            if(deskApk.exists()){
                Log.d("kang","deskapk已经存在");
                return;
            }
            //判断旧包和差分包是否存在
            if(patch.exists() && oldApk.exists()){
                Log.d("kang","正在合成...");
                //pu.genPatch(oldApk.getAbsolutePath(),deskApk.getAbsolutePath(),patch.getAbsolutePath());//合成deskApk
                int ret = BsPatchJNI.patch(oldApk.getAbsolutePath(),deskApk.getAbsolutePath(),patch.getAbsolutePath());
                Log.d("kang",Integer.toString(ret));
            }
            //生成新包
            if(deskApk.exists()){
                //安装
                Log.d("kang","successed");
            }else{
                Log.d("kang","failed");
            }
        }

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
   // public native String stringFromJNI();
}

package com.veken.xm.bitmapcompress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.compress.ImageCompress;
import com.bumptech.glide.Glide;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView tvBefore,tvAfter,tvCompress;
    private ImageView ivBefore,ivAfter,ivCompress;

    private String path =  Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator;


    //测试图片的存位置
    private String beforePath =path+"test.jpg";
    private String afterPath = path+ "hfresult.jpg";
    private String resultPath = path + "result.jpg";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBefore = findViewById(R.id.tv_before);
        tvAfter = findViewById(R.id.tv_after);
        tvCompress = findViewById(R.id.tv_compress);
        ivBefore = findViewById(R.id.iv_before);
        ivAfter = findViewById(R.id.iv_after);
        ivCompress = findViewById(R.id.iv_compress);
        Glide.with(this).load(beforePath).into(ivBefore);
        tvBefore.setText("压缩之前的名字:"+beforePath.substring(beforePath.lastIndexOf("/")+1));
        findViewById(R.id.btn_compress)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        compressTask();
                    }
                });

    }

    /**
     * 压缩图片
     */
    @SuppressLint("StaticFieldLeak")
    private void compressTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                compressTest();
                return null;
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(MainActivity.this, "压缩完成", Toast.LENGTH_LONG)
                        .show();
                Glide.with(MainActivity.this).load(afterPath).into(ivAfter);
                tvAfter.setText("压缩之后的名字:"+afterPath.substring(afterPath.lastIndexOf("/")+1));
                ImageUitl.compress(beforePath,resultPath);
                tvCompress.setText("采样率压缩之后的名字:"+resultPath.substring(resultPath.lastIndexOf("/")+1));
                Glide.with(MainActivity.this).load(resultPath).into(ivCompress);

            }
        }.execute();
    }

    /**
     * 对比压缩出的同等质量的图片，使用哈夫曼算法的话，压缩的更小
     */
    private void compressTest() {
        Bitmap bitmap = BitmapFactory.decodeFile(beforePath);
        ImageCompress.nativeCompressBitmap(bitmap, 1, afterPath, true);
    }



}

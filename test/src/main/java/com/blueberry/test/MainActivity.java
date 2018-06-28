package com.blueberry.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.compress.ImageCompress;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private TextView tvBefore,tvAfter;
    private ImageView ivBefore,ivAfter;

    private String path =  Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator;


    //测试图片的存位置
    private String beforePath =path+"test.jpg";
    private String afterPath = path+ "hfresult.jpg";
    private Bitmap bitmap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBefore = (TextView) findViewById(R.id.tv_before);
        tvAfter = (TextView) findViewById(R.id.tv_after);
        ivBefore = (ImageView) findViewById(R.id.iv_before);
        ivAfter = (ImageView) findViewById(R.id.iv_after);
        bitmap = BitmapFactory.decodeFile(beforePath);
        ivBefore.setImageBitmap(bitmap);
        tvBefore.setText("压缩之前的名字:"+beforePath.substring(beforePath.lastIndexOf("/")+1));
        checkPermission();
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
                Bitmap bmap= BitmapFactory.decodeFile(afterPath);
                ivAfter.setImageBitmap(bmap);
                tvAfter.setText("压缩之后的名字:"+afterPath.substring(afterPath.lastIndexOf("/")+1));
                Toast.makeText(MainActivity.this, "压缩完成", Toast.LENGTH_LONG)
                        .show();
            }
        }.execute();
    }

    /**
     * 对比压缩出的同等质量的图片，使用哈夫曼算法的话，压缩的更小
     */
    private void compressTest() {
        ImageCompress.nativeCompressBitmap(bitmap, 10, afterPath, true);
    }

    /**
     * 6.0 权限申请
     */
    private void checkPermission() {
        if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
//            compressTask();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                compressTask();
            }
        }
    }



}

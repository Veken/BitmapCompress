package com.veken.xm.bitmapcompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Veken on 2018/6/28 20:10
 *
 * @desc
 */

public class ImageUitl {
    /**
     * 采样率压缩
     * @return
     */
    public static Bitmap compress(String beforePath,String resultPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(beforePath, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int inSampleSize = 100; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if(minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int)ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(resultPath, options); // 解码文件
        return bm;
    }

}

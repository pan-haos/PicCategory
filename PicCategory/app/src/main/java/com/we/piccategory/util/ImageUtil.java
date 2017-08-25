package com.we.piccategory.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;

import com.we.piccategory.ui.base.BaseApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/25
 * Time: 15:43
 * Description:
 */
public class ImageUtil {
    public static Bitmap decordBitmap(Context context, int resId) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inPreferredConfig = Bitmap.Config.RGB_565;
        if (Build.VERSION.SDK_INT < 21) {
            option.inPurgeable = true;
            option.inInputShareable = true;
        }
        InputStream ins = context.getResources().openRawResource(resId);

        Bitmap bitmap = BitmapFactory.decodeStream(ins, null, option);
        return bitmap;
    }

    public static Uri convertUri(Uri uri) {
        InputStream ins = null;
        try {
            ins = BaseApp.context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            Uri uri1 = saveBitmap(bitmap);
            return uri1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Uri saveBitmap(Bitmap bm) {
        File tmpFile = new File(BaseApp.context.getExternalFilesDir("exter_test") + "com.piccate.tmp");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        File img = new File(tmpFile.getAbsoluteFile() + "avent.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String base64CodingBmp(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
            byte[] bytes = stream.toByteArray();
            //用Base64编码封装该字节数组
            String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
            return img;
        }
        return null;
    }


    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }


    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;
    }


    public static File base64Decoding(String bmp) {
        byte[] bytes = Base64.decode(bmp, Base64.DEFAULT);
        return null;
    }

}

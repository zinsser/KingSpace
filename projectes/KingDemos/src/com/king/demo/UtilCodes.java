package com.king.demo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class UtilCodes {
	private static String IMG_URL = "http://www.cssnz.org/flower.jpg";
	public void LoadImageFromFile(ImageView img){
		Bitmap bmp=BitmapFactory.decodeFile("/sdcard/QQDownload/test.jpg");
		img.setImageBitmap(bmp);
	}
	
    @SuppressWarnings("unused")
	public void LoadFromFile(String path){
//    	String path = "/sdcard/Tencent/QQ/log/myqqlog.txt";//得到文件路径
//    	String path = "/sdcard/QQDownload/test.jpg";//得到文件路径

    	File file = new File(path);//创建一个文件对象
    	FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			//读文件
	    	byte[] buffer = new byte[1024];//缓存
	    	int len = 0;
	    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while( (len = inStream.read(buffer))!= -1){//直到读到文件结束
				   outStream.write(buffer, 0, len);
			}
			
			byte[] data = outStream.toByteArray();//得到文件的二进制数据
			
//			mTxtLoginOffline.setText(new String(data));

//			bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//			mImgLogo.setImageBitmap(bmp);

			outStream.close();
			inStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }    
    public void load() {
        // TODO Auto-generated method stub
        try {
                URL aURL = new URL(IMG_URL);
    URLConnection con = aURL.openConnection();
    con.connect();
    InputStream is = con.getInputStream();
    /* 建立缓冲区是一个良好的编程习惯. */
    BufferedInputStream bis = new BufferedInputStream(is);
    /* 解析网络上的图片 */
//    Bitmap bm = BitmapFactory.decodeStream(bis);
//    imageview.setImageBitmap(bm);
    bis.close();
    is.close();
    /* 这时图片已经被加载到ImageView中. */

        } catch (IOException e) {
                e.printStackTrace();
        }

}	
}

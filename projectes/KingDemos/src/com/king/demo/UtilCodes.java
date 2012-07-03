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
//    	String path = "/sdcard/Tencent/QQ/log/myqqlog.txt";//�õ��ļ�·��
//    	String path = "/sdcard/QQDownload/test.jpg";//�õ��ļ�·��

    	File file = new File(path);//����һ���ļ�����
    	FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			//���ļ�
	    	byte[] buffer = new byte[1024];//����
	    	int len = 0;
	    	ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while( (len = inStream.read(buffer))!= -1){//ֱ�������ļ�����
				   outStream.write(buffer, 0, len);
			}
			
			byte[] data = outStream.toByteArray();//�õ��ļ��Ķ���������
			
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
    /* ������������һ�����õı��ϰ��. */
    BufferedInputStream bis = new BufferedInputStream(is);
    /* ���������ϵ�ͼƬ */
//    Bitmap bm = BitmapFactory.decodeStream(bis);
//    imageview.setImageBitmap(bm);
    bis.close();
    is.close();
    /* ��ʱͼƬ�Ѿ������ص�ImageView��. */

        } catch (IOException e) {
                e.printStackTrace();
        }

}	
}

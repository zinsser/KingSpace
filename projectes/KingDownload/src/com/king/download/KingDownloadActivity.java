package com.king.download;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
  
public class KingDownloadActivity extends Activity {  
    private Button buttontxt;  
    private Button buttonmp3;  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        
        buttontxt=(Button)findViewById(R.id.buttontxt);  
        //为buttontxt添加单击事件监听器  
        buttontxt.setOnClickListener(new OnClickListener(){  
  
            /* (non-Javadoc) 
             * @see android.view.View.OnClickListener#onClick(android.view.View) 
             */  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                //创建一个匿名线程用于下载文件  
                new Thread()  
                {  
                    public void run()  
                    {  
                        HttpDownloader httpDownloader=new HttpDownloader();   
                        //调用httpDownloader对象的重载方法download下载txt文件  
                        String txt=httpDownloader.download("http://192.168.0.119:8080/Android/log.img");
                        System.out.println(txt);  
                    }  
                }.start();  
            }  
              
        });  
        buttonmp3=(Button)findViewById(R.id.buttonmp3);  
        //为buttonmp3添加单击事件监听器  
        buttonmp3.setOnClickListener(new OnClickListener()  
        {  
  
            /* (non-Javadoc) 
             * @see android.view.View.OnClickListener#onClick(android.view.View) 
             */  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                new Thread()  
                {  
                    public void run()  
                    {  
                        try  
                        {  
                            HttpDownloader httpDownloader=new HttpDownloader();  
                            //调用httpDownloader对象的重载方法download下载mp3文件  
                            int result=httpDownloader.download("http://172.24.24.20:8080/Android/music.mp3","Android/","music.mp3");  
                            System.out.println(result);  
                        }  
                        catch(Exception e)  
                        {  
                            e.printStackTrace();  
                        }     
                    }  
                }.start();  
                  
            }  
        });  
    }  
}
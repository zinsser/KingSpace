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
        //Ϊbuttontxt��ӵ����¼�������  
        buttontxt.setOnClickListener(new OnClickListener(){  
  
            /* (non-Javadoc) 
             * @see android.view.View.OnClickListener#onClick(android.view.View) 
             */  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                //����һ�������߳����������ļ�  
                new Thread()  
                {  
                    public void run()  
                    {  
                        HttpDownloader httpDownloader=new HttpDownloader();   
                        //����httpDownloader��������ط���download����txt�ļ�  
                        String txt=httpDownloader.download("http://192.168.0.119:8080/Android/log.img");
                        System.out.println(txt);  
                    }  
                }.start();  
            }  
              
        });  
        buttonmp3=(Button)findViewById(R.id.buttonmp3);  
        //Ϊbuttonmp3��ӵ����¼�������  
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
                            //����httpDownloader��������ط���download����mp3�ļ�  
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
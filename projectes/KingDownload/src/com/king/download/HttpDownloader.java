package com.king.download;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
public class HttpDownloader {  
    private URL url=null;  
    public String download(String urlStr)  
    {  
        StringBuffer stringbuffer=new StringBuffer();  
        String line;  
        BufferedReader bufferReader=null;  
        try  
        {  
            //����һ��URL����  
            url=new URL(urlStr);              
            //�õ�һ��HttpURLConnection����  
            HttpURLConnection httpUrlConnection=(HttpURLConnection) url.openConnection();     
            // �õ�IO����ʹ��IO����ȡ����  
            bufferReader=new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));  
            while((line=bufferReader.readLine())!=null)  
            {                 
                stringbuffer.append(line);  
            }             
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }                 
        return stringbuffer.toString();  
          
    }  
    // �ú����������� -1�����������ļ����� ;0�����������ļ��ɹ�; 1�������ļ��Ѿ�����  
    public int download(String urlStr,String path,String fileName)  
    {  
        InputStream inputstream=null;  
        FileUtils fileUtils=new FileUtils();  
        if(fileUtils.isExist(path+fileName))  
            return 1;  
        else  
        {  
            try {  
                inputstream=getFromUrl(urlStr);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            File file=fileUtils.writeToSDPATHFromInput(path, fileName, inputstream);  
            if(file!=null)  
                return 0;  
            else   
                return -1;  
        }  
    }  
    //����url�ַ����õ�������  
    public InputStream getFromUrl(String urlStr) throws IOException  
    {         
        url=new URL(urlStr);              
        HttpURLConnection httpUrlConnection=(HttpURLConnection) url.openConnection();  
        InputStream input=httpUrlConnection.getInputStream();     
        return input;  
    }  
}
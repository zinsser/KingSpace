package com.king.download;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import android.os.Environment;  
  
  
public class FileUtils {  
    private String SDPATH=null;  
    public String getSDPATH()  
    {  
        return SDPATH;  
    }  
    public FileUtils()  
    {  
        //获得当前外部存储设备SD卡的目录  
        SDPATH=Environment.getExternalStorageDirectory()+"/";  
    }  
    //创建文件  
    public File createFile(String fileName) throws IOException  
    {  
        File file=new File(SDPATH+fileName);  
        file.createNewFile();  
        return file;  
    }  
    //创建目录  
    public File createDir(String fileName) throws IOException  
    {  
        File dir=new File(SDPATH+fileName);       
        dir.mkdir();  
        return dir;  
    }  
    //判断文件是否存在  
    public boolean isExist(String fileName)  
    {  
        File file=new File(SDPATH+fileName);  
        return file.exists();  
    }  
    public File writeToSDPATHFromInput(String path,String fileName,InputStream inputstream)  
    {  
        File file=null;  
        OutputStream outputstream=null;  
        try  
        {  
            createDir(path);  
            file=createFile(path+fileName);  
            outputstream=new FileOutputStream(file);  
            byte buffer[]=new byte[1024];  
            //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中  
            while((inputstream.read(buffer))!=-1)  
            {  
                outputstream.write(buffer);  
            }  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try {  
                outputstream.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        return file;  
    }  
}

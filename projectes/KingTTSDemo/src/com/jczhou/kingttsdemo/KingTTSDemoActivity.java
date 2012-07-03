package com.jczhou.kingttsdemo;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class KingTTSDemoActivity extends Activity implements OnInitListener {
	private static final int REQ_TTS_STATUS_CHECK = 0;  
	private static final String TAG = "TTS Demo";  
	
	private EditText inputText = null;  
	private Button speakBtn = null;  
	private TextToSpeech mTts;  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      //检查TTS数据是否已经安装并且可用  
        Intent checkIntent = new Intent();  
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);  
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);   
        
        inputText = (EditText)findViewById(R.id.txtContent);  
        speakBtn = (Button)findViewById(R.id.btnSpeak);          
		inputText.setText("This is an example of speech synthesis.");  
		speakBtn.setOnClickListener(new OnClickListener() {  
			public void onClick(View v) {  
				//朗读输入框里的内容
				mTts.speak(inputText.getText().toString(), TextToSpeech.QUEUE_ADD, null);  
			}  
		});    
    }
    
    @Override  
    protected void onPause() {  
        super.onPause();  
        if(mTts != null){//activity暂停时也停止TTS  
            mTts.stop();  
        }  
    }  
      
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //释放TTS的资源
        if (mTts != null){
        	mTts.shutdown();  
        }
    } 
    
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if(requestCode == REQ_TTS_STATUS_CHECK) {  
            switch (resultCode) {  
            case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                //这个返回结果表明TTS Engine可以用    
                mTts = new TextToSpeech(this, this);  
                Log.v(TAG, "TTS Engine is installed!");  
                break;  
             //这三种情况都表明数据有错,重新下载安装需要的数据  
            case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:    //需要的语音数据已损坏  
            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:   //缺少需要语言的语音数据
            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME: //缺少需要语言的发音数据
                Log.v(TAG, "Need language stuff:"+resultCode);  
                Intent dataIntent = new Intent();  
                dataIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);  
                startActivity(dataIntent);  
                break;  
            case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL: //检查失败  
            default:  
                Log.v(TAG, "Got a failure. TTS apparently not available");  
                break;  
            }  
        }  
    }      
    
    //实现TTS初始化接口  
    @Override  
    public void onInit(int status) {  
        //TTS Engine初始化完成  
        if(status == TextToSpeech.SUCCESS) {  
            int result = mTts.setLanguage(Locale.US);  
            //设置发音语言  
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){//判断语言是否可用    
                Log.v(TAG, "Language is not available");  
                speakBtn.setEnabled(false);  
            }else{  
            	mTts.speak("This is an example of speech synthesis.", TextToSpeech.QUEUE_ADD, null);  
                speakBtn.setEnabled(true);  
            }  
        }
    }
}
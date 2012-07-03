package com.jczhou.kingcai;

import com.jczhou.kingcai.messageservice.ImageRequestMessage;
import com.jczhou.kingcai.socketservice.SocketService;
import com.jczhou.kingcai.Questions;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class KingCAIServerActivity extends Activity {
	private TextView mLogger = null;
	private String mPeerIP = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mLogger = (TextView)findViewById(R.id.txtLogger);
        mLogger.setScrollContainer(true);

        findViewById(R.id.btnStartServer).setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
       			SocketService.GetInstance().StartService(KingCAIServerActivity.this, new SocketEventWaiter());
    			KingCAIUtils.showLogger(mHandler, "Server Started", "Server");			
			}
        });
        
        findViewById(R.id.btnSendImage).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				SocketService.SendImage(new ImageRequestMessage(0, "/sdcard/QQDownload/sample.png"), mPeerIP);
				String paper = ConstructPaper();
				SocketService.GetInstance().BroadcastPaper(paper);				
    			KingCAIUtils.showLogger(mHandler, "Image has been sent", "Server");				
			}
		});
    }
    
    private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();

			String tag = bundle.getCharSequence("DEST").toString();
			String log = bundle.getString("MSG");
			
			mLogger.append(tag+"|"+log+"\n");
		}
    };
    
    public class SocketEventWaiter implements SocketService.SocketEventWaiter{

		@Override
		public void onQueryRequest(String peerip) {
			KingCAIUtils.showLogger(mHandler, "Query Server Request:" + peerip, "Client");
			mPeerIP = peerip;
		}

		@Override
		public void onQueryFinished() {
			KingCAIUtils.showLogger(mHandler, "Query Server Finished!", "Client");			
		}

		@Override
		public void onLoginRequest(String number, String peerip) {
			KingCAIUtils.showLogger(mHandler, "Login Request: " + number + "/" + peerip, "Client");			
		}

		@Override
		public void onServiceStart(String localip) {
			KingCAIUtils.showLogger(mHandler, "Server Address:" + localip, "Server");			
		}

		@Override
		public void onLoginFinished() {
			SocketService.GetInstance().DispatchPaperTitle("广东省深圳市小学统一考试三年级（上学期）第一单元数学试卷");
		}

		@Override
		public void onPaperReady() {
			KingCAIUtils.showLogger(mHandler, "Paper would be sent to student", "Server");
//			String paper = ConstructPaper();
//			SocketService.GetInstance().BroadcastPaper(paper);
		}
    }
    
    public String ConstructPaper(){
    	String paper = new String("");
		//[answer]xxx[type]x[content]xxx
    	for (int i = 0; i < Questions.sQuestions.length; ++i){
    		paper += "[answer]#"+ Questions.sQuestions[i].mAnswer +"#[type]1[content]" 
    					+ Questions.sQuestions[i].mContent + "@";
    	}
    	return paper;
    }
}
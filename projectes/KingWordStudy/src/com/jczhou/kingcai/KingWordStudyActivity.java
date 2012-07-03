package com.jczhou.kingcai;

import java.util.ArrayList;
import java.util.HashMap;

import com.jczhou.kingcai.wordmanager.WordDBAdapter;
import com.jczhou.kingcai.wordmanager.WordManagerActivity;
import com.jczhou.kingcai.wordmanager.WordValue;
import com.jczhou.kingcai.DictManager.Word;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class KingWordStudyActivity extends Activity {
	private final static int GROUP_NORMAL = 0;
	private final static int MENU_MANAGER_DICT = 0;
	private final static int MENU_HELP = 1;

	private final static int DIALOG_SUCCESS = 0;
	
	private WordDBAdapter wordDBAdapter;
	
	private LinearLayout mRootView = null;
	private FrameLayout mGameView = null;
	private Spinner mDicts = null;
	private DictManager mDictMgr = new DictManager();
	private ArrayList<HolderButton> mButtons = new ArrayList<HolderButton>(); 
	private HashMap<String, Integer> mSplitedWord = new HashMap<String, Integer>();
	private GameManager mController = new GameManager(new GameManager.ManagerListener(){

		public void OnGameRunningStatusChanged(boolean running) {
			if (running){
		        InitWords();
		        SetHolderButtonInfo();
			}
		}

		public void OnAchievementChanged(int score, int level, int leftwords) {
			((TextView)findViewById(R.id.info_score)).setText(" "+score+" ");
			((TextView)findViewById(R.id.info_level)).setText(" "+level+" ");			
		}
		
		public void OnGameOver(boolean success){
			if (success){
				//本轮已完成，需要重新取单词组
				//TODO:此处对话框提示成功及进入下一关
	    		showDialog(DIALOG_SUCCESS);
			}
		}
	}); 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater factory = LayoutInflater.from(this);
        mRootView = (LinearLayout) factory.inflate(R.layout.main, null);
        setContentView(mRootView);

        mDicts = (Spinner)findViewById(R.id.info_dict);        
        InitSpinner(mDicts);
        
        mGameView = (FrameLayout)findViewById(R.id.block_layout);
        MakeGameView(GameManager.mWordRowCount, mGameView);
        
        findViewById(R.id.info_play_pause).setOnClickListener(mControlClickListener);
        findViewById(R.id.info_play_pause).setEnabled(false);
        findViewById(R.id.info_music).setOnClickListener(mControlClickListener);
        findViewById(R.id.info_mind).setOnClickListener(mControlClickListener);        
    }

    private AdapterView.OnItemSelectedListener mDictSelectorListener = new AdapterView.OnItemSelectedListener(){


		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String tag = (String)arg0.getAdapter().getItem(arg2);
			if (arg2 != 0){
				if (mDictMgr.LoadDictToMemory(tag)){
			        findViewById(R.id.info_play_pause).setEnabled(true);
				}else{
					Log.d("KingWordActivity", "Load Dictory error!");
				}
			}
			//Begin Add by lixingang
			WordValue wordValue = new WordValue();
			wordDBAdapter = new WordDBAdapter(KingWordStudyActivity.this);
			wordDBAdapter.open();
			Cursor cursor = null;
			
			switch(arg2){
				case 1:
					wordValue.setGrade(WordValue.THREE);
					wordValue.setRank(WordValue.LAST_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
				case 2:
					wordValue.setGrade(WordValue.THREE);
					wordValue.setRank(WordValue.NEXT_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
				case 3:
					wordValue.setGrade(WordValue.TWO);
					wordValue.setRank(WordValue.LAST_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
				case 4:
					wordValue.setGrade(WordValue.TWO);
					wordValue.setRank(WordValue.NEXT_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
				case 5:
					wordValue.setGrade(WordValue.ONE);
					wordValue.setRank(WordValue.LAST_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
				case 6:
					wordValue.setGrade(WordValue.ONE);
					wordValue.setRank(WordValue.NEXT_SEMESTER);
					cursor = wordDBAdapter.fetchData(wordValue);
					break;
			}
			mDictMgr.setWordList(cursor);
			if(cursor != null){
				cursor.close();
			}
			if(wordDBAdapter != null){
				wordDBAdapter.close();
			}
			//End
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
    };
	
	private void InitSpinner(Spinner dictSpinner){
		ArrayList<String> dicts = new ArrayList<String>();
		dicts.add("请选择词典");
		dicts.addAll(mDictMgr.GetDictorys());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dicts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dictSpinner.setOnItemSelectedListener(mDictSelectorListener);
        dictSpinner.setAdapter(adapter);
	}
	
    private void MakeGameView(int rowCount, FrameLayout gameView){
    	ScrollView sv = new ScrollView(this);
    	
    	LinearLayout table = new LinearLayout(this);
    	table.setOrientation(LinearLayout.VERTICAL);
    	for (int i = 0; i < rowCount; ++i){
        	LinearLayout row = new LinearLayout(this);
        	row.setOrientation(LinearLayout.HORIZONTAL);
    		for (int j = 0; j < rowCount; ++j){
    			HolderButton btn = new HolderButton(this);
    			btn.setVisibility(View.GONE);
    	    	btn.setOnClickListener(mPalyClickListener);
    	    	row.addView(btn);
    	    	mButtons.add(btn);
    		}
    		table.addView(row);
    	}
    	sv.addView(table);
    	gameView.addView(sv);
    } 
    
    private void InitWords(){
    	HashMap<Integer, Word> subWords = mDictMgr.GetWords(mController.GetWordCount());
    	if(subWords != null){
    		Log.v("lxg...InitWords","size1="+subWords.size());
    	}
    	
    	for (Integer id : subWords.keySet()){
			mSplitedWord.put(subWords.get(id).mChineseExplain, id);
			mSplitedWord.put(subWords.get(id).mEnglishWord, id);
    	}
    	if(mSplitedWord != null){
    		Log.v("lxg...InitWords","size2="+mSplitedWord.size());
    	}
    }
    
    private void SetHolderButtonInfo(){
    	int i = 0; 
    	for (String word : mSplitedWord.keySet()){
    		HolderContent content = new HolderContent(word, HolderContent.MakeKeyString(mDictMgr.GetDictName(), mSplitedWord.get(word))); 
    		mButtons.get(i).SetContent(content);
    		mButtons.get(i).setText(content.GetWrapper());
    		mButtons.get(i).setVisibility(View.VISIBLE);
    		i++;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(GROUP_NORMAL, MENU_MANAGER_DICT, 0, "管理词典");
    	menu.add(GROUP_NORMAL, MENU_HELP, 0, "帮助");

    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case MENU_MANAGER_DICT:
    		Intent intent = new Intent(this,WordManagerActivity.class);
//    		intent.setClass(com.jczhou.kingcai.wordmanager, com.jczhou.kingcai.wordmanager.WordManagerActivity.class);
    		startActivity(intent);
    		break;
    	case MENU_HELP:

    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }    

	
    public View.OnClickListener mControlClickListener = new View.OnClickListener() {
    	private Integer mLastSameFirst = 0;
    	private Integer mLastSameSecond = 0;
    	
		public void onClick(View v) {
			switch (v.getId()){
			case R.id.info_mind:
				OnMindRequest();
				break;
			case R.id.info_play_pause:
				if (mDictMgr.IsWordLoaded()){
					OnPlayPauseRequest();
				}else{
					Log.d("Activity", "please load words first");
				}
				break;
			case R.id.info_music:
				OnMusicRequest();
				break;
			}
		}
	    
		class OutParam<T>{
			public T mParam;
			public OutParam(T initValue){
				mParam = initValue;
			}
		}
		
		private void FindEqualElement(ArrayList<HolderButton> buttons, OutParam<Integer> first, OutParam<Integer> second){
			Integer slower = first.mParam;
			Integer faster = second.mParam + 1;
			int loopCnt = 0;
			if (buttons.size() > 0){
				do{
					slower += 1;
					faster += 2;
					loopCnt++;
					if (slower >= buttons.size()){
						slower = 0;
					}
					if (faster >= buttons.size()){
						if (loopCnt >= buttons.size()){
							loopCnt = 0;
						}
						faster = loopCnt;
					}

				}while(!buttons.get(slower).ConnectTo(buttons.get(faster)));
				Log.d("Activity", "find equal elements");
				first.mParam = slower;
				second.mParam = faster;
			}
		}

		private void OnMindRequest(){
			OutParam<Integer> first = new OutParam<Integer>(mLastSameFirst);
			OutParam<Integer> second  = new OutParam<Integer>(mLastSameSecond);
			do {
				first.mParam++;
				second.mParam++;
				FindEqualElement(mButtons, first, second);
			}while((first.mParam < mButtons.size() && !mButtons.get(first.mParam).isShown()) 
					|| (second.mParam < mButtons.size() && !mButtons.get(second.mParam).isShown()));
			mLastSameFirst = first.mParam;
			mLastSameSecond = second.mParam;
			mButtons.get(mLastSameFirst).SetMindedStatus(true);
			mButtons.get(mLastSameSecond).SetMindedStatus(true);
		}
		
		private void OnPlayPauseRequest(){
			if (mController.IsPlaying()){
				mController.StopGame();
			}else{
				mController.StartGame();
			}
		}
		
		private void OnMusicRequest(){			
		}
    };
    
    public View.OnClickListener mPalyClickListener = new View.OnClickListener() {
    	private HolderButton mPrevClickBtn = null;
		public void onClick(View v) {
			if (mPrevClickBtn == null){
				mPrevClickBtn = (HolderButton)v;
				mPrevClickBtn.SetToggle(true);//显示不同状态的背景图片
			}else{
				if (mPrevClickBtn != (HolderButton)v 
						&& mPrevClickBtn.ConnectTo((HolderButton)v)){
					Log.d("OnClickListener", "Success"); //此处播放动画效果
					mPrevClickBtn.setVisibility(View.GONE);
					((HolderButton)v).setVisibility(View.GONE);
					mController.RecordAchievement();
				}else if (mPrevClickBtn != (HolderButton)v){
					Log.d("OnClickListener", "Failure");
					OnOnePairFailure();
				}
				
				mPrevClickBtn.SetToggle(false);//显示不同状态的背景图片
				mPrevClickBtn = null;	
			}
		}
		
		public void OnOnePairFailure(){	
		}		
	};
    
    @Override
    protected Dialog onCreateDialog(int id){
    	if (id == DIALOG_SUCCESS){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this)
    			.setTitle("闯关成功")
    			.setNegativeButton("重玩", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mController.PlayNextLevel(false);
					}
				})
				.setPositiveButton("下一关", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						mController.PlayNextLevel(true);
					}
				});
    		return builder.create();
    	}
    	
    	return super.onCreateDialog(id);
    }
}
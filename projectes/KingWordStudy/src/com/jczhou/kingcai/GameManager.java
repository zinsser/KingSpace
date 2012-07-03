package com.jczhou.kingcai;

import java.util.HashMap;

public class GameManager {
	public final static int mWordRowCount = 4; //表示难易程度的单词行数		
	private int mTotalScore = 0;
	private int mCurLevel = 1; //从第一关开始
	private int mLeftTime = 0;
	private int mLeftWordCount = mWordRowCount * mWordRowCount / 2;
	private boolean mPalying = false;
	private ManagerListener mListener = null;
	private HashMap<Integer, LevelInfo> mLevelScoreMapper = new HashMap<Integer, LevelInfo>();

	public static interface ManagerListener{
		public abstract void OnGameRunningStatusChanged(boolean running);
		public abstract void OnAchievementChanged(int score, int level, int leftwords);
		public abstract void OnGameOver(boolean success);
	};	
	
	public class LevelInfo{
		public int mScore;
		public int mTime;//以秒为单位
		
		public LevelInfo(int score, int time){
			mScore = score;
			mTime = time;
		}
	}
	
	public GameManager(ManagerListener l){
		mListener = l;
		
		for (int i = 0; i < 17; ++i){
			mLevelScoreMapper.put(i, new LevelInfo(5 * i, 900 - i * 10));
		}
	}

	
	public void StartGame(){
		mPalying = true;
		DoGamePrefare();
	}
	
	public void StopGame(){
		mPalying = false;
		mListener.OnGameRunningStatusChanged(mPalying);
	}
	
	public boolean IsPlaying(){
		return mPalying;
	}
	
	public void PlayNextLevel(boolean next){
		if (next){
			mCurLevel++;
		}
		DoGamePrefare();
	}
	
	private void DoGamePrefare(){
		if (mCurLevel < mLevelScoreMapper.size()){
			mLeftTime = mLevelScoreMapper.get(mCurLevel).mTime;	
			mLeftWordCount = mWordRowCount * mWordRowCount / 2;		
			mListener.OnGameRunningStatusChanged(mPalying);
			mListener.OnAchievementChanged(mTotalScore, mCurLevel, mLeftWordCount);
		}
	}
	
	public void RecordAchievement(){
		mTotalScore += mLevelScoreMapper.get(mCurLevel).mScore;
		mLeftWordCount--;
		
		mListener.OnAchievementChanged(mTotalScore, mCurLevel, mLeftWordCount);
		if (mLeftWordCount == 0){
			mListener.OnGameOver(true);
		}
	}
	
	public int GetWordCount(){
		return mLeftWordCount;
	}
}

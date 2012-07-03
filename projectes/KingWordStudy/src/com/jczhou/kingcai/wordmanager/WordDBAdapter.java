package com.jczhou.kingcai.wordmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class WordDBAdapter {
	private Context mContext = null;
	private DatabaseHelper mDBHelper = null;
	private SQLiteDatabase mDB = null;
	
	private ArrayList<Cursor> cursorList = null;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_WORD = "_word";
	public static final String KEY_MEAN = "_mean";
	public static final String KEY_PHONETIC = "_phonetic";
	public static final String KEY_RANK = "_rank";
	public static final String KEY_GRADE = "_grade";
	private static final String DATABASE_NAME = "Word.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final int JUNIR_FIRST = 1;
	private static final int JUNIR_SECOND = 2;
	private static final int JUNIR_THREED = 3;
	
	private static final String TABLE_ONE = "table_1";
	private static final String TABLE_TWO = "table_2";
	private static final String TABLE_THREE = "table_3";
	
	public static final String[] RESULT = {KEY_ID,KEY_WORD,KEY_MEAN,KEY_PHONETIC,KEY_RANK,KEY_GRADE};
	
	private static final String DATABASE_CREATE_ONE = "CREATE TABLE " + TABLE_ONE + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_WORD + " TEXT NOT NULL," + KEY_MEAN + " TEXT NOT NULL,"
			+ KEY_PHONETIC + " TEXT NOT NULL,"+ KEY_GRADE + " TEXT NOT NULL," + KEY_RANK + " TEXT NOT NULL);";
	
	private static final String DATABASE_CREATE_TWO = "CREATE TABLE " + TABLE_TWO + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_WORD + " TEXT NOT NULL," + KEY_MEAN + " TEXT NOT NULL,"
			+ KEY_PHONETIC + " TEXT NOT NULL,"+ KEY_GRADE + " TEXT NOT NULL," + KEY_RANK + " TEXT NOT NULL);";
	
	private static final String DATABASE_CREATE_THREE = "CREATE TABLE " + TABLE_THREE + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_WORD + " TEXT NOT NULL," + KEY_MEAN + " TEXT NOT NULL,"
			+ KEY_PHONETIC + " TEXT NOT NULL," + KEY_GRADE + " TEXT NOT NULL," + KEY_RANK + " TEXT NOT NULL);";
	
	
	public WordDBAdapter(Context context){
		this.mContext = context;
	}
	
	public SQLiteDatabase getDB(){
		if(mDB != null){
			return mDB;
		}
		return null;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		DatabaseHelper(Context context){
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(DATABASE_CREATE_ONE);
				db.execSQL(DATABASE_CREATE_TWO);
				db.execSQL(DATABASE_CREATE_THREE);
			}catch(Exception e){
				Log.v("lxg...WordDBAdapter...erro",e.getMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			try{
				db.execSQL("DROP TABLE IF EXISTS" + TABLE_ONE);
				db.execSQL("DROP TABLE IF EXISTS" + TABLE_TWO);
				db.execSQL("DROP TABLE IF EXISTS" + TABLE_THREE);
				onCreate(db);
			}catch(Exception e){
				
			}
			
		}
		
	}
	
	public WordDBAdapter open() throws SQLException{
		mDBHelper = new DatabaseHelper(mContext);
		try{
			mDB = mDBHelper.getWritableDatabase();
			return this;
		}catch(Exception e){
			mDBHelper.close();
			return null;
		}
	}
	
	public void close(){
		try{
			mDBHelper.close();
		}catch(Exception e){
			
		}
	}
	
	public long insertData(WordValue wordValue){
		if(wordValue != null){
			String tableName = null;
			ContentValues values = new ContentValues();
			values.put(KEY_WORD, wordValue.getWord());
			values.put(KEY_MEAN, wordValue.getMean());
			values.put(KEY_PHONETIC, wordValue.getPhonetic());
			values.put(KEY_RANK, wordValue.getRank());
			values.put(KEY_GRADE, wordValue.getGrade());
			switch(wordValue.getGrade()){
				case JUNIR_FIRST:
					tableName = TABLE_ONE;
					break;
				case JUNIR_SECOND:
					tableName = TABLE_TWO;
					break;
				case JUNIR_THREED:
					tableName = TABLE_THREE;
					break;
			}
			try{
				//先查询数据库中是否存在此单词，如果存在就直接返回。
				Cursor cursor = fetchDataByWord(wordValue);
				if(cursor != null && cursor.getCount() > 0){
					return 0;
				}
				//
				return mDB.insert(tableName, null, values);
			}catch(Exception e){
				Log.v("lxg...WordDBAdapter","insert erro..."+e.getMessage());
				return -1;
			}
		}
		return -1;
	}
	
	public Cursor fetchDataByWord(WordValue wordValue) throws SQLException{
		if(wordValue != null && mDB != null){
			Cursor mCursor = null;
			if(!TextUtils.isEmpty(wordValue.getWord())){
				try{
					//查询初一年级单词表
					mCursor = mDB.query(TABLE_ONE, RESULT, KEY_WORD + "=?"  ,new String[]{ wordValue.getWord()}, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						mCursor.moveToFirst();
						return mCursor;
					}
					//查询初二年级单词表
					mCursor = mDB.query(TABLE_TWO, RESULT, KEY_WORD + "=?"  ,new String[]{ wordValue.getWord()}, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						mCursor.moveToFirst();
						return mCursor;
					}
					//查询初三年级单词表
					mCursor = mDB.query(TABLE_THREE, RESULT, KEY_WORD + "=?"  ,new String[]{ wordValue.getWord()}, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						mCursor.moveToFirst();
						return mCursor;
					}
				}catch(Exception e){
					
				}
			}
		}
		return null;
	}
	
	public List<Cursor> fetchDataByMean(WordValue wordValue) throws SQLException{
		if(wordValue != null && mDB != null){
			Cursor mCursor = null;
			cursorList = new ArrayList<Cursor>();
			if(!TextUtils.isEmpty(wordValue.getMean())){
				try{
					String mean_like = "\'%" + wordValue.getMean() + "%\'";
					String selection = String.format("%s LIKE %s", KEY_MEAN, mean_like);
					Log.v("lxg...WordDBAdapter","selection="+selection);
					//查询初一年级单词表
					mCursor = mDB.query(TABLE_ONE, RESULT, selection  ,null, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						cursorList.add(mCursor);
					}
					//查询初二年级单词表
					Log.v("lxg...WordDBAdapter","selection="+selection);
					mCursor = mDB.query(TABLE_TWO, RESULT, selection  ,null, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						cursorList.add(mCursor);
					}
					//查询初三年级单词表
					mCursor = mDB.query(TABLE_THREE, RESULT, selection  ,null, null, null, null);
					if(mCursor != null && mCursor.getCount() > 0){
						cursorList.add(mCursor);
					}
					return cursorList;
				}catch(Exception e){
					Log.v("lxg..db","query erro..."+e.getMessage());
				}
			}
		}
		return null;
	}
	
	public Cursor fetchData(WordValue wordValue) throws SQLException{
		if(wordValue != null && mDB != null){
			Cursor mCursor = null;
			String tableName = null;
			String selection = String.format("%s=? and %s=?", KEY_GRADE, KEY_RANK);
			if(wordValue.getGrade() > 0){
				switch(wordValue.getGrade()){
				case JUNIR_FIRST:
					tableName = TABLE_ONE;
					break;
				case JUNIR_SECOND:
					tableName = TABLE_TWO;
					break;
				case JUNIR_THREED:
					tableName = TABLE_THREE;
					break;
				}
				try{
					mCursor = mDB.query(tableName, RESULT, selection, new String[]{String.valueOf(wordValue.getGrade()),wordValue.getRank()}, null, null, null);
					return mCursor;
				}catch(Exception e){
					return null;
				}
			}
		}
		
		return null;
	}
}

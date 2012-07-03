package com.jczhou.kingcai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.jczhou.kingcai.wordmanager.WordDBAdapter;
import com.jczhou.kingcai.wordmanager.WordValue;

import android.database.Cursor;
import android.util.Log;

/* 词典管理
 * 从数据库中或词典文本文件中加载词典
 * 注意:音标的显示是一个问题
 * **/
public class DictManager {
	//单条词条
	public static class Word{
		public String mEnglishWord;  //英语单词
		public String mChineseExplain; //中文解释
		public String mSymbol; //音标
		
		public Word(String word, String explain, String symbol){
			mEnglishWord = new String(word);
			mChineseExplain = new String(explain);
			mSymbol = new String(symbol);
		}
	}; 
	
	
	private Word[] sWords = {
			new Word("a", "art.一(个)；任何一(个)；每一(个)", "[ei, ə, æn, ən]"),
			new Word("name", "n.名字；名声 vt.取名；提名；列举", "[ˈneim]"),
			new Word("morning", "n.早晨，上午", "[ˈmɔ:niŋ]"),
			new Word("pen", "n.钢笔，圆珠笔；围栏 vt.把…关在圈中", "[pen]"),
			new Word("good", "a.好的；擅长的； n.善，好处[ pl.]商品", "[gud]"),
			new Word("how", "ad.怎么，怎样；多么，多少", "[hau]"),
			new Word("help", "v.帮(援)助；有助于；[呼救]救命 n.帮助(手)", "[help]"),
			new Word("assist", "n.帮助，协助", "[əˈsist]"),
			new Word("support", "vt./ n.支持，拥护；支撑；供养；证实", "[səˈpɔ:t]"),
			new Word("foster", "vt.收养；培养，促进 a.收养的，收养孩子的", "[ˈfɔstə]"),
			new Word("mind", "n.头脑；智力；想法 v.介意；注意；照料", "[maind]"),
			new Word("body", "n.身体；主体；尸体；物体；一群(批)", "[ˈbɔdi]"),
			new Word("group", "n.组，群；集团 vt.把…分组；使聚集 vi.聚集", "[gru:p]"),
			new Word("grade", "n.等级，级别；成绩；年级 vt.分等；评分", "[greid]"),
			new Word("mark", "n.痕迹；记号；分数 vt.标明；纪念；打分", "[mɑ:k]"),
			new Word("line", "n.(界)线；条纹；方针；线路 v.排队(齐)", "[lain]"),
			new Word("string", "n.弦，线；一串，一行 vt.用线串；悬挂", "[striŋ]"),
			new Word("thread", "n.线(索)，思路；螺纹 vt.穿(过)，通过", "[θred]"),
			new Word("draw", "vt.画；拖；取出； vi.移动； n.平局；抽签", "[drɔ:]"),
			new Word("pull", "v.拉，拖，拔；移动 n.拉，拖；拉(引)力", "[pul]"),
			new Word("drag", "vt.拖，拉；迫使 vi.拖延 n.累赘；一吸", "[dræg]"),
			new Word("delay", "n.耽搁，延迟 vi.耽搁，拖延 vt.耽搁；推迟", "[diˈlei]")
	};
	
	//词典
	private HashMap<Integer, Word> mWords = new HashMap<Integer, Word>();
	private String mDictName = "初中一年级";
	//Begin Add by lixingang
	public ArrayList<Word> wordList = new ArrayList<Word>();
	
	public void setDictName(String dictName){
		this.mDictName = dictName;
	}
	
	public DictManager(){
		for (int i = 0; i < wordList.size(); ++i){
			mWords.put(i, wordList.get(i));
		}
	}
	
	public void setWordList(Cursor cursor){
		wordList.clear();
		if(cursor != null && cursor.getCount() > 0){
			if(cursor.moveToFirst()){
				do{
					String resultWord = cursor.getString(cursor.getColumnIndex(WordDBAdapter.KEY_WORD));
					String resultMean = cursor.getString(cursor.getColumnIndex(WordDBAdapter.KEY_MEAN));
					String resultPhonetic = cursor.getString(cursor.getColumnIndex(WordDBAdapter.KEY_PHONETIC));
					Word resultWordValue = new Word(resultWord,resultMean,resultPhonetic);
					wordList.add(resultWordValue);
				}while(cursor.moveToNext());
			}
			cursor.close();
		}
		setWord();
	}
	
	private void setWord(){
		mWords.clear();
		for (int i = 0; i < wordList.size(); ++i){
			mWords.put(i, wordList.get(i));
		}
	}
	//End
	
	public String GetDictName(){
		return mDictName;
	}
	
	public int GetWordCount(){
		return mWords.size();
	}
	
	public Set<Integer> GetIDs(){
		return mWords.keySet();
	}
	
	public ArrayList<String> GetDictorys(){
		ArrayList<String> dicts =  new	ArrayList<String>();
		dicts.add("初中三年级上");
		dicts.add("初中三年级下");
		dicts.add("初中二年级上");
		dicts.add("初中二年级下");
		dicts.add("初中一年级上");
		dicts.add("初中一年级下");
		return dicts;
	}
	
	public HashMap<Integer, Word> GetWords(int count){
		HashMap<Integer, Word> subWords = new HashMap<Integer, Word>();
		if (count < mWords.size()){
			int i = 0;
			for (Integer id : mWords.keySet()){
				subWords.put(id, mWords.get(id));
				if (++i == count){
					break;
				}
			}
			return subWords;
		}else{
			return mWords;
		}
	}
	
	public String GetEnglishWord(Integer id){
		return mWords.get(id).mEnglishWord;
	}
	
	public String GetChineseExplain(Integer id){
		return mWords.get(id).mChineseExplain;
	}
	
	public String GetSymbol(Integer id){
		return mWords.get(id).mSymbol;
	}
	
	//TODO:
	public void LoadDictFromDB(){
		
	}
	
	public void ReadDictFromFile(){
		
	}

	private boolean mLoaded = false;
	
    //返回值表示加载成功（true）或失败（false）
	public boolean LoadDictToMemory(String dictName){
		mLoaded = true;
		return true;
	}
	
	public boolean IsWordLoaded(){
		return mLoaded;
	}
}

package com.jczhou.kingcai.wordmanager;

public class WordValue {
	//单词
	private String word;
	//词义
	private String mean;
	//音标
	private String phonetic;
	//等级
	private String rank;
	//班级
	private int grade;
	
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	
	public static final String LAST_SEMESTER = "LAST";
	public static final String NEXT_SEMESTER = "NEXT";
	
	public void setWord(String word){
		this.word = word;
	}
	public String getWord(){
		return this.word;
	}
	
	public void setMean(String mean){
		this.mean = mean;
	}
	public String getMean(){
		return this.mean;
	}
	
	public void setPhonetic(String phonetic){
		this.phonetic = phonetic;
	}
	public String getPhonetic(){
		return this.phonetic;
	}
	
	public void setRank(String rank){
		this.rank = rank;
	}
	public String getRank(){
		return this.rank;
	}
	
	public void setGrade(int grade){
		this.grade = grade;
	}
	public int getGrade(){
		return this.grade;
	}
}

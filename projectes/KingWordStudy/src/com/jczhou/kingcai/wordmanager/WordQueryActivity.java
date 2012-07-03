package com.jczhou.kingcai.wordmanager;

import java.util.ArrayList;
import java.util.List;

import com.jczhou.kingcai.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WordQueryActivity extends Activity implements View.OnClickListener{
	private EditText valueText;
	private TextView textView;
	private TextView queryTextView;
	private WordDBAdapter wordDBAdapter;
	private List<Cursor> cursorList = null;
	
	private ListView list;
	private ArrayList<WordValue> myList;
	private ListAdapter listAdapter;
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.word_query);
		
		valueText = (EditText)findViewById(R.id.queryEdit);
		textView = (TextView)findViewById(R.id.queryText);
		queryTextView = (TextView)findViewById(R.id.queryTextView);
		Button queryButton = (Button)findViewById(R.id.queryButton);
		queryButton.setOnClickListener(this);
		
		list = (ListView)findViewById(R.id.myListView);
		myList = new ArrayList<WordValue>();
		myList.clear();
		listAdapter = null;
	}

	public void onClick(View view) {
		int id = view.getId();
		switch(id){
			case R.id.queryButton:
				WordValue wordValue = new WordValue();
				wordDBAdapter = new WordDBAdapter(this);
				wordDBAdapter.open();
				if(TextUtils.isEmpty(valueText.getText().toString())){
					Toast toast = Toast.makeText(this, "请输入需要查询的内容！", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP, 0, 220);
					toast.show();
					break;
				}
				if(wordDBAdapter != null){
					if(judgeLanguage(valueText.getText().toString().substring(0, 1))){
						Log.v("lxg...WordQueryActivity","language is chinese!");
						wordValue.setMean(valueText.getText().toString());
						
						cursorList = wordDBAdapter.fetchDataByMean(wordValue);
						myList.clear();
						
						if(cursorList != null&& cursorList.size() > 0){
							for(Cursor cursor:cursorList){
								if(cursor != null && cursor.getCount() > 0){
									if(cursor.moveToFirst()){
										do{
											WordValue resultWordValue = new WordValue();
											resultWordValue.setWord(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_WORD)));
											resultWordValue.setMean(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_MEAN)));
											resultWordValue.setPhonetic(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_PHONETIC)));
											resultWordValue.setGrade(cursor.getInt(cursor.getColumnIndex(wordDBAdapter.KEY_GRADE)));
											myList.add(resultWordValue);
										}while(cursor.moveToNext());
									}
									cursor.close();
								}
							}
							wordDBAdapter.close();
						}
						
						listAdapter = new ListAdapter(this,myList);
						list.setAdapter(listAdapter);
						if(myList != null && myList.size() > 0){
							list.setVisibility(View.VISIBLE);
							textView.setVisibility(View.GONE);
						}else{
							list.setVisibility(View.GONE);
							textView.setVisibility(View.VISIBLE);
							textView.setText("抱歉，没有找到相关信息！");
						}
					}else{
						Log.v("lxg...WordQueryActivity","language is english!");
						wordValue.setWord(valueText.getText().toString());
						
						Cursor cursor = wordDBAdapter.fetchDataByWord(wordValue);
						myList.clear();
						
						if(cursor != null && cursor.getCount() > 0){
							if(cursor.moveToFirst()){
								do{
									WordValue resultWordValue = new WordValue();
									resultWordValue.setWord(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_WORD)));
									resultWordValue.setMean(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_MEAN)));
									resultWordValue.setPhonetic(cursor.getString(cursor.getColumnIndex(wordDBAdapter.KEY_PHONETIC)));
									resultWordValue.setGrade(cursor.getInt(cursor.getColumnIndex(wordDBAdapter.KEY_GRADE)));
									myList.add(resultWordValue);
								}while(cursor.moveToNext());
							}
							cursor.close();
						}
						wordDBAdapter.close();
						listAdapter = new ListAdapter(this,myList);
						list.setAdapter(listAdapter);
						if(myList != null && myList.size() > 0){
							list.setVisibility(View.VISIBLE);
							textView.setVisibility(View.GONE);
						}else{
							list.setVisibility(View.GONE);
							textView.setVisibility(View.VISIBLE);
							textView.setText("抱歉，没有找到相关信息！");
						}
					}
					
				}
				break;
		}
	}
	
	private class ListAdapter extends BaseAdapter{
		private Context context;
		private ViewHolder holder;
		
		private class ViewHolder{
			TextView wordText;
			TextView meanText;
			TextView phoneticText;
			TextView gradeText;
		}
		
		public ListAdapter(Context c, ArrayList<WordValue> arrayList){
			context = c;
		}
		
		public int getCount() {
			// TODO Auto-generated method stub
			return myList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return myList.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(context);
			String word = myList.get(position).getWord();
			String mean = myList.get(position).getMean();
			String phonetic = myList.get(position).getPhonetic();
			int grade = myList.get(position).getGrade();
			
			convertView = inflater.inflate(R.layout.list_view, null);
			holder = new ViewHolder();
			holder.wordText = (TextView)convertView.findViewById(R.id.itemWordText);
			holder.meanText = (TextView)convertView.findViewById(R.id.itemMeanText);
			holder.phoneticText = (TextView)convertView.findViewById(R.id.itemPhoneticText);
			holder.gradeText = (TextView)convertView.findViewById(R.id.itemGradeText);
			
			holder.wordText.setText("单词：" + word);
			holder.meanText.setText("词义：" + mean);
			holder.phoneticText.setText("音标：" + phonetic);
			holder.gradeText.setText("班级：" + String.valueOf(grade));
			convertView.setTag(holder);
			
			return convertView;
		}
		
	}
	
	private boolean judgeLanguage(String string){
		if(!TextUtils.isEmpty(string)){
			if(string.getBytes().length > 1){
				return true;
			}
		}
		return false;
	}
}

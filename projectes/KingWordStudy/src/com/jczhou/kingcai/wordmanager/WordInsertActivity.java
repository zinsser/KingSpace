package com.jczhou.kingcai.wordmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.jczhou.kingcai.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WordInsertActivity extends Activity  implements View.OnClickListener{
	
	private static final String TAG = "WordInsertActivity";
	private EditText wordEditText;
	private EditText meanEditText;
	private EditText phoneticEditText;
	private TextView wordTextView;
	private TextView meanTextView;
	private TextView phoneticTextView;
	private TextView gradeTextView;
	private TextView rankTextView;
	private RadioGroup radioGroup;
	private RadioButton easyRadioButton;
	private RadioButton hardRadioButton;
	private Spinner spinner;
	private Button insertButton;
	
	private TextView insertTextView;
	private Spinner insertSpinner;
	
	private TextView selectFileTextView;
	private EditText insertWordEditText;
	private Button browseButton;
	private Button multiSaveButton;
	
	private ListView nameListView;
	private ArrayList nameArrayList;
	private ArrayList pathArrayList;
	private int arrayId;
	
	private WordDBAdapter wordDBAdapter;
	private WordValue wordValue;
	
	private static final String[] grade = {"","初一年级","初二年级","初三年级"};
	private ArrayAdapter<String> adapter;
	
	private static final String[] selectWay = {"","单个新增","多个新增"};
	private ArrayAdapter<String> selectAdapter;
	
	
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.word_insert);
		
		initView();
		wordValue = new WordValue();
		nameArrayList = new ArrayList();
		pathArrayList = new ArrayList();
		
		selectAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectWay);
		selectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		insertSpinner.setAdapter(selectAdapter);
		insertSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				switch(position){
					case 1:
						showSingleInsertView();
						break;
					case 2:
						showMultiInsertView();
						break;
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,grade);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				switch(position){
					case 1:
						wordValue.setGrade(WordValue.ONE);
						break;
					case 2:
						wordValue.setGrade(WordValue.TWO);
						break;
					case 3:
						wordValue.setGrade(WordValue.THREE);
						break;
				}
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Log.v("lxg...WordInsertActivity","onNothingSelected");
			}
			
		});
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if(checkedId == easyRadioButton.getId()){
					wordValue.setRank(WordValue.LAST_SEMESTER);
				}else{
					wordValue.setRank(WordValue.NEXT_SEMESTER);
				}
			}
		});
		
		insertButton.setOnClickListener(this);
		browseButton.setOnClickListener(this);
		multiSaveButton.setOnClickListener(this);
		
		nameListView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				arrayId = position;
				HashMap map = (HashMap) nameArrayList.get(position); 
				insertWordEditText.setText("");
				insertWordEditText.setText((CharSequence) map.get("Name"));
			}
			
		});
	}
	
	private void initView(){
		//单个新增
		wordEditText = (EditText)findViewById(R.id.insertWordEditText);
		meanEditText = (EditText)findViewById(R.id.insertMeanEditText);
		phoneticEditText = (EditText)findViewById(R.id.insertPhoneticEditText);
		wordTextView = (TextView)findViewById(R.id.insertWordView);
		meanTextView = (TextView)findViewById(R.id.insertMeanView);
		phoneticTextView = (TextView)findViewById(R.id.insertPhoneticView);
		gradeTextView = (TextView)findViewById(R.id.insertGradeView);
		rankTextView = (TextView)findViewById(R.id.insertRankView);
		radioGroup = (RadioGroup)findViewById(R.id.rankRadioGroup);
		easyRadioButton = (RadioButton)findViewById(R.id.easyRadioButton);
		hardRadioButton = (RadioButton)findViewById(R.id.hardRadioButton);
		spinner = (Spinner)findViewById(R.id.gradeSpinner);
		insertButton = (Button)findViewById(R.id.insertButton);
		//多个新增
		selectFileTextView = (TextView)findViewById(R.id.selectFileTextView);
		insertWordEditText = (EditText)findViewById(R.id.multiInsertWordEditText);
		browseButton = (Button)findViewById(R.id.browseButton);
		multiSaveButton = (Button)findViewById(R.id.multiSaveButton);
		
		nameListView = (ListView)findViewById(R.id.nameListView);
		
		wordEditText.setVisibility(View.GONE);
		meanEditText.setVisibility(View.GONE);
		phoneticEditText.setVisibility(View.GONE);
		wordTextView.setVisibility(View.GONE);
		meanTextView.setVisibility(View.GONE);
		phoneticTextView.setVisibility(View.GONE);
		gradeTextView.setVisibility(View.GONE);
		rankTextView.setVisibility(View.GONE);
		radioGroup.setVisibility(View.GONE);
		easyRadioButton.setVisibility(View.GONE);
		hardRadioButton.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		insertButton.setVisibility(View.GONE);
		
		selectFileTextView.setVisibility(View.GONE);
		insertWordEditText.setVisibility(View.GONE);
		browseButton.setVisibility(View.GONE);
		multiSaveButton.setVisibility(View.GONE);
		
		insertTextView = (TextView)findViewById(R.id.selectInsertView);
		insertSpinner = (Spinner)findViewById(R.id.insertSpinner);
	}

	private void showSingleInsertView(){
		wordEditText.setVisibility(View.VISIBLE);
		meanEditText.setVisibility(View.VISIBLE);
		phoneticEditText.setVisibility(View.VISIBLE);
		wordTextView.setVisibility(View.VISIBLE);
		meanTextView.setVisibility(View.VISIBLE);
		phoneticTextView.setVisibility(View.VISIBLE);
		gradeTextView.setVisibility(View.VISIBLE);
		rankTextView.setVisibility(View.VISIBLE);
		radioGroup.setVisibility(View.VISIBLE);
		easyRadioButton.setVisibility(View.VISIBLE);
		hardRadioButton.setVisibility(View.VISIBLE);
		spinner.setVisibility(View.VISIBLE);
		insertButton.setVisibility(View.VISIBLE);
		
		selectFileTextView.setVisibility(View.GONE);
		insertWordEditText.setVisibility(View.GONE);
		browseButton.setVisibility(View.GONE);
		multiSaveButton.setVisibility(View.GONE);
		
		nameListView.setVisibility(View.GONE);
	}
	
	private void showMultiInsertView(){
		wordEditText.setVisibility(View.GONE);
		meanEditText.setVisibility(View.GONE);
		phoneticEditText.setVisibility(View.GONE);
		wordTextView.setVisibility(View.GONE);
		meanTextView.setVisibility(View.GONE);
		phoneticTextView.setVisibility(View.GONE);
		gradeTextView.setVisibility(View.GONE);
		rankTextView.setVisibility(View.GONE);
		radioGroup.setVisibility(View.GONE);
		easyRadioButton.setVisibility(View.GONE);
		hardRadioButton.setVisibility(View.GONE);
		spinner.setVisibility(View.GONE);
		insertButton.setVisibility(View.GONE);
		
		selectFileTextView.setVisibility(View.VISIBLE);
		insertWordEditText.setVisibility(View.VISIBLE);
		browseButton.setVisibility(View.VISIBLE);
		multiSaveButton.setVisibility(View.VISIBLE);
		
		nameListView.setVisibility(View.VISIBLE);
	}
	
	private void getValue(){
		wordValue.setWord(wordEditText.getText().toString());
		wordValue.setMean(meanEditText.getText().toString());
		wordValue.setPhonetic(phoneticEditText.getText().toString());
	}
	
	private void initValue(){
		wordEditText.setText("");
		meanEditText.setText("");
		phoneticEditText.setText("");
		spinner.setSelection(0);
		easyRadioButton.setChecked(false);
		hardRadioButton.setChecked(false);
		wordValue.setGrade(0);
	}
	
	private void displayToast(String string){
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 220);
		toast.show();
	}
	
	private boolean judgeValue(WordValue wordValue){
		if(TextUtils.isEmpty(wordValue.getWord())){
			displayToast("单词不能为空！");
			return false;
		}
		if(TextUtils.isEmpty(wordValue.getMean())){
			displayToast("词义不能为空！");
			return false;
		}
		if(TextUtils.isEmpty(wordValue.getPhonetic())){
			displayToast("音标不能为空！");
			return false;
		}
		if(wordValue.getGrade() <= 0){
			displayToast("班级不能为空！");
			return false;
		}
		if(TextUtils.isEmpty(wordValue.getRank())){
			displayToast("等级不能为空！");
			return false;
		}
		return true;
	}
	
	public void onClick(View view) {
		int id = view.getId();
		switch(id){
			case R.id.insertButton:
				wordDBAdapter = new WordDBAdapter(this);
				wordDBAdapter.open();
				getValue();
				if(judgeValue(wordValue)){
					try{
						long resultLong = wordDBAdapter.insertData(wordValue);
						if(resultLong == 0){
							displayToast("单词数据库中已经存在此单词！");
						}
						initValue();
					}catch(Exception e){
						Log.v(TAG,e.getMessage());
					}
				}
				if(wordDBAdapter != null){
					wordDBAdapter.close();
				}
				break;
				
			case R.id.browseButton:
				
				ProgressDialog mypDialog=new ProgressDialog(this);
	            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            mypDialog.setTitle("加载文件");
	            mypDialog.setMessage(getResources().getString(R.string.ProgressDialogMessage));
	            mypDialog.setIndeterminate(false);
	            mypDialog.setCancelable(true);
	            mypDialog.show();
				
				queryAllFiles();
				mypDialog.dismiss();
				break;
				
			case R.id.multiSaveButton:
				if(TextUtils.isEmpty(insertWordEditText.getText().toString())){
					displayToast("请选择需要导入的文件！");
					break;
				}
				wordDBAdapter = new WordDBAdapter(this);
				wordDBAdapter.open();
				wordDBAdapter.getDB().beginTransaction();
				try{
					saveSelectFile();
					wordDBAdapter.getDB().setTransactionSuccessful();
				}finally{
					wordDBAdapter.getDB().endTransaction();
				}
				if(wordDBAdapter != null){
					wordDBAdapter.close();
				}
				insertWordEditText.setText("");
				break;
		}
	}
	
	private void queryAllFiles(){
		if (Environment.getExternalStorageState().equals(   
                Environment.MEDIA_MOUNTED)) {   
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径    
            // File path = new File("/mnt/sdcard/");    
            File[] files = path.listFiles();// 读取    
            getFileName(files);   
        }   
		
        SimpleAdapter adapter = new SimpleAdapter(this, nameArrayList, R.layout.name_list_view,   
                new String[] { "Name" }, new int[] { R.id.nameText });   
        nameListView.setAdapter(adapter);  
	}
	
	private void getFileName(File[] files) {   
		nameArrayList.clear();
        if (files != null) {// 先判断目录是否为空，否则会报空指针    
            for (File file : files) {   
                if (file.isDirectory()) {   
                    getFileName(file.listFiles());   
                } else {   
                    String fileName = file.getName();   
                    if (fileName.endsWith(".txt")) {  
                        HashMap map = new HashMap();   
                        String s = fileName.substring(0,fileName.lastIndexOf(".")).toString();   
                        map.put("Name", fileName.substring(0,fileName.lastIndexOf(".")));   
                        nameArrayList.add(map);
                        pathArrayList.add(file.getAbsolutePath());
                    }   
                }   
            }   
        }   
    }  
	
	private void saveSelectFile(){
		File file = new File((String) pathArrayList.get(arrayId));
		
		if(file.exists()){
			try{
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
				
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = null;
				while((line = bufferedReader.readLine()) != null){
					dealLine(line);
				}
			}catch(Exception e){
				displayToast("文件格式不对!");
				Log.v("lxg...saveSelectFile ","exception:"+e.getMessage());
			}
		}
	}

	private void dealLine(String line){
 		String[] array = line.split(",");
		if(array != null && array.length == 5){
			WordValue wordValue = new WordValue();
			wordValue.setWord(array[0]);
			wordValue.setMean(array[1]);
			wordValue.setPhonetic(array[2]);
			wordValue.setGrade(Integer.parseInt(array[3]));
			wordValue.setRank(array[4]);
			try{
				wordDBAdapter.insertData(wordValue);
			}catch(Exception e){
				Log.v(TAG,e.getMessage());
			}
		}
		else{
			displayToast("文件格式不对!");
			Log.v("lxg...dealLine ","fashion is wrong");
		}
	}
}

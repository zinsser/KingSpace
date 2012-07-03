package com.jczhou.mindsnap;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UnblockableEditText extends TableLayout{
	private TextView mInnerTextView = null;
	private EditText mInnerEditText = null;
		
	public UnblockableEditText(Context context) {
		super(context);
		LayoutChildren(context);
		setBackgroundColor(Color.rgb(244, 239, 197));
		setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	
	public UnblockableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutChildren(context);
		setBackgroundColor(Color.rgb(244, 239, 197));
		setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	private void SetTextViewClickListener(TextView v){
        v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mInnerEditText.setText(mInnerTextView.getText());
				mInnerTextView.setVisibility(View.GONE);
				mInnerEditText.setVisibility(View.VISIBLE);				
			}
		});		
	}
	
	private void SetEditTextActionListener(EditText et){
		et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE
						|| actionId ==  EditorInfo.IME_ACTION_UNSPECIFIED){
					mInnerTextView.setText(mInnerEditText.getText());
					HiddenKeyBoard(mInnerEditText);
					mInnerEditText.setVisibility(View.GONE);
					mInnerTextView.setVisibility(View.VISIBLE);				
					return true;
				}
				
				return false;
			}
		});
	}
	
	private TextView CreatNullTextView(Context context){
		TextView nullView = new TextView(context);
		nullView.setWidth(20);
		nullView.setHeight(20);
		return nullView;
	}
	
	private TableRow CreateNullTableRow(Context context){
		TableRow row = new TableRow(context);
		for (int i = 0; i < 3; ++i){
			row.addView(CreatNullTextView(context));
		}
		return row;
	}
	
	private void LayoutChildren(Context context){

		addView(CreateNullTableRow(context));
		
		TableRow row2 = new TableRow(context);
		
		row2.addView(CreatNullTextView(context));

		TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout ll = new LinearLayout(context);
		
		mInnerTextView = new TextView(context);		
		mInnerTextView.setText("wealth");
		mInnerTextView.setLayoutParams(lp);
		SetTextViewClickListener(mInnerTextView);
		mInnerTextView.setVisibility(View.VISIBLE);
		mInnerTextView.setWidth(200);
		mInnerTextView.setTextSize(22);
		ll.addView(mInnerTextView);
		
		mInnerEditText = new EditText(context);
		SetEditTextActionListener(mInnerEditText);
		mInnerEditText.setLayoutParams(lp);		
		mInnerEditText.setVisibility(View.GONE);
		mInnerEditText.setWidth(200);
		mInnerEditText.setTextSize(22);		
		ll.addView(mInnerEditText);
		
		row2.addView(ll);
		row2.addView(CreatNullTextView(context));
		
		addView(row2);

		addView(CreateNullTableRow(context));		
	}

	public void setText(CharSequence text){
		if ((mInnerTextView != null && mInnerTextView.getVisibility() == View.VISIBLE)
				&& (mInnerEditText != null && mInnerEditText.getVisibility() == View.GONE)){
			mInnerTextView.setText(text);
		}else if ((mInnerEditText != null && mInnerEditText.getVisibility() == View.VISIBLE)
				&& (mInnerTextView != null && mInnerTextView.getVisibility() == View.GONE)){
			mInnerEditText.setText(text);		
		}
	}
	
    private void HiddenKeyBoard(View v){
		InputMethodManager im =(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
		im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
    }	
}

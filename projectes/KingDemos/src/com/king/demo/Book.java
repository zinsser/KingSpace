package com.king.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Book extends Activity {
    /** Called when the activity is first created. */
	eBook mBook;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mBook = (eBook)findViewById(R.id.my_book);
        mBook.addLayoutRecForPage(R.layout.page,21);
        mBook.setListener(new eBook.Listener() {
   public void onPrevPage() {
    updateContent();
   }
   public void onNextPage() {
    updateContent();
   }
   public void onInit() {
    updateContent();
   }
  });
    }
    
    private void updateContent(){
     int index = mBook.getIndexForLeftPage();
     View left = mBook.getLeftPage(),right = mBook.getRightPage();
        View next1 = mBook.getNextPage1(),next2 = mBook.getNextPage2();
        View prev1 = mBook.getPrevPage1(),prev2 = mBook.getPrevPage2();
        if(left != null)setImg(left,index);
        if(right != null)setImg(right,index+1);
        if(next1 != null)setImg(next1,index+2);
        if(next2 != null)setImg(next2,index+3);
        if(prev1 != null)setImg(prev1,index-1);
        if(prev2 != null)setImg(prev2,index-2);
        mBook.invalidate(); 
    }
    
 private void setImg(View v , int index){
  if(index >= 0 && index < 20){
   ImageView img = (ImageView)v.findViewById(R.id.book_img);
   if(img == null)return;
   Log.d("eBook","set Img");
   switch(index%6){
   case 0:
    img.setImageResource(R.drawable.p1);
    break;
   case 1:
    img.setImageResource(R.drawable.p2);
    break;
   case 2:
    img.setImageResource(R.drawable.p3);
    break;
   case 3:
    img.setImageResource(R.drawable.p4);
    break;
   case 4:
    img.setImageResource(R.drawable.p5);
    break;
   case 5:
    img.setImageResource(R.drawable.p6);
    break;
   default:
    break;
   }
  }
 }
}

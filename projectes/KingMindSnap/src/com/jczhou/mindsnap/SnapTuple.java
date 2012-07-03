package com.jczhou.mindsnap;

import java.util.ArrayList;

public class SnapTuple {
	private String mContent;
	private SnapTuple mParent = null;
	private ArrayList<SnapTuple> mChildren = new ArrayList<SnapTuple>();
	
	public SnapTuple(SnapTuple parent, String content){
		mParent = parent;
		mContent = content;
	}
	
	public void AddChild(SnapTuple child){
		if (!mChildren.contains(child)){
			mChildren.add(child);
		}
	}
	
	public void AddSibling(SnapTuple sibling){
		if (mParent != null){
			mParent.AddChild(sibling);
		}else{//TODO:
		}
	}
}

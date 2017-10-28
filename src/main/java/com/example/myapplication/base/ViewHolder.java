/**
 *
 */
package com.example.myapplication.base;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * class name: ViewHolder 
 * class description: ViewHolder封装 
 * author: zp 
 * create time: 016-4-5 上午10:48:43 
 * modify by:
 */
public class ViewHolder {
	private static final String TAG = ViewHolder.class.getSimpleName();
	private SparseArray<View> mHolder = null;
	private View mItemView;

	public ViewHolder(View itemView) {
		if (itemView == null) {
			throw new IllegalStateException("item view 不能为空！");
		}
		this.mItemView = itemView;
		mHolder = new SparseArray<>();
	}

	@SuppressWarnings("unchecked")
	public <V extends View> V get(int id) {
		View childView = mHolder.get(id);
		if (null != childView) {
			return (V) childView;
		}
		childView = mItemView.findViewById(id);
		if (null == childView) {
			Log.e(TAG, "no view that id is " + id);
			return null;
		}
		mHolder.put(id, childView);
		return (V) childView;
	}
	
	/**
	 * @return the mItemView
	 */
	public View getItemView() {
		return mItemView;
	}
}

package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.bean.ItemData;
import com.example.myapplication.interfaces.OnDrawInterface;
import com.example.myapplication.util.ScreenUtil;
import com.example.myapplication.view.MyImageView;

import java.util.List;

/**
 * Created by cqian on 2017/10/27.
 */

public class MainAdapter extends android.widget.BaseAdapter {
    private final Context context;
    private final List list;
    private int height;
    private int num;
    private int paintWidth;
    ProgressDialog progressDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ((MainActivity) context).loadmore();
            progressDialog.dismiss();
        }
    };

    public MainAdapter(Context context, List list, int height, int num) {
        this.context = context;
        this.list = list;
        this.height = height;
        this.num = num;
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载...");
    }

    @Override
    public int getItemViewType(int position) {  //20     
        if (position == list.size()) {
            return 1;
        }
        return 0;
    }


    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public void setNum(int num) {
        this.num = num;
    }


    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("WrongConstant")
    public View getView(final int position, View convertView, ViewGroup parent) {
        MoreHolder holder = null;
        ViewHolder viewHolder = null;
        switch (getItemViewType(position)) {
            case 1:
                if (convertView == null) {
                    holder = new MoreHolder();
                    convertView = View.inflate(context, R.layout.load_more, null);
                    convertView.setTag(holder);
                } else {
                    holder = (MoreHolder) convertView.getTag();
                }
                //加载更多
                progressDialog.show();
                handler.sendEmptyMessageDelayed(0,2000);
                break;
            default:
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(context, R.layout.main_item, null);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (position < list.size()) {
                    final ItemData item = (ItemData) list.get(position);

                    viewHolder.imageView = (MyImageView) convertView.findViewById(R.id.imageView);
                    viewHolder.hint = (ImageView) convertView.findViewById(R.id.hint);
                    viewHolder.rbFault = (RadioButton) convertView.findViewById(R.id.rbFault);
                    viewHolder.rbOk = (RadioButton) convertView.findViewById(R.id.rbOk);
                    viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.root);
                    ViewGroup.LayoutParams layoutParams = viewHolder.relativeLayout.getLayoutParams();
                    layoutParams.height = (int) (height / Math.sqrt(num));
                    layoutParams.width = (int) ((int) ScreenUtil.getScreenWidth((MainActivity) context) / Math.sqrt(num));
                    viewHolder.relativeLayout.setLayoutParams(layoutParams);
                    if (!item.init) {
                        item.init = true;
                        viewHolder.imageView.init(layoutParams.width, layoutParams.height);
                    }
                    if (0 == item.hint) {
                        viewHolder.hint.setVisibility(View.GONE);
                        if (item.restore) {
                            viewHolder.imageView.restore();
                        }
                        ((MainActivity) context).setInterceptTouchEvent(false);
                        viewHolder.rbOk.setChecked(true);
                    } else {
                        viewHolder.hint.setVisibility(View.VISIBLE);
                        viewHolder.hint.setBackgroundResource(R.mipmap.icon_delete_red);
                        viewHolder.rbFault.setChecked(true);
                    }
                    final ViewHolder finalViewHolder = viewHolder;
                    viewHolder.imageView.setDrawInterface(new OnDrawInterface() {
                        @Override
                        public void hasDraw() {
                            //让gridView不要拦截事件
                            ((MainActivity) context).setInterceptTouchEvent(true);
                            finalViewHolder.hint.setVisibility(View.VISIBLE);
                            finalViewHolder.hint.setBackgroundResource(R.mipmap.icon_delete_red);
                            item.hint = 1;
                            finalViewHolder.rbFault.setChecked(true);
                        }
                    });
                    viewHolder.hint.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (0 == item.hint) {
                                //表示初始化状态
                                item.hint = 1;
                            } else if (1 == item.hint) {
                                //表示可清空状态，此状态点击后可初始化画布
                                item.hint = 0;
                                item.restore = true;
                            }
                            notifyDataSetChanged();
                        }
                    });
                }
                break;
        }


        return convertView;
    }

    class ViewHolder {
        MyImageView imageView;
        ImageView hint;
        RadioButton rbFault, rbOk;
        RelativeLayout relativeLayout;
    }

    class MoreHolder {
        TextView loading_txt;
    }
}

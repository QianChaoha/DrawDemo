package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.base.BaseAdapter;
import com.example.myapplication.base.ViewHolder;
import com.example.myapplication.bean.ItemData;
import com.example.myapplication.interfaces.OnDrawInterface;
import com.example.myapplication.util.ScreenUtil;
import com.example.myapplication.view.MyImageView;

import java.util.List;

/**
 * Created by cqian on 2017/10/27.
 */

public class MainAdapter extends BaseAdapter<ItemData> {
    private int height;
    private int num;

    public MainAdapter(Context context, List list, int height, int num) {
        super(context, list);
        this.height = height;
        this.num = num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_item;
    }

    @Override
    protected void onViewCreated(ViewHolder holder, final int position) {
        final MyImageView imageView = holder.get(R.id.imageView);
        final ImageView hint = holder.get(R.id.hint);
        final RelativeLayout relativeLayout = holder.get(R.id.root);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.height = (int) (height / Math.sqrt(num));
        layoutParams.width = (int) ((int)ScreenUtil.getScreenWidth((MainActivity)context)/Math.sqrt(num));
        relativeLayout.setLayoutParams(layoutParams);
        if (!getItem(position).init){
            getItem(position).init=true;
            imageView.init(layoutParams.width,layoutParams.height);
        }
        if (0==getItem(position).hint){
            hint.setBackgroundResource(R.mipmap.check_no);
            imageView.setCanDraw(false);
            if ( getItem(position).restore){
                imageView.restore();
            }
            ((MainActivity)context).setInterceptTouchEvent(false);
        }else if(1==getItem(position).hint){
            hint.setBackgroundResource(R.mipmap.check_ok);
            imageView.setCanDraw(true);
        }else {
            hint.setBackgroundResource(R.mipmap.icon_delete);
        }
        imageView.setDrawInterface(new OnDrawInterface() {
            @Override
            public void hasDraw() {
                //让gridView不要拦截事件
                ((MainActivity)context).setInterceptTouchEvent(true);
                hint.setBackgroundResource(R.mipmap.icon_delete);
                list.get(position).hint=2;
            }
        });
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemData item = list.get(position);
                if (0==item.hint){
                    item.hint=1;
                    imageView.setCanDraw(true);
                }else if(1==item.hint){
                    item.hint=2;

                }else {
                    item.hint=0;
                    getItem(position).restore=true;
                }
                notifyDataSetChanged();
            }
        });
    }
}

package com.example.myapplication.activity;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MainAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.bean.ItemData;
import com.example.myapplication.util.PopupFactory;
import com.example.myapplication.util.ScreenUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    PullToRefreshGridView refreshGridView;
    private RadioGroup radioGroup;
    private boolean isFirst = true;
    private GridView gridView;
    int num = 9;
    private MainAdapter mainAdapter;
    ArrayList arrayList = new ArrayList();
    PopupMenu popupMenuLeft, popupMenuRight;
    public static int paintWidth = 5;
    boolean hint = false;
    public static boolean canDraw = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        View imageLeft = findViewById(R.id.img_left);
        View img_right = findViewById(R.id.img_right);
        popupMenuLeft = PopupFactory.create(this, imageLeft, R.menu.menulist, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nine:
                        num = 9;
                        gridView.setNumColumns(3);
                        break;
                    case R.id.four:
                        num = 4;
                        gridView.setNumColumns(2);
                        break;
                    case R.id.one:
                        num = 1;
                        gridView.setNumColumns(1);
                        break;

                }
                addData(num, true);
                mainAdapter.setNum(num);
                mainAdapter.notifyDataSetChanged();
                return false;
            }
        });
        popupMenuRight = PopupFactory.create(this, img_right, R.menu.menulist_right, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.paint_one:
                        setPaintWidth(1);
                        break;
                    case R.id.paint_two:
                        setPaintWidth(2);
                        break;
                    case R.id.paint_five:
                        setPaintWidth(5);
                        break;
                    case R.id.paint_eight:
                        setPaintWidth(8);
                        break;
                    case R.id.paint_ten:
                        setPaintWidth(10);
                        break;

                }
                return false;
            }
        });
        imageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenuLeft.show();
            }
        });
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenuRight.show();
            }
        });
        refreshGridView = (PullToRefreshGridView) findViewById(R.id.gridView);
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                addData(num, false);
                mainAdapter.notifyDataSetChanged();
                refreshGridView.onRefreshComplete();
            }
        });
        gridView = refreshGridView.getRefreshableView();
        gridView.setNumColumns(3);
        addData(num, false);
        final CheckBox cbHint = (CheckBox) findViewById(R.id.cbHint);
        cbHint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //可以编辑

                } else {
                    //不可以编辑
                }
                canDraw=isChecked;
            }
        });
    }

    private void addData(int num, boolean cleanData) {
        if (cleanData) {
            arrayList.clear();
        }
        for (int i = 0; i < num; i++) {
            arrayList.add(new ItemData());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            mainAdapter = new MainAdapter(this, arrayList, gridView.getHeight(), num);
            gridView.setAdapter(mainAdapter);
            setPaintWidth(5);
            isFirst = false;
        }
    }

    public void setInterceptTouchEvent(boolean disallowIntercept) {
        refreshGridView.requestDisallowInterceptTouchEvent(disallowIntercept);
        gridView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public void setPaintWidth(int paintWidth) {
        MainActivity.paintWidth = paintWidth;
    }
}
package com.example.myapplication.activity;

import android.widget.GridView;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MainAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.bean.ItemData;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        refreshGridView = (PullToRefreshGridView) findViewById(R.id.gridView);
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView = refreshGridView.getRefreshableView();
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.nineRb:
                        num = 9;
                        gridView.setNumColumns(3);
                        break;
                    case R.id.fourRb:
                        num = 4;
                        gridView.setNumColumns(2);
                        break;
                    case R.id.oneRb:
                        num = 1;
                        gridView.setNumColumns(1);
                        break;

                }
                addData(num);
                mainAdapter.setNum(num);
                mainAdapter.notifyDataSetChanged();
            }
        });
        gridView.setNumColumns(3);
        addData(num);
    }

    private void addData(int num) {
        arrayList.clear();
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
            isFirst = false;
        }
    }

    public void setInterceptTouchEvent(boolean disallowIntercept) {
        refreshGridView.requestDisallowInterceptTouchEvent(disallowIntercept);
        gridView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
/**
 *
 */
package com.example.myapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * class name: BaseAdapter
 * class description: BaseAdapter二次封装
 * author: zp
 * create time: 2016-4-5 上午10:14:23
 * modify by:
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> list;
    protected Context context;

    public BaseAdapter(Context context, List<T> list) {
        this.context = context;
        if (!Validator.isEmpty(list)) {
            this.list = list;
            return;
        }
        this.list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list.clear();
        addAll(list);
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void add(T t) {
        if (!Validator.isNull(t)) {
            this.list.add(t);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> list) {
        if (!Validator.isEmpty(list) && !this.list.containsAll(list)) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (!Validator.isNull(convertView)) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(getLayoutId(), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        onViewCreated(holder, position);
        return convertView;
    }

    protected abstract int getLayoutId();

    protected abstract void onViewCreated(ViewHolder holder, int position);
}

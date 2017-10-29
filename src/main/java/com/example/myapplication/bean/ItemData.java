package com.example.myapplication.bean;

/**
 * Created by cqian on 2017/10/28.
 */

public class ItemData {
    /**
     * 0表示初始化状态
     * 1表示可清空状态，此状态点击后可初始化画布
     */
    public int hint;
    public boolean init;
    /**
     * 是否需要初始化画板
     */
    public boolean restore;
}

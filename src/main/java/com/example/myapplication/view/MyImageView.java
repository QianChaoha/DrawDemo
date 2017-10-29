package com.example.myapplication.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.interfaces.OnDrawInterface;

public class MyImageView extends View {
    private OnDrawInterface drawInterface;
    private Bitmap cacheBitmap;// 画纸
    private Canvas cacheCanvas;// 创建画布、画家
    private Path path;// 绘图的路径
    public Paint paint;// 画笔
    private float preX, preY;// 之前的XY的位置，用于下面的手势移动
    private int view_width, view_height;// 屏幕的高度与宽度
    boolean isInit = false;
    private int paintWidth;

    public OnDrawInterface getDrawInterface() {
        return drawInterface;
    }

    public void setDrawInterface(OnDrawInterface drawInterface) {
        this.drawInterface = drawInterface;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }



    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void init(int imageViewWidth, int imageViewHeight) {
        if (imageViewHeight>0&&imageViewWidth>0){
            path = new Path();
            paint = new Paint();
            cacheCanvas = new Canvas();
            // 获取屏幕的高度与宽度
            view_width = imageViewWidth;
            view_height = imageViewHeight;
            cacheBitmap = Bitmap.createBitmap(view_width, view_height,
                    Config.ARGB_8888);// 建立图像缓冲区用来保存图像
            cacheCanvas.setBitmap(cacheBitmap);
            paint.setColor(Color.RED);// 设置画笔的默认颜色
            paint.setStyle(Paint.Style.STROKE);// 设置画笔的填充方式为无填充、仅仅是画线
            paint.setStrokeWidth(5);// 设置画笔的宽度为5
            cacheCanvas.save();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (MainActivity.canDraw && canvas != null) {
            canvas.drawBitmap(cacheBitmap, 0, 0, paint);// 把cacheBitmap画到DrawView上
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MainActivity.canDraw) {
            // 获取触摸位置
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {// 获取触摸的各个瞬间
                case MotionEvent.ACTION_DOWN:// 手势按下
                    path.moveTo(x, y);// 绘图的起始点
                    preX = x;
                    preY = y;
                    paint.setStrokeWidth(MainActivity.paintWidth);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawInterface.hasDraw();

                    float dx = Math.abs(x - preX);
                    float dy = Math.abs(y - preY);
                    if (dx > 5 || dy > 5) {// 用户要移动超过5像素才算是画图，免得手滑、手抖现象
                        path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                        preX = x;
                        preY = y;
                        cacheCanvas.drawPath(path, paint);// 绘制路径
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    path.reset();
                    break;
            }
            invalidate();
        }
        return true;
    }

    public void saveBitmap() throws Exception {

        String sdpath = Environment.getExternalStorageDirectory()
                .getAbsolutePath();// 获取sdcard的根路径
        String filename = new SimpleDateFormat("yyyyMMddhhmmss",
                Locale.getDefault())
                .format(new Date(System.currentTimeMillis()));// 产生时间戳，称为文件名
        File file = new File(sdpath + File.separator + filename + ".png");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// 以100%的品质创建png
        fileOutputStream.flush();
        fileOutputStream.close();
        Toast.makeText(getContext(),
                "图像已保存到" + sdpath + File.separator + filename + ".png",
                Toast.LENGTH_SHORT).show();

    }

    public void restore() {
       init(view_width,view_height);
    }

}

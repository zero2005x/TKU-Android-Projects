package com.liangtinglin.n913410014_w14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

public class gem extends View {
    private ShapeDrawable[] sh;

    public gem(Context context) {
        super(context);
        init(); // 務必在建構子初始化
    }

    public gem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public gem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 將初始化邏輯獨立出來，確保 sh 陣列被建立
    private void init() {
        sh = new ShapeDrawable[4]; // 這裡不要加 ShapeDrawable[]，直接指派給全域變數
        sh[0] = new ShapeDrawable(new OvalShape());
        sh[1] = new ShapeDrawable(new OvalShape());
        sh[2] = new ShapeDrawable(new RectShape());
        sh[3] = new ShapeDrawable(new RectShape());

        // 設定初始座標
        sh[0].setBounds(10, 10, 100, 150);
        sh[1].setBounds(120, 10, 260, 100);
        sh[2].setBounds(10, 170, 100, 310);
        sh[3].setBounds(120, 170, 260, 260);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 加入防錯機制，確保 sh 不是 null
        if (sh == null) return;

        for (int i = 0; i < sh.length; i++) {
            int r = (int)(Math.random() * 256);
            int g = (int)(Math.random() * 256);
            int b = (int)(Math.random() * 256);
            sh[i].getPaint().setColor(android.graphics.Color.rgb(r, g, b));
            sh[i].draw(canvas);
        }

        // 如果你想要它一直閃爍變色，可以加上這行（選配）
        // invalidate();
    }
}
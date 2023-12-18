package cn.rubintry.nbui.drag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import cn.rubintry.nbui.core.INBUIInterface;
import cn.rubintry.nbui.core.NBUI;
import cn.rubintry.nbui.core.utils.BarUtils;
import cn.rubintry.nbui.core.config.NBFloatViewConfig;

public class NBFloatView extends View implements INBUIInterface {

    private Paint mPaint = null;

    /**
     * 宽度
     */
    int mWidth = -1;

    /**
     * 高度
     */
    int mHeight = -1;

    /**
     * 起始位置
     */
    Point position = null;

    private PositionHandler positionHandler = new PositionHandler();


    /**
     * 视图区域，用于判断是否在视图区域内
     */
    private Region viewRegion = new Region();


    /**
     * 点击事件监听器
     */
    private cn.rubintry.nbui.drag.OnClickListener mOnClickListener = null;



    private OnDrawListener onDrawListener = null;

    /**
     * 记录按下的那一瞬间，用于处理点击事件
     */
    private long downTime = 0L;


    public NBFloatView(Context context) {
        super(context);
        preInit(context , null);
        init();
    }

    public NBFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit(context , attrs);
        init();
    }

    public NBFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit(context , attrs);
        init();
    }

    @Override
    public void preInit(@Nullable Context context, @Nullable AttributeSet attrs) {
        assert attrs == null;
    }

    @Override
    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#000000"));
        if(mWidth == -1){
            mWidth = NBUI.getInstance().config(NBFloatViewConfig.class).getWidth();
        }
        if(mHeight == -1){
            mHeight = NBUI.getInstance().config(NBFloatViewConfig.class).getHeight();
        }
        if(position == null){
            position = NBUI.getInstance().config(NBFloatViewConfig.class).getPosition();
        }
    }



    public void  setOnDrawListener(@Nullable OnDrawListener onDrawListener){
        this.onDrawListener = onDrawListener;
    }

    public void  setOnClickListener(cn.rubintry.nbui.drag.OnClickListener listener){
        this.mOnClickListener = listener;
    }

    /**
     * 处理拖拽
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(viewRegion.contains((int)event.getX(), (int)event.getY())){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downTime = System.currentTimeMillis();
                    position.set((int)(event.getX() - mWidth / 2), (int)(event.getY() - mHeight / 2));
                    break;
                case MotionEvent.ACTION_MOVE:
                    position.set((int)(event.getX() - mWidth / 2), (int)(event.getY() - mHeight / 2));
                    break;
                case MotionEvent.ACTION_UP:
                    if(System.currentTimeMillis() - downTime < 100 && mOnClickListener != null){
                        mOnClickListener.click(this);
                    }
                    break;
                    default:
                        break;
            }
            postInvalidate();
            return true;
        }
        return false;
    }

    /**
     * 将悬浮按钮添加至窗口
     */
    public void  addSelfToWindow() {
        ViewGroup mDecorView = (ViewGroup) ((Activity)getContext()).getWindow().getDecorView();
        mDecorView.removeView(this);
        mDecorView.addView(this);
    }

    /**
     * 确保能够不侵入状态栏
     */
    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t + BarUtils.getStatusBarHeight(), r,b);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null ==canvas){
            return;
        }
        RectF rectF = positionHandler.getRectF(this);
        if(onDrawListener != null){
            onDrawListener.onDraw(rectF , canvas , this , mPaint);
            viewRegion.set(new Rect((int)rectF.left , (int)rectF.top , (int)rectF.right , (int)rectF.bottom));
            return;
        }
        canvas.drawRect(rectF , mPaint);
        viewRegion.set(new Rect((int)rectF.left , (int)rectF.top , (int)rectF.right , (int)rectF.bottom));
    }
}

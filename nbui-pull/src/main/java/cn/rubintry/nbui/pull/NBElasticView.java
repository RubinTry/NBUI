package cn.rubintry.nbui.pull;

import static androidx.core.view.ViewCompat.TYPE_NON_TOUCH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.ViewCompat;

import cn.rubintry.nbui.core.INBUIInterface;
import cn.rubintry.nbui.core.NBUI;
import cn.rubintry.nbui.core.utils.ScreenUtils;
import cn.rubintry.nbui.core.config.NBElasticPullConfig;


/**
 * Created by RubinTry on 2023/12/08.
 * NB的仿抖音“我的”页面的下拉放大效果，特别NB，自带惯性越界回弹
 * 回弹时，速度越快，回弹越猛
 */
public class NBElasticView extends FrameLayout implements INBUIInterface , NestedScrollingParent3, NestedScrollingParent2,
        NestedScrollingChild3, NestedScrollingChild2, NestedScrollingParent,
        NestedScrollingChild {

    private ElasticPull elasticPull = null;
    private View headerView = null;
    private int mHeaderWidth = 0;
    private int mHeaderHeight = 0;
    private boolean mHeaderSizeReady = false;
    private OnReadyPullListener onReadyPullListener = null;
    private float mInitialX = 0f;
    private float mInitialY = 0f;
    private boolean mIsBeingDragged = false;

    private int[] mNestedScrollingV2ConsumedCompat = new int[2];

    private int DEFAULT_MAX_PULL_HEIGHT  = ScreenUtils.getScreenHeight(getContext()) / 2;

    /**
     * 下拉的最大高度
     */
    int maxPullHeight  = DEFAULT_MAX_PULL_HEIGHT;



    /**
     * 下拉放大阻力系数
     */
    Double elasticCoefficient = -1.0;

    /**
     * 最后一次偏移时产生的偏移量
     */
    private int lastOffset = -1;

    private NestedScrollingChildHelper mNestedScrollingChildHelper = null;


    private boolean isReady = false;

    private Boolean isHeaderReady = false;

    public NBElasticView(@NonNull Context context) {
        super(context);
        preInit(context , null);
        init();
    }

    public NBElasticView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit(context , attrs);
        init();
    }

    public NBElasticView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit(context , attrs);
        init();
    }

    @Override
    public void preInit(@Nullable Context context, @Nullable AttributeSet attrs) {
        if(context == null){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NBElasticView);
        elasticCoefficient = (double) typedArray.getFloat(R.styleable.NBElasticView_elasticCoefficient , -1.0f);
        if(elasticCoefficient == -1.0){
            elasticCoefficient = NBUI.getInstance().config(NBElasticPullConfig.class).getElasticCoefficient();
        }
        maxPullHeight = typedArray.getDimensionPixelOffset(R.styleable.NBElasticView_maxPullHeight , 0);
        if(maxPullHeight == 0){
            maxPullHeight = DEFAULT_MAX_PULL_HEIGHT;
        }
        typedArray.recycle();
    }

    @Override
    public void init() {
        mHeaderSizeReady = false;
        //去除边界阴影
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        elasticPull = ElasticPullFactory.create(elasticCoefficient);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
    }


    public boolean isReady(){
        isReady = onReadyPullListener != null && onReadyPullListener.isReady();
        return isReady;
    }


    public boolean isHeaderReady() {
        isHeaderReady = headerView != null && mHeaderSizeReady;
        return isHeaderReady;
    }


    /**
     * @param onReadyPullListener 触发下拉放大的监听器
     */
    public void setOnReadyPullListener(OnReadyPullListener onReadyPullListener) {
        this.onReadyPullListener = onReadyPullListener;
    }


    /**
     * 需要被放大的头部view
     */
    public NBElasticView setHeader(View header) {
        headerView = header;
        header.post(new Runnable() {
            @Override
            public void run() {
                if(headerView != null){
                    mHeaderHeight = headerView.getHeight();
                    mHeaderWidth = headerView.getWidth();
                }
                mHeaderSizeReady = true;
            }
        });
        return this;
    }


    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isHeaderReady() && isReady()) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    mInitialX = ev.getX();
                    mInitialY = ev.getY();
                    mIsBeingDragged = false;
                break;

                case MotionEvent.ACTION_MOVE :
                    float diffY = ev.getY() - mInitialY;
                    float diffX = ev.getX() - mInitialX;
                    if (diffY > 0) {
                        return true;
                    }
                break;
                    default:
                        break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isHeaderReady() && isReady()) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE :
                    float diffY = ev.getY() - mInitialY;
                    changeHeader((int)diffY);
                break;

                case MotionEvent.ACTION_CANCEL :
                case MotionEvent.ACTION_UP:
                    resetHeader();
                    return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void resetHeader() {
        elasticPull.resetAnimator(headerView, mHeaderHeight, mHeaderWidth);
    }

    private void changeHeader(int offsetY) {
        elasticPull.pullAnimator(
                headerView,
                mHeaderHeight,
                mHeaderWidth,
                offsetY,
                maxPullHeight
        );
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        //划重点，需要加type == TYPE_NON_TOUCH判断，才能准确的在滑动结束时执行相应逻辑
        if(type == TYPE_NON_TOUCH){
            //执行头部伸缩动画
            if(isReady() && isHeaderReady()){
                elasticPull.pullByAnimator(headerView ,
                        mHeaderHeight,
                        mHeaderWidth,
                        3 * Math.abs(lastOffset),
                        maxPullHeight);
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        this.lastOffset = dyConsumed;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        onNestedScroll(
                target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type,
                mNestedScrollingV2ConsumedCompat
        );
    }


    /**
     * 此处会在滑动时被调用2次，一次是ACTION_UP和ACTION_CANCEL调用一次，此时type为TYPE_TOUCH,一次是fling完毕，此时type为TYPE_NON_TOUCH。
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        elasticPull = null;
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll(int type) {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public void onStopNestedScroll(View child) {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return type == ViewCompat.TYPE_TOUCH && mNestedScrollingChildHelper.dispatchNestedScroll(
                dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type
        );
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return type == ViewCompat.TYPE_TOUCH && dispatchNestedPreScroll(
                dx, dy, consumed,
                offsetInWindow
        );
    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        if (type == ViewCompat.TYPE_TOUCH) {
            mNestedScrollingChildHelper.dispatchNestedScroll(
                    dxConsumed, dyConsumed, dxUnconsumed,
                    dyUnconsumed, offsetInWindow, type, consumed
            );
        }
    }
}

package cn.rubintry.nbui.pull

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat.TYPE_NON_TOUCH
import cn.rubintry.nbui.core.INBUIInterface
import kotlin.math.abs


/**
 * @author rubintry
 * 带有下拉放大特性的组件，特别NB，支持嵌套滑动（需要配合NestedScrollView使用）、惯性回弹
 */
class NBElasticView : FrameLayout , INBUIInterface , NestedScrollingParent3{
    private var headerView: View? = null
    private var mHeaderWidth = 0
    private var mHeaderHeight = 0
    private var mHeaderSizeReady = false
    private var onReadyPullListener: OnReadyPullListener? = null
    private var mInitialX = 0f
    private var mInitialY = 0f
    private var mIsBeingDragged = false

    /**
     * 最大头部下拉高度
     */
    private val mMaxPullHeight: Int = ScreenUtils.getScreenHeight(context) / 3


    /**
     * 最后一次偏移时产生的偏移量
     */
    private var lastOffset: Int = 0

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
        //去除边界阴影
        isHorizontalFadingEdgeEnabled = false
        isVerticalFadingEdgeEnabled = false
        overScrollMode = OVER_SCROLL_NEVER
    }

    fun setOnReadyPullListener(onReadyPullListener: OnReadyPullListener?) {
        this.onReadyPullListener = onReadyPullListener
    }

    override fun init() {
        mHeaderSizeReady = false
    }



    fun setHeader(header: View?): NBElasticView {
        headerView = header
        header?.post {
            mHeaderHeight = headerView!!.height
            mHeaderWidth = headerView!!.width
            mHeaderSizeReady = true
        }
        return this
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isHeaderReady && isReady) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    //                    log("onInterceptTouchEvent DOWN");
                    mInitialX = ev.x
                    mInitialY = ev.y
                    mIsBeingDragged = false
                }

                MotionEvent.ACTION_MOVE -> {
                    val diffY = ev.y - mInitialY
                    val diffX = ev.x - mInitialX
                    if (diffY > 0 && diffY / Math.abs(diffX) > 2) {
                        mIsBeingDragged = true
                        return true
                    }
                }

                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {}
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isHeaderReady && isReady) {
            when (ev.action) {
                MotionEvent.ACTION_MOVE -> if (mIsBeingDragged) {
                    val diffY = ev.y - mInitialY
                    changeHeader(diffY.toInt())
                }

                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> if (mIsBeingDragged) {
                    resetHeader()
                    return true
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun resetHeader() {
        PullAnimatorUtil.resetAnimator(headerView, mHeaderHeight, mHeaderWidth)
    }

    private fun changeHeader(offsetY: Int) {
        PullAnimatorUtil.pullAnimator(
            headerView,
            mHeaderHeight,
            mHeaderWidth,
            offsetY,
            mMaxPullHeight
        )
    }



    private val isReady: Boolean
        get() = onReadyPullListener != null && onReadyPullListener?.isReady() == true
    private val isHeaderReady: Boolean
        get() = headerView != null && mHeaderSizeReady

    
    companion object{
        @JvmField
        val TAG = "NBElasticView"
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }


    /**
     * 此处会在滑动时被调用2次，一次是ACTION_UP和ACTION_CANCEL调用一次，此时type为TYPE_TOUCH,一次是fling完毕，此时type为TYPE_NON_TOUCH。
     */
    override fun onStopNestedScroll(target: View, type: Int) {
        //划重点，需要加type == TYPE_NON_TOUCH判断，才能准确的在滑动结束时执行相应逻辑
        if(type == TYPE_NON_TOUCH){
            //执行头部伸缩动画
            if(isReady && isHeaderReady){
                PullAnimatorUtil.pullByAnimator(headerView ,
                    mHeaderHeight,
                    mHeaderWidth,
                    3 * abs(lastOffset),
                    mMaxPullHeight)
            }
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        this.lastOffset = dyConsumed
    }



    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

    }


}

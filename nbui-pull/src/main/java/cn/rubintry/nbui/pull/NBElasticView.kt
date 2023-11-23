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
 * NB的仿抖音“我的”页面的下拉放大效果，特别NB，自带惯性越界回弹
 * 回弹时，速度越快，回弹越猛
 */
class NBElasticView : FrameLayout , INBUIInterface , NestedScrollingParent3{
    private var elasticPull: ElasticPull ?= null
    private var headerView: View? = null
    private var mHeaderWidth = 0
    private var mHeaderHeight = 0
    private var mHeaderSizeReady = false
    private var onReadyPullListener: OnReadyPullListener? = null
    private var mInitialX = 0f
    private var mInitialY = 0f
    private var mIsBeingDragged = false

    private val DEFAULT_MAX_PULL_HEIGHT : Int = ScreenUtils.getScreenHeight(context) / 2

    /**
     * 下拉的最大高度
     */
    var maxPullHeight: Int = DEFAULT_MAX_PULL_HEIGHT

    private val DEFAULT_ELASTIC_COEFFCIENT : Double = 0.8

    /**
     * 下拉放大阻力系数
     */
    var elasticCoefficient: Double = DEFAULT_ELASTIC_COEFFCIENT

    /**
     * 最后一次偏移时产生的偏移量
     */
    private var lastOffset: Int = -1

    constructor(context: Context?) : super(context!!){
        preInit(context , null)
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        preInit(context , attrs)
        init()
    }

    @SuppressLint("Recycle")
    override fun preInit(context: Context?, attrs: AttributeSet?) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.NBElasticView)
        elasticCoefficient = typedArray?.getFloat(R.styleable.NBElasticView_elasticCoefficient , -1f)?.toDouble() ?: (-1).toDouble()
        if(elasticCoefficient == (-1).toDouble()){
            elasticCoefficient = DEFAULT_ELASTIC_COEFFCIENT
        }
        maxPullHeight = typedArray?.getDimensionPixelOffset(R.styleable.NBElasticView_maxPullHeight , 0) ?: 0
        if(maxPullHeight == 0){
            maxPullHeight = DEFAULT_MAX_PULL_HEIGHT
        }
        typedArray?.recycle()
    }


    override fun init() {
        mHeaderSizeReady = false
        //去除边界阴影
        isHorizontalFadingEdgeEnabled = false
        isVerticalFadingEdgeEnabled = false
        overScrollMode = OVER_SCROLL_NEVER
        elasticPull = ElasticPullFactory.create(elasticCoefficient)
    }
    /**
     * @param onReadyPullListener 触发下拉放大的监听器
     */
    fun setOnReadyPullListener(onReadyPullListener: OnReadyPullListener?) {
        this.onReadyPullListener = onReadyPullListener
    }


    /**
     * 需要被放大的头部view
     */
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
        elasticPull?.resetAnimator(headerView, mHeaderHeight, mHeaderWidth)
    }

    private fun changeHeader(offsetY: Int) {
        elasticPull?.pullAnimator(
            headerView,
            mHeaderHeight,
            mHeaderWidth,
            offsetY,
            maxPullHeight
        )
    }



    private val isReady: Boolean
        get() = onReadyPullListener != null && onReadyPullListener?.isReady() == true
    private val isHeaderReady: Boolean
        get() = headerView != null && mHeaderSizeReady



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
                elasticPull?.pullByAnimator(headerView ,
                    mHeaderHeight,
                    mHeaderWidth,
                    3 * abs(lastOffset),
                    maxPullHeight)
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


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        elasticPull = null
    }

}

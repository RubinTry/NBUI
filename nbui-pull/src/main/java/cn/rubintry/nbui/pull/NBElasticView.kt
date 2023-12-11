package cn.rubintry.nbui.pull

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.TYPE_NON_TOUCH
import cn.rubintry.nbui.core.INBUIInterface
import cn.rubintry.nbui.core.NBUI
import cn.rubintry.nbui.pull.config.NBElasticPullConfig
import kotlin.math.abs



/**
 * Created by RubinTry on 2023/12/08.
 * NB的仿抖音“我的”页面的下拉放大效果，特别NB，自带惯性越界回弹
 * 回弹时，速度越快，回弹越猛
 */
class NBElasticView : FrameLayout , INBUIInterface , NestedScrollingParent3, NestedScrollingParent2,
    NestedScrollingChild3, NestedScrollingChild2, NestedScrollingParent,
    NestedScrollingChild{
    private var elasticPull: ElasticPull ?= null
    private var headerView: View? = null
    private var mHeaderWidth = 0
    private var mHeaderHeight = 0
    private var mHeaderSizeReady = false
    private var onReadyPullListener: OnReadyPullListener? = null
    private var mInitialX = 0f
    private var mInitialY = 0f
    private var mIsBeingDragged = false

    private var mNestedScrollingV2ConsumedCompat = IntArray(2)

    private val DEFAULT_MAX_PULL_HEIGHT : Int = ScreenUtils.getScreenHeight(context) / 2

    /**
     * 下拉的最大高度
     */
    var maxPullHeight: Int = DEFAULT_MAX_PULL_HEIGHT

    companion object{
        @JvmField
        val DEFAULT_ELASTIC_COEFFCIENT : Double = 0.95
    }

    /**
     * 下拉放大阻力系数
     */
    var elasticCoefficient: Double = (-1).toDouble()

    /**
     * 最后一次偏移时产生的偏移量
     */
    private var lastOffset: Int = -1

    private var mNestedScrollingChildHelper: NestedScrollingChildHelper? = null

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
            elasticCoefficient = NBUI.getInstance().config(NBElasticPullConfig::class.java).getElasticCoefficient()
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
        mNestedScrollingChildHelper = NestedScrollingChildHelper(this)


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

    override fun requestDisallowInterceptTouchEvent(b: Boolean) {
        parent?.requestDisallowInterceptTouchEvent(b)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isHeaderReady && isReady) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    mInitialX = ev.x
                    mInitialY = ev.y
                    mIsBeingDragged = false
                }

                MotionEvent.ACTION_MOVE -> {
                    val diffY = ev.y - mInitialY
                    val diffX = ev.x - mInitialX
                    if (diffY > 0) {
//                        mIsBeingDragged = true
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
                MotionEvent.ACTION_MOVE -> {
                    val diffY = ev.y - mInitialY
                    changeHeader(diffY.toInt())
                }

                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
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
        onNestedScroll(
            target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type,
            mNestedScrollingV2ConsumedCompat
        )
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        elasticPull = null
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mNestedScrollingChildHelper?.startNestedScroll(axes) == true
    }

    override fun stopNestedScroll(type: Int) {
        mNestedScrollingChildHelper?.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mNestedScrollingChildHelper?.hasNestedScrollingParent() == true
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return type == ViewCompat.TYPE_TOUCH && mNestedScrollingChildHelper?.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type
        ) == true
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return type == ViewCompat.TYPE_TOUCH && dispatchNestedPreScroll(
            dx, dy, consumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        if (type == ViewCompat.TYPE_TOUCH) {
            mNestedScrollingChildHelper?.dispatchNestedScroll(
                dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed, offsetInWindow, type, consumed
            )
        }
    }

}

package cn.rubintry.nbui.drag

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toRect
import cn.rubintry.nbui.core.INBUIInterface
import cn.rubintry.nbui.core.NBUI
import cn.rubintry.nbui.drag.config.NBFloatViewConfig
import com.blankj.utilcode.util.BarUtils


/**
 * Created by RubinTry on 2023/11/24.
 * 一个悬浮在屏幕上的可拖动视图
 */
class NBFloatView : View , INBUIInterface{


    private var mPaint : Paint ?= null

    /**
     * 宽度
     */
    var mWidth = -1

    /**
     * 高度
     */
    var mHeight = -1

    /**
     * 起始位置
     */
    var position : Point ?= null

    private val positionHandler = PositionHandler()


    /**
     * 视图区域，用于判断是否在视图区域内
     */
    private val viewRegion = Region()


    /**
     * 点击事件监听器
     */
    private var mOnClickListener : cn.rubintry.nbui.drag.OnClickListener ?= null



    private var onDrawListener : OnDrawListener ?= null

    /**
     * 记录按下的那一瞬间，用于处理点击事件
     */
    private var downTime = 0L

    constructor(context: Context?) : super(context){
        preInit(context , null)
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        preInit(context , attrs)
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        preInit(context , attrs)
        init()
    }

    override fun preInit(context: Context?, attrs: AttributeSet?) {
        assert(attrs == null){"不允许在xml中声明此view"}
    }

    override fun init() {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.color = Color.parseColor("#000000")
        if(mWidth == -1){
            mWidth = NBUI.getInstance().config(NBFloatViewConfig::class.java).getWidth()
        }
        if(mHeight == -1){
            mHeight = NBUI.getInstance().config(NBFloatViewConfig::class.java).getHeight()
        }
        if(position == null){
            position = NBUI.getInstance().config(NBFloatViewConfig::class.java).getPosition()
        }
    }


    fun setOnDrawListener(onDrawListener: OnDrawListener){
        this.onDrawListener = onDrawListener
    }

    fun setOnClickListener(listener: cn.rubintry.nbui.drag.OnClickListener){
        this.mOnClickListener = listener
    }

    /**
     * 处理拖拽
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(viewRegion.contains(event.x.toInt(), event.y.toInt())){
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
                    position?.set((event.x - mWidth / 2).toInt(), (event.y - mHeight / 2).toInt())
                }
                MotionEvent.ACTION_MOVE -> {
                    position?.set((event.x - mWidth / 2).toInt(), (event.y - mHeight / 2).toInt())
                }
                MotionEvent.ACTION_UP -> {
                    if(System.currentTimeMillis() - downTime < 100){
                        mOnClickListener?.click(this)
                    }
                }
            }
            postInvalidate()
            return true
        }
        return false
    }


    /**
     * 将悬浮按钮添加至窗口
     */
    fun addSelfToWindow() {
        val mDecorView = ((context as Activity).window.decorView as? ViewGroup)
        mDecorView?.removeView(this)
        mDecorView?.addView(this)
    }


    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t + BarUtils.getStatusBarHeight(), r,b)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rectF = positionHandler.getRectF(this)
        if(onDrawListener != null){
            onDrawListener?.onDraw(rectF , canvas , this , mPaint)
            viewRegion.set(rectF.toRect())
            return
        }
        canvas?.drawRect(rectF , mPaint!!)
        viewRegion.set(rectF.toRect())
    }
}
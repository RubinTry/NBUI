package cn.rubintry.nbui.normal

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import cn.rubintry.nbui.core.INBUIInterface


/**
 * Created by RubinTry on 2023/11/24.
 * NB的ImageView
 */
class NBImageView : View , INBUIInterface {

    /**
     * 图片资源
     */
    private var mBitmap : Bitmap ?= null

    /**
     * 边框宽度
     */
    private var mStrokeWidth = -1

    /**
     * 边框颜色
     */
    private var mStrokeColor = -1

    /**
     * 圆角
     */
    private var mCornerRadius = -1

    private var mPaint : Paint ?= null

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

    @SuppressLint("Recycle")
    override fun preInit(context: Context?, attrs: AttributeSet?) {
        val typedArray = context?.obtainStyledAttributes(attrs , R.styleable.NBImageView)
        val resId = typedArray?.getResourceId(R.styleable.NBImageView_android_src , NO_ID) ?: NO_ID
        mBitmap = BitmapFactory.decodeResource(resources , resId)
        mStrokeWidth = typedArray?.getDimensionPixelSize(R.styleable.NBImageView_strokeWidth, 0)?: 0
        mStrokeColor = typedArray?.getColor(R.styleable.NBImageView_strokeColor , Color.TRANSPARENT) ?: Color.TRANSPARENT
        mCornerRadius = typedArray?.getDimensionPixelSize(R.styleable.NBImageView_cornerRadius , 0) ?: 0
        typedArray?.recycle()
    }

    override fun init() {
        mPaint = Paint()
        if(mStrokeColor != -1){
            mPaint?.color = mStrokeColor
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        if(mStrokeWidth != -1){
            rectF = RectF(mStrokeWidth.toFloat() , mStrokeWidth.toFloat() , width.toFloat() - mStrokeWidth , height.toFloat() - mStrokeWidth)
        }
//        if(mStrokeColor != -1){
////            canvas?.drawArc(rectF , 90f , 90f , true ,  mPaint!!)
//
//        }
        mBitmap?.let {
            canvas?.drawBitmap(it , null , rectF , mPaint)
        }
    }
}
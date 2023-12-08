package cn.rubintry.nbui

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import cn.rubintry.nbui.drag.NBFloatView
import cn.rubintry.nbui.drag.OnClickListener
import cn.rubintry.nbui.drag.OnDrawListener

class FloatViewActivity : ComponentActivity() {
    private var nbFloatView : NBFloatView ?= null
    private var tvTest: TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_float_view)
        tvTest = findViewById(R.id.tvTest)
        nbFloatView = NBFloatView(this)
        nbFloatView?.addSelfToWindow()
//        nbFloatView?.mWidth = 150
//        nbFloatView?.mHeight = 150
//        nbFloatView?.position = Point(0 , 0)
        nbFloatView?.setOnClickListener(object : OnClickListener{
            override fun click(view: NBFloatView) {
                Toast.makeText(this@FloatViewActivity, "点击了悬浮按钮", Toast.LENGTH_SHORT).show()
            }

        })

        //用canvas绘制一个圆
//        nbFloatView?.setMode(object : OnDrawListener{
//            override fun onDraw(rectF: RectF, canvas: Canvas? , view: NBFloatView , paint: Paint?) {
//                val cx = rectF.left + rectF.width() / 2
//                val cy = rectF.top + rectF.height() / 2
//                val radius = min(rectF.width() , rectF.height()) / 2
//                canvas?.drawCircle(cx , cy , radius , paint!!)
//            }
//        })
        //canvas绘制图片
        nbFloatView?.setMode(object : OnDrawListener{
            override fun onDraw(rectF: RectF, canvas: Canvas?, view: NBFloatView, paint: Paint?) {
                val bitmap = BitmapFactory.decodeResource(resources , R.mipmap.favicon)
                canvas?.drawBitmap(bitmap , null , rectF , paint)
            }
        })

        tvTest?.setOnClickListener {
            Toast.makeText(this@FloatViewActivity, "activity中按钮正常", Toast.LENGTH_SHORT).show()
        }
    }
}
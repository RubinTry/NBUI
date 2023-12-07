package cn.rubintry.nbui.drag

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

interface OnDrawListener {
    /**
     * 自定义view时，绘制的回调方法
     * @param rectF 绘制的区域
     * @param canvas 画布
     */
    fun onDraw(rectF: RectF , canvas: Canvas? , view: NBFloatView , paint: Paint?)
}
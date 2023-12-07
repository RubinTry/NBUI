package cn.rubintry.nbui.drag

import android.graphics.RectF


internal class PositionHandler {

    fun getRectF(view: NBFloatView): RectF {
        return RectF(view.point.x.toFloat() , view.point.y.toFloat() , view.point.x.toFloat() + view.mWidth , view.point.y.toFloat() + view.mHeight)
    }
}
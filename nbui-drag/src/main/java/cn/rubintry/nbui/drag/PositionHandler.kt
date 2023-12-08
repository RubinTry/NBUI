package cn.rubintry.nbui.drag

import android.graphics.RectF


internal class PositionHandler {

    fun getRectF(view: NBFloatView): RectF {
        val position = view.position ?: return RectF()
        return RectF(position.x.toFloat() , position.y.toFloat() , position.x.toFloat() + view.mWidth , position.y.toFloat() + view.mHeight)
    }
}
package cn.rubintry.nbui.pull

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

/**
 * @author rubintry
 */
object ScreenUtils {
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.y
    }

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return -1
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.x
    }
}
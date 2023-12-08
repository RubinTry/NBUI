package cn.rubintry.nbui.drag.config

import android.graphics.Point
import cn.rubintry.nbui.core.INBUIConfig
import cn.rubintry.nbui.core.NBUI


/**
 * [cn.rubintry.nbui.drag.NBFloatView]的全局配置，优先级低于[cn.rubintry.nbui.drag.NBFloatView]的局部配置
 */
class NBFloatViewConfig : INBUIConfig {
    private var width = 150;
    private var height = 150;
    private var position = Point(0, 0);
    private var enableSavePosition = false;

    fun setWidth(width: Int) : NBFloatViewConfig{
        this.width = width
        return this
    }

    fun setHeight(height : Int) : NBFloatViewConfig{
        this.height = height
        return this
    }

    fun setPosition(position : Point) : NBFloatViewConfig{
        this.position = position
        return this
    }

    fun enableSavePosition() : NBFloatViewConfig{
        this.enableSavePosition = true
        return this
    }

    fun getWidth(): Int{
        return width
    }

    fun getHeight() : Int{
        return height
    }

    fun getPosition(): Point{
        return position
    }

    fun isEnableSavePosition() : Boolean{
        return enableSavePosition
    }

    override fun init(): NBUI {
        return NBUI.getInstance()
    }
}
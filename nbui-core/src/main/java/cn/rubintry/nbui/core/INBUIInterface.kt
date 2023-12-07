package cn.rubintry.nbui.core

import android.content.Context
import android.util.AttributeSet

interface INBUIInterface {

    /**
     * 在初始化之前，可以做一些事情，如获取参数，需要在[init]前调用
     * @param context context
     */
    fun preInit(context: Context? , attrs: AttributeSet?)

    /**
     * 初始化
     */
    fun init()


}
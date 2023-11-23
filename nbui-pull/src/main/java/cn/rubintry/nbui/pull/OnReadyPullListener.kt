package cn.rubintry.nbui.pull


/**
 * 监听器，监听是否触发下拉放大
 */
public interface OnReadyPullListener {
    /**
     * 返回一个触发下拉放大的条件
     */
    fun isReady() : Boolean
}
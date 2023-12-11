package cn.rubintry.nbui.pull

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.widget.RelativeLayout
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import kotlin.math.pow

/**
 * @author rubintry
 * @param elasticCoefficient 弹性阻力系数
 */
internal class ElasticPull(private val elasticCoefficient: Double) {

    /**
     * @param headerView
     * @param headerHeight
     * @param offsetY
     */
    fun pullAnimator(
        headerView: View?,
        headerHeight: Int,
        headerWidth: Int,
        offsetY: Int,
        maxHeight: Int
    ) {
        if (headerView == null) {
            return
        }
        val pullOffset = offsetY.toDouble().pow(elasticCoefficient).toInt()
        val newHeight = (maxHeight + headerHeight).coerceAtMost(pullOffset + headerHeight)
        val newWidth = (newHeight.toFloat() / headerHeight * headerWidth).toInt()
        headerView.layoutParams.height = newHeight
        headerView.layoutParams.width = newWidth
        var margin = (newWidth - headerWidth) / 2
        if (headerView.parent != null
            && headerView.parent is RelativeLayout
        ) {
            margin = 0
        }
        headerView.translationX = -margin.toFloat()
        headerView.requestLayout()
    }


    /**
     * 惯性回弹动画
     */
    fun pullByAnimator(
        headerView: View?,
        headerHeight: Int,
        headerWidth: Int,
        offsetY: Int,
        maxHeight: Int
    ) {
        if (headerView == null) {
            return
        }
        val pullOffset = offsetY.toDouble().pow(elasticCoefficient).toInt()
        val newHeight = (maxHeight + headerHeight).coerceAtMost(pullOffset + headerHeight)

        val heightAnimator = ValueAnimator.ofInt(headerHeight , newHeight)
        heightAnimator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Int
            headerView.layoutParams.height = scale
            val newWidth = (scale.toFloat() / headerHeight * headerWidth).toInt()
            headerView.layoutParams.width = newWidth
            var margin = (newWidth - headerWidth) / 2
            if (headerView.parent != null
                && headerView.parent is RelativeLayout
            ) {
                margin = 0
            }
            headerView.translationX = -margin.toFloat()
            headerView.requestLayout()
        }
        val set = AnimatorSet()
        set.interpolator = LinearOutSlowInInterpolator()
        set.duration = 200
        val heightAfterAnimator = ValueAnimator.ofInt(newHeight , headerHeight)
        heightAfterAnimator.addUpdateListener {animation ->
            val scale = animation.animatedValue as Int
            headerView.layoutParams.height = scale
            val newWidth = (scale.toFloat() / headerHeight * headerWidth).toInt()
            headerView.layoutParams.width = newWidth
            var margin = (newWidth - headerWidth) / 2
            if (headerView.parent != null
                && headerView.parent is RelativeLayout
            ) {
                margin = 0
            }
            headerView.translationX = -margin.toFloat()
            headerView.requestLayout()
        }
        set.play(heightAfterAnimator).after(heightAnimator)
        set.start()
    }

    /**
     * 高度重置动画
     *
     * @param headerView
     * @param headerHeight
     */
    fun resetAnimator(headerView: View?, headerHeight: Int, headerWidth: Int) {
        if (headerView == null) {
            return
        }
        val heightAnimator = ValueAnimator.ofInt(headerView.layoutParams.height, headerHeight)
        heightAnimator.addUpdateListener { animation ->
            val height = animation.animatedValue as Int
            headerView.layoutParams.height = height
        }
        val widthAnimator = ValueAnimator.ofInt(headerView.layoutParams.width, headerWidth)
        widthAnimator.addUpdateListener { animation ->
            val width = animation.animatedValue as Int
            headerView.layoutParams.width = width
        }
        val translationAnimator = ValueAnimator.ofInt(headerView.translationX.toInt(), 0)
        translationAnimator.addUpdateListener { animation ->
            val translation = animation.animatedValue as Int
            headerView.translationX = translation.toFloat()
            headerView.requestLayout()
        }
        val set = AnimatorSet()
        set.interpolator = LinearOutSlowInInterpolator()
        set.duration = 200
        set.play(heightAnimator).with(widthAnimator).with(translationAnimator)
        set.start()
    }
}
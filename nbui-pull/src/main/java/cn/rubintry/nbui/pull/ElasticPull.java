package cn.rubintry.nbui.pull;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class ElasticPull {
    private double elasticCoefficient;

    public ElasticPull(double elasticCoefficient) {
        this.elasticCoefficient = elasticCoefficient;
    }


    /**
     * @param headerView
     * @param headerHeight
     * @param offsetY
     */
    public void  pullAnimator(
            View headerView,
            int headerHeight,
            int headerWidth,
            int offsetY,
            int maxHeight
    ) {
        if (headerView == null) {
            return;
        }
        int pullOffset = (int) Math.pow(Double.valueOf(offsetY) , elasticCoefficient);
//        int newHeight = (maxHeight + headerHeight).coerceAtMost(pullOffset + headerHeight)
        int newHeight = Math.min(maxHeight + headerHeight , pullOffset + headerHeight);
        int newWidth = (int) (Float.valueOf(newHeight) / headerHeight * headerWidth);
        headerView.getLayoutParams().height = newHeight;
        headerView.getLayoutParams().width = newWidth;
        int margin = (newWidth - headerWidth) / 2;
        if (headerView.getParent() != null
                && RelativeLayout.class.isAssignableFrom(headerView.getParent().getClass())
        ) {
            margin = 0;
        }
        headerView.setTranslationX(-margin);
        headerView.requestLayout();
    }


    /**
     * 惯性回弹动画
     */
    public void  pullByAnimator(
            View headerView,
            int headerHeight,
            int headerWidth,
            int offsetY,
            int maxHeight
    ) {
        if (headerView == null) {
            return;
        }
        int pullOffset = (int) Math.pow(Double.valueOf(offsetY) , elasticCoefficient);
//        int newHeight = (maxHeight + headerHeight).coerceAtMost(pullOffset + headerHeight)
        int newHeight = Math.min(maxHeight + headerHeight , pullOffset + headerHeight);

        ValueAnimator heightAnimator = ValueAnimator.ofInt(headerHeight , newHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int scale = (int) animation.getAnimatedValue();
                headerView.getLayoutParams().height = scale;
                int newWidth = (int) (Float.valueOf(scale) / headerHeight * headerWidth);
                headerView.getLayoutParams().width = newWidth;
                int margin = (newWidth - headerWidth) / 2;
                if (headerView.getParent() != null
                        && RelativeLayout.class.isAssignableFrom(headerView.getParent().getClass())
            ) {
                    margin = 0;
                }
                headerView.setTranslationX(-margin);
                headerView.requestLayout();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new LinearOutSlowInInterpolator());
        set.setDuration(200);
        ValueAnimator heightAfterAnimator = ValueAnimator.ofInt(newHeight , headerHeight);
        heightAfterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int scale = (int) animation.getAnimatedValue();
                headerView.getLayoutParams().height = scale;
                float newWidth = (Float.valueOf(scale) / headerHeight * headerWidth);
                headerView.getLayoutParams().width = (int) newWidth;
                float margin = (newWidth - headerWidth) / 2;
                if (headerView.getParent() != null
                        && RelativeLayout.class.isAssignableFrom(headerView.getParent().getClass())
            ) {
                    margin = 0;
                }
                headerView.setTranslationX(-margin);
                headerView.requestLayout();
            }
        });
        set.play(heightAfterAnimator).after(heightAnimator);
        set.start();
    }

    /**
     * 高度重置动画
     *
     * @param headerView
     * @param headerHeight
     */
    public void resetAnimator(View headerView, int headerHeight,  int headerWidth) {
        if (headerView == null) {
            return;
        }
        ValueAnimator heightAnimator = ValueAnimator.ofInt(headerView.getLayoutParams().height, headerHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                headerView.getLayoutParams().height = height;
            }
        });
        ValueAnimator widthAnimator = ValueAnimator.ofInt(headerView.getLayoutParams().width, headerWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                headerView.getLayoutParams().width = width;
            }
        });
        ValueAnimator translationAnimator = ValueAnimator.ofInt((int) headerView.getTranslationX(), 0);
        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int translation = (int) animation.getAnimatedValue();
                headerView.setTranslationX(translation);
                headerView.requestLayout();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new LinearOutSlowInInterpolator());
        set.setDuration(200);
        set.play(heightAnimator).with(widthAnimator).with(translationAnimator);
        set.start();
    }
}

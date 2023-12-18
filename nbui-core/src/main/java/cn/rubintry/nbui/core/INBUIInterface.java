package cn.rubintry.nbui.core;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;


/**
 * Created by RubinTry on 2023/12/07.
 * UI库标准接口
 */
public interface INBUIInterface {

    /**
     * 在初始化之前，可以做一些事情，如获取参数，需要在{@link INBUIInterface#init()}前调用
     * @param context context
     */
    void preInit(@Nullable Context context , @Nullable AttributeSet attrs);

    /**
     * 初始化
     */
    void init();
}

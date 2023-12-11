package cn.rubintry.nbui;

import android.app.Application;
import android.graphics.Point;

import cn.rubintry.nbui.drag.config.NBFloatViewConfig;
import cn.rubintry.nbui.core.NBUI;
import cn.rubintry.nbui.pull.config.NBElasticPullConfig;

public class exampleApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //UI全局配置,优先级要低于直接对view设置
        NBUI.getInstance()
                //设置NBFloatView相关配置
                .config(NBFloatViewConfig.class)
                .setWidth(200)
                .setHeight(200)
                .setPosition(new Point(100 , 100))
                .endConfig()
                //结束设置
                //设置NBElasticView相关配置
                .config(NBElasticPullConfig.class)
                .setElasticCoefficient(0.9f)
                .endConfig();//结束设置
    }
}

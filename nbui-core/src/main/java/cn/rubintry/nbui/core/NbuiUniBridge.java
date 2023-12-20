package cn.rubintry.nbui.core;


import android.graphics.Point;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rubintry.nbui.core.config.NBElasticPullConfig;
import cn.rubintry.nbui.core.config.NBFloatViewConfig;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class NbuiUniBridge extends UniModule {


    private final NBUI mNBUI;

    public NbuiUniBridge() {
        mNBUI = NBUI.getInstance();
    }


    @UniJSMethod
    public void config(JSONObject options , UniJSCallback callback) {
        try {
            String configName = options.getString("configName");
            switch (configName){
                case "NBFloatViewConfig":
                    int width = options.getInteger("width");
                    int height = options.getInteger("height");
                    JSONObject position = options.getJSONObject("position");
                    int positionX = position.getInteger("x");
                    int positionY = position.getInteger("y");
                    mNBUI.config(NBFloatViewConfig.class)
                            .setWidth(width)
                            .setHeight(height)
                            .setPosition(new Point(positionX , positionY))
                            .endConfig();
                    break;
                case "NBElasticPullConfig":
                    double elasticCoefficient = options.getDouble("elasticCoefficient");
                    mNBUI.config(NBElasticPullConfig.class)
                            .setElasticCoefficient(elasticCoefficient)
                            .endConfig();
                    break;
                default:
                    break;
            }
            callback.invoke(JSONObject.toJSON(new Error("成功" , 0)));
        } catch (Exception e) {
            UPluginLogger.e(e);
        }
    }






}

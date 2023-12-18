package cn.rubintry.nbui.core.config;


import android.graphics.Point;

import cn.rubintry.nbui.core.INBUIConfig;
import cn.rubintry.nbui.core.NBUI;

/**
 * {@link cn.rubintry.nbui.drag.NBFloatView}的全局配置，优先级低于{@link cn.rubintry.nbui.drag.NBFloatView}的局部配置
 */
public class NBFloatViewConfig implements INBUIConfig {
    private int width = 150;
    private int height = 150;
    private Point position = new Point(0, 0);
    private boolean enableSavePosition = false;

    public NBFloatViewConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public NBFloatViewConfig setHeight(int height)  {
        this.height = height;
        return this;
    }

    public NBFloatViewConfig setPosition(Point position){
        this.position = position;
        return this;
    }

    public NBFloatViewConfig enableSavePosition() {
        this.enableSavePosition = true;
        return this;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Point getPosition(){
        return position;
    }

    public Boolean isEnableSavePosition() {
        return enableSavePosition;
    }

    @Override
    public NBUI endConfig() {
        return NBUI.getInstance();
    }
}

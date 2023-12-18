package cn.rubintry.nbui.core.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class ScreenUtils {

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }
}

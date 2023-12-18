package cn.rubintry.nbui.drag;

import android.graphics.Point;
import android.graphics.RectF;

public class PositionHandler {

    public RectF getRectF(NBFloatView view) {
        if(view.position == null){
            return new RectF();
        }
        Point position = view.position;
        return new RectF((float) position.x, (float) position.y , (float) position.x + view.mWidth , (float) (position.y + view.mHeight));
    }
}

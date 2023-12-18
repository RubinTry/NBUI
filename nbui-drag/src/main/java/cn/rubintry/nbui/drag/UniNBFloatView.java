package cn.rubintry.nbui.drag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.common.UniModule;

public class UniNBFloatView extends UniModule {

    private NBFloatView nbFloatView;

    @UniJSMethod
    public void attach(){
        nbFloatView = new NBFloatView(mUniSDKInstance.getContext());
        nbFloatView.addSelfToWindow();
        //canvas绘制图片
        nbFloatView.setOnDrawListener(new OnDrawListener() {
            @Override
            public void onDraw(@NonNull RectF rectF, @Nullable Canvas canvas, @NonNull NBFloatView view, @Nullable Paint paint) {
                if(canvas == null || paint == null){
                    return;
                }
                int saveCount = canvas.getSaveCount();
                float shadowRadius = 40f; // 阴影半径
                // 设置阴影效果
                paint.setShadowLayer(shadowRadius, 0f, 0f, Color.parseColor("#99000000"));
                paint.setColor(0xFFFFFFFF);
                canvas.drawRoundRect(new RectF(rectF.left + shadowRadius / 2 , rectF.top + shadowRadius / 2 , rectF.right - shadowRadius / 2 , rectF.bottom - shadowRadius / 2)  , rectF.width() / 2f , rectF.height() / 2f , paint);
                Bitmap bitmap = BitmapFactory.decodeResource(mUniSDKInstance.getContext().getResources(), cn.rubintry.nbui.drag.R.mipmap.favicon);
                canvas.drawBitmap(bitmap , null , new RectF(rectF.left + shadowRadius / 2 , rectF.top + shadowRadius / 2 , rectF.right - shadowRadius / 2 , rectF.bottom - shadowRadius / 2) , paint);
                canvas.restoreToCount(saveCount);
            }
        });
    }
}

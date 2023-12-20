package cn.rubintry.nbui.drag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taobao.weex.bridge.SimpleJSCallback;

import cn.rubintry.nbui.core.Error;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class UniNBFloatView extends UniModule {

    private NBFloatView nbFloatView;

    @UniJSMethod
    public void attach(String url){
        nbFloatView = new NBFloatView(mUniSDKInstance.getContext());
        String imageUrl = url == null ? "": url;
        if(!imageUrl.isEmpty()){
            Glide.with(mUniSDKInstance.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            attachFloatView(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
            return;
        }
        attachFloatView(null);
    }

    private void attachFloatView(Bitmap bitmap) {
        nbFloatView.addSelfToWindow();
        if(null == bitmap){
            bitmap = BitmapFactory.decodeResource(mUniSDKInstance.getContext().getResources(), cn.rubintry.nbui.drag.R.mipmap.favicon);
        }
        //canvas绘制图片
        Bitmap finalBitmap = bitmap;
        nbFloatView.setOnDrawListener(new OnDrawListener() {
            @Override
            public void onDraw(@NonNull RectF rectF, @Nullable Canvas canvas, @NonNull NBFloatView view, @Nullable Paint paint) {
                if(canvas == null || paint == null || mUniSDKInstance.getContext() == null){
                    return;
                }
                int saveCount = canvas.getSaveCount();
                float shadowRadius = 40f; // 阴影半径
                // 设置阴影效果
                paint.setShadowLayer(shadowRadius, 0f, 0f, Color.parseColor("#99000000"));
                paint.setColor(0xFFFFFFFF);
                canvas.drawRoundRect(new RectF(rectF.left + shadowRadius / 2 , rectF.top + shadowRadius / 2 , rectF.right - shadowRadius / 2 , rectF.bottom - shadowRadius / 2)  , rectF.width() / 2f , rectF.height() / 2f , paint);
                RectF bitmapRect = new RectF(rectF);
                float bitmapRatio = finalBitmap.getWidth() / finalBitmap.getHeight();
                if(bitmapRatio > 1){
                    float bitmapRealHeight = finalBitmap.getHeight() * rectF.width() / finalBitmap.getWidth();
                    bitmapRect.top += (rectF.height() - bitmapRealHeight) / 2 + shadowRadius / 2;
                    bitmapRect.bottom -= (rectF.height() - bitmapRealHeight) / 2 + shadowRadius / 2;
                    bitmapRect.left += shadowRadius / 2;
                    bitmapRect.right -= shadowRadius / 2;
                }
//                bitmapRect = new RectF(rectF.left + shadowRadius / 2 , rectF.top + shadowRadius / 2 , rectF.right - shadowRadius / 2 , rectF.bottom - shadowRadius / 2);
                canvas.drawBitmap(finalBitmap, null , bitmapRect , paint);
                canvas.restoreToCount(saveCount);
            }
        });
    }


    /**
     * 设置点击事件
     * @param callback 点击回调
     * 如果你碰到了callback只能执行一次的情况，请看这里，这是因为人家本来就让{@link com.taobao.weex.bridge.JSCallback#invoke(Object)}方法只能执行一次（猜的，但是有根据，在后面），
     * 解决办法，使用{@link com.taobao.weex.bridge.JSCallback#invokeAndKeepAlive(Object)}代替，看字面意思也该明白了，这个方法能够一直活跃，不会只能调用一次
     */
    @UniJSMethod
    public void setOnClickListener(SimpleJSCallback callback){
        if(nbFloatView == null){
            return;
        }
        nbFloatView.setOnClickListener(new OnClickListener() {
            @Override
            public void click(@NonNull NBFloatView view) {
                if(null != callback){
                    callback.invokeAndKeepAlive(JSONObject.toJSON(new Error("触发点击" , 0)));
                }
            }
        });
    }


    @UniJSMethod
    public void detach(){
        nbFloatView.detachSelf();
    }
}

package cn.rubintry.nbui.pull;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.rubintry.nbui.core.UPluginLogger;
import cn.rubintry.nbui.core.utils.SizeUtils;
import io.dcloud.feature.uniapp.UniSDKInstance;
import io.dcloud.feature.uniapp.ui.action.AbsComponentData;
import io.dcloud.feature.uniapp.ui.component.AbsVContainer;
import io.dcloud.feature.uniapp.ui.component.UniComponent;
import io.dcloud.feature.uniapp.ui.component.UniComponentProp;


/**
 * {@link NBElasticView}的UniApp组件
 * Created by RubinTry on 2023/12/08.
 * NB的仿抖音“我的”页面的下拉放大效果，特别NB，自带惯性越界回弹
 * 回弹时，速度越快，回弹越猛
 */
public class UniNBElasticView extends UniComponent<NBElasticView> {

    private NBElasticView view;

    private ImageView headerView;

    private LinearLayout linearLayout;
    private final List<String> datas = new ArrayList<>();


    public UniNBElasticView(UniSDKInstance instance, AbsVContainer parent, AbsComponentData componentData) {
        super(instance, parent, componentData);
    }

    @Override
    protected NBElasticView initComponentHostView(@NonNull Context context) {
        view = new NBElasticView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        NestedScrollView scrollView = new NestedScrollView(context);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.setFillViewport(true);
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        headerView = new ImageView(context);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , SizeUtils.dp2px(200)));
        headerView.setAdjustViewBounds(true);
        view.setHeader(headerView);
        linearLayout.addView(headerView);
        RecyclerView rv = new RecyclerView(mUniSDKInstance.getContext());
        rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT));
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(mUniSDKInstance.getContext()));
        RvAdapter adapter = new RvAdapter(datas);
        rv.setAdapter(adapter);
        linearLayout.addView(rv);
        scrollView.addView(linearLayout);
        view.addView(scrollView);
        view.setOnReadyPullListener(new OnReadyPullListener() {
            @Override
            public boolean isReady() {
                return scrollView.getScrollY() == 0;
            }
        });

        return view;
    }


    /**
     * 设置{@link NBElasticView}的宽度
     * @param width 宽度
     */
    @UniComponentProp(name = "NBWidth")
    public void setViewWidth(String width){
        UPluginLogger.d("setViewWidth： " + width);
        if(view == null){
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) Float.parseFloat(width);
        view.setLayoutParams(layoutParams);
    }


    /**
     * 设置{@link NBElasticView}的高度
     * @param height 高度
     */
    @UniComponentProp(name = "NBHeight")
    public void setViewHeight(String height){
        try{
            UPluginLogger.d("setViewHeight： " + height);
            if(view == null || headerView == null || linearLayout == null){
                return;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = (int) Float.parseFloat(height);
            view.setLayoutParams(layoutParams);
        }catch (Exception e){
            UPluginLogger.e(e);
        }
    }

    @UniComponentProp(name = "headerWidth")
    public void setHeaderWidth(String width){
        try{
            UPluginLogger.d("setHeaderWidth： " + width);
            if(view == null || headerView == null || linearLayout == null){
                return;
            }
            ViewGroup.LayoutParams layoutParams = headerView.getLayoutParams();
            layoutParams.width = (int) Float.parseFloat(width);
            headerView.setLayoutParams(layoutParams);
        }catch (Exception e){
            UPluginLogger.e(e);
        }
    }



    /**
     * 设置{@link NBElasticView#headerView}的高度
     * @param height 高度
     */
    @UniComponentProp(name = "headerHeight")
    public void setHeaderHeight(String height){
        try{
            UPluginLogger.d("setHeaderHeight： " + height);
            if(view == null || headerView == null || linearLayout == null){
                return;
            }
            ViewGroup.LayoutParams layoutParams = headerView.getLayoutParams();
            layoutParams.height = (int) Float.parseFloat(height);
            headerView.setLayoutParams(layoutParams);
        }catch (Exception e){
            UPluginLogger.e(e);
        }
    }


    /**
     * 设置背景色，支持8位数的16进制颜色码，头两位表示不透明度，取值范围[0,F]
     * @param color 背景色
     */
    @UniComponentProp(name = "bgc")
    public void setViewBackgroundColor(String color){
        if(view == null || headerView == null || linearLayout == null){
            return;
        }
        view.setBackgroundColor(Color.parseColor(color));
    }


    /**
     * 图片链接
     * @param path
     */
    @UniComponentProp(name = "headerImagePath")
    public void setHeaderImagePath(String path) {
        if(view == null || headerView == null || linearLayout == null){
            return;
        }

        Uri imgUri = Uri.parse(path);
        if(imgUri.getScheme() != null && (imgUri.getScheme().equals("http") || imgUri.getScheme().equals("https"))){
            Glide.with(mUniSDKInstance.getContext().getApplicationContext())
                    .load(imgUri)
                    .into(headerView);
            return;
        }
        Glide.with(mUniSDKInstance.getContext().getApplicationContext())
                .load(imgUri)
                .into(headerView);
    }

    @UniComponentProp(name = "dataArray")
    public void setArrayData(String dataStr){
        Log.d("TAG", "setArrayData: " + dataStr);
        if(view == null || headerView == null || linearLayout == null){
            return;
        }
        List<String> strings = JSON.parseArray(dataStr, String.class);
        this.datas.clear();
        this.datas.addAll(strings);
    }
}

package cn.rubintry.nbui.core;

import java.util.concurrent.ConcurrentHashMap;

public class NBUI {
    private ConcurrentHashMap<Class<? extends INBUIConfig> , INBUIConfig> uiGlobalConfigMap = new ConcurrentHashMap<>();

    private static volatile NBUI instance;

    public static NBUI getInstance(){
        if(instance == null){
            synchronized (NBUI.class){
                if(instance == null){
                    instance = new NBUI();
                }
            }
        }
        return instance;
    }

    private NBUI(){

    }


    /**
     * 设置UI全局配置，优先级低于直接在view中配置
     */
    public  <T extends INBUIConfig> T config(Class<T> clazz){
        if(uiGlobalConfigMap.containsKey(clazz)){
            return (T) uiGlobalConfigMap.get(clazz);
        }
        try {
            INBUIConfig obj = clazz.newInstance();
            uiGlobalConfigMap.put(clazz , obj);
            return (T) obj;
        }catch (Exception e){

        }
        return null;
    }
}

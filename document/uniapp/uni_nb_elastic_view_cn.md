## 组件使用

* 如果你按照教程集成了插件，但发现系统它找不到，则需要在项目根目录下创建vite.config.js，并加入如下代码：
```javascript
import {      
    defineConfig      
} from 'vite'      
import uni from '@dcloudio/vite-plugin-uni'  
const CUSTOM_TAG = ['UniNBElasticView']//把自定义原生组件的名字写入到这里面  
export default defineConfig({      
    plugins: [      
        uni({      
            vueOptions: {      
                template: {      
                    compilerOptions: {  
                        isCustomElement: tag => CUSTOM_TAG.includes(tag)//这样就可以让编译器知道哪些是自定义组件  
                    }      
                }      
            }      
        })      
    ]      
})
```


* UniNBElasticView

由于是原生view，因此只能在nvue中才能使用，其中，mListData为列表数据源，仅支持string类型，如需特殊定制，请自行封装实现，[参考](https://nativesupport.dcloud.net.cn/NativePlugin/course/android.html)

```
<template>
	<div :style="{width: screenWidth , height: screenHeight , backgroundColor: '#ffffff'}">
		<!-- :bgc 组件透明度，支持使用八位数十六进制颜色码，前两位表示不透明度，取值范围00~FF -->
		<UniNBElasticView :bgc="'#88aaaaaa'" :NBWidth=getScreenWidth() :NBHeight=getScreenHeight() headerImagePath="https://rubintry.com.cn:8888/a_logo.png" :dataArray="mListData">
		</UniNBElasticView>
	</div>
</template>
```
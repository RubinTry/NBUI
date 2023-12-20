
* UniNBFloatView

由于本组件的层级凌驾于大部分UI View之上，因此直接使用即可，无需做其他引入操作
```
<script>
    var nbuiModule = uni.requireNativePlugi("NBUI-Uni-drag");
    export default {
        onLoad() {
			nbuiModule.attach();
		},
    }
</script>
```
此时，你便可在app上看到悬浮球了
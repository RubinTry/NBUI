
* UniNBFloatView
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
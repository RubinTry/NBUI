
* UniNBFloatView

由于本组件的层级凌驾于大部分UI View之上，因此直接使用即可，无需做其他引入操作
```
<script>
    var floatView = uni.requireNativePlugin("UniNBFloatView")
    export default {
        mounted() {
			floatView.attach();
            floatView.setOnClickListener((msg) => {
				console.log(msg);
				uni.showToast({
					icon:"none",
					title: msg
				})
			});
		},
        onBackPress() {
			floatView.detach();
		},
    }
</script>
```
此时，你便可在app上看到悬浮球了
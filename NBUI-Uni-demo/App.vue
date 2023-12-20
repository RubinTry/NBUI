<script>
	// 获取 module
	var globalConfig = uni.requireNativePlugin("NBUI-Uni-core")
	export default {
		onLaunch: function() {
			console.log('App Launch')
			//全局配置
			globalConfig.config({
				configName: "NBFloatViewConfig",
				width: 200,
				height: 200,
				position: {
					x: 100,
					y: 100
				},
			}, (msg) => {
				console.log(msg);
			});
			globalConfig.config({
				configName: "NBElasticPullConfig",
				elasticCoefficient: 0.95,
			}, (msg) => {
				console.log(msg);
			});
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
</style>

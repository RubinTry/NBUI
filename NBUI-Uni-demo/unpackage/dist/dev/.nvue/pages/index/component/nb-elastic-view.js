import { resolveComponent, openBlock, createElementBlock, normalizeStyle, createCommentVNode, createVNode } from "vue";
import { _ as _export_sfc } from "../../../_plugin-vue_export-helper.js";
const _sfc_main = {
  data() {
    return {
      screenWidth: 0,
      screenHeight: 0,
      mListData: [
        "1"
      ]
    };
  },
  components: {},
  methods: {
    getScreenWidth() {
      return this.screenWidth;
    },
    getScreenHeight() {
      return this.screenHeight;
    }
  },
  created() {
    const windowInfo = uni.getWindowInfo();
    this.screenWidth = windowInfo.screenWidth * windowInfo.pixelRatio;
    this.screenHeight = windowInfo.windowHeight * windowInfo.pixelRatio;
    for (var i = 0; i < 100; i++) {
      this.mListData[i] = i + "";
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_UniNBElasticView = resolveComponent("UniNBElasticView");
  return openBlock(), createElementBlock(
    "div",
    {
      style: normalizeStyle({ width: $data.screenWidth, height: $data.screenHeight, backgroundColor: "#ffffff" })
    },
    [
      createCommentVNode(" :bgc 组件透明度，支持使用八位数十六进制颜色码，前两位表示不透明度，取值范围00~FF "),
      createVNode(_component_UniNBElasticView, {
        bgc: "#88aaaaaa",
        NBWidth: $options.getScreenWidth(),
        NBHeight: $options.getScreenHeight(),
        headerImagePath: "https://rubintry.com.cn:8888/a_logo.png",
        dataArray: $data.mListData
      }, null, 8, ["NBWidth", "NBHeight", "dataArray"])
    ],
    4
    /* STYLE */
  );
}
const nbElasticView = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/uniApp/NBUI-test/NBUI-test/pages/index/component/nb-elastic-view.nvue"]]);
export {
  nbElasticView as default
};

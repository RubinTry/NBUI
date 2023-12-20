import { openBlock, createElementBlock, createElementVNode } from "vue";
import { _ as _export_sfc } from "../../../_plugin-vue_export-helper.js";
function requireNativePlugin(name) {
  return weex.requireModule(name);
}
function formatAppLog(type, filename, ...args) {
  if (uni.__log__) {
    uni.__log__(type, filename, ...args);
  } else {
    console[type].apply(console, [...args, filename]);
  }
}
var floatView = requireNativePlugin("UniNBFloatView");
const _sfc_main = {
  data() {
    return {};
  },
  mounted() {
    floatView.attach();
    floatView.setOnClickListener((msg) => {
      formatAppLog("log", "at pages/index/component/nb-float-view.nvue:18", msg);
      uni.showToast({
        icon: "none",
        title: msg
      });
    });
  },
  onBackPress() {
    floatView.detach();
  },
  methods: {
    getRealUrl(url) {
      if (url.startsWith("/")) {
        return plus.io.convertLocalFileSystemURL(url);
      }
      return url;
    }
  }
};
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return openBlock(), createElementBlock("scroll-view", {
    scrollY: true,
    showScrollbar: true,
    enableBackToTop: true,
    bubble: "true",
    style: { flexDirection: "column" }
  }, [
    createElementVNode("view")
  ]);
}
const nbFloatView = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/uniApp/NBUI-test/NBUI-test/pages/index/component/nb-float-view.nvue"]]);
export {
  nbFloatView as default
};

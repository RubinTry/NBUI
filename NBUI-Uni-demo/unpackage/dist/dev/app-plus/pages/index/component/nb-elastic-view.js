"use weex:vue";

if (typeof Promise !== 'undefined' && !Promise.prototype.finally) {
  Promise.prototype.finally = function(callback) {
    const promise = this.constructor
    return this.then(
      value => promise.resolve(callback()).then(() => value),
      reason => promise.resolve(callback()).then(() => {
        throw reason
      })
    )
  }
};

if (typeof uni !== 'undefined' && uni && uni.requireGlobal) {
  const global = uni.requireGlobal()
  ArrayBuffer = global.ArrayBuffer
  Int8Array = global.Int8Array
  Uint8Array = global.Uint8Array
  Uint8ClampedArray = global.Uint8ClampedArray
  Int16Array = global.Int16Array
  Uint16Array = global.Uint16Array
  Int32Array = global.Int32Array
  Uint32Array = global.Uint32Array
  Float32Array = global.Float32Array
  Float64Array = global.Float64Array
  BigInt64Array = global.BigInt64Array
  BigUint64Array = global.BigUint64Array
};


(() => {
  var __create = Object.create;
  var __defProp = Object.defineProperty;
  var __getOwnPropDesc = Object.getOwnPropertyDescriptor;
  var __getOwnPropNames = Object.getOwnPropertyNames;
  var __getProtoOf = Object.getPrototypeOf;
  var __hasOwnProp = Object.prototype.hasOwnProperty;
  var __commonJS = (cb, mod) => function __require() {
    return mod || (0, cb[__getOwnPropNames(cb)[0]])((mod = { exports: {} }).exports, mod), mod.exports;
  };
  var __copyProps = (to, from, except, desc) => {
    if (from && typeof from === "object" || typeof from === "function") {
      for (let key of __getOwnPropNames(from))
        if (!__hasOwnProp.call(to, key) && key !== except)
          __defProp(to, key, { get: () => from[key], enumerable: !(desc = __getOwnPropDesc(from, key)) || desc.enumerable });
    }
    return to;
  };
  var __toESM = (mod, isNodeMode, target) => (target = mod != null ? __create(__getProtoOf(mod)) : {}, __copyProps(
    // If the importer is in node compatibility mode or this is not an ESM
    // file that has been converted to a CommonJS file using a Babel-
    // compatible transform (i.e. "__esModule" has not been set), then set
    // "default" to the CommonJS "module.exports" for node compatibility.
    isNodeMode || !mod || !mod.__esModule ? __defProp(target, "default", { value: mod, enumerable: true }) : target,
    mod
  ));

  // vue-ns:vue
  var require_vue = __commonJS({
    "vue-ns:vue"(exports, module) {
      module.exports = Vue;
    }
  });

  // ../../../uniApp/NBUI-test/NBUI-test/unpackage/dist/dev/.nvue/pages/index/component/nb-elastic-view.js
  var import_vue = __toESM(require_vue());

  // ../../../uniApp/NBUI-test/NBUI-test/unpackage/dist/dev/.nvue/_plugin-vue_export-helper.js
  var _export_sfc = (sfc, props) => {
    const target = sfc.__vccOpts || sfc;
    for (const [key, val] of props) {
      target[key] = val;
    }
    return target;
  };

  // ../../../uniApp/NBUI-test/NBUI-test/unpackage/dist/dev/.nvue/pages/index/component/nb-elastic-view.js
  var _sfc_main = {
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
    const _component_UniNBElasticView = (0, import_vue.resolveComponent)("UniNBElasticView");
    return (0, import_vue.openBlock)(), (0, import_vue.createElementBlock)(
      "div",
      {
        style: (0, import_vue.normalizeStyle)({ width: $data.screenWidth, height: $data.screenHeight, backgroundColor: "#ffffff" })
      },
      [
        (0, import_vue.createCommentVNode)(" :bgc \u7EC4\u4EF6\u900F\u660E\u5EA6\uFF0C\u652F\u6301\u4F7F\u7528\u516B\u4F4D\u6570\u5341\u516D\u8FDB\u5236\u989C\u8272\u7801\uFF0C\u524D\u4E24\u4F4D\u8868\u793A\u4E0D\u900F\u660E\u5EA6\uFF0C\u53D6\u503C\u8303\u56F400~FF "),
        (0, import_vue.createVNode)(_component_UniNBElasticView, {
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
  var nbElasticView = /* @__PURE__ */ _export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "D:/uniApp/NBUI-test/NBUI-test/pages/index/component/nb-elastic-view.nvue"]]);

  // <stdin>
  var webview = plus.webview.currentWebview();
  if (webview) {
    const __pageId = parseInt(webview.id);
    const __pagePath = "pages/index/component/nb-elastic-view";
    let __pageQuery = {};
    try {
      __pageQuery = JSON.parse(webview.__query__);
    } catch (e) {
    }
    nbElasticView.mpType = "page";
    const app = Vue.createPageApp(nbElasticView, { $store: getApp({ allowDefault: true }).$store, __pageId, __pagePath, __pageQuery });
    app.provide("__globalStyles", Vue.useCssStyles([...__uniConfig.styles, ...nbElasticView.styles || []]));
    app.mount("#root");
  }
})();

if (typeof Promise !== "undefined" && !Promise.prototype.finally) {
  Promise.prototype.finally = function(callback) {
    const promise = this.constructor;
    return this.then(
      (value) => promise.resolve(callback()).then(() => value),
      (reason) => promise.resolve(callback()).then(() => {
        throw reason;
      })
    );
  };
}
;
if (typeof uni !== "undefined" && uni && uni.requireGlobal) {
  const global = uni.requireGlobal();
  ArrayBuffer = global.ArrayBuffer;
  Int8Array = global.Int8Array;
  Uint8Array = global.Uint8Array;
  Uint8ClampedArray = global.Uint8ClampedArray;
  Int16Array = global.Int16Array;
  Uint16Array = global.Uint16Array;
  Int32Array = global.Int32Array;
  Uint32Array = global.Uint32Array;
  Float32Array = global.Float32Array;
  Float64Array = global.Float64Array;
  BigInt64Array = global.BigInt64Array;
  BigUint64Array = global.BigUint64Array;
}
;
if (uni.restoreGlobal) {
  uni.restoreGlobal(Vue, weex, plus, setTimeout, clearTimeout, setInterval, clearInterval);
}
(function(vue) {
  "use strict";
  const _export_sfc = (sfc, props) => {
    const target = sfc.__vccOpts || sfc;
    for (const [key, val] of props) {
      target[key] = val;
    }
    return target;
  };
  const _sfc_main$1 = {
    data() {
      return {
        title: "Hello"
      };
    },
    onLoad() {
    },
    methods: {
      elasticViewClick() {
        uni.navigateTo({
          url: "/pages/index/component/nb-elastic-view"
        });
      },
      floatViewClick() {
        uni.navigateTo({
          url: "/pages/index/component/nb-float-view"
        });
      }
    }
  };
  function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
    return vue.openBlock(), vue.createElementBlock("div", null, [
      vue.createElementVNode("button", {
        type: "primary",
        onClick: _cache[0] || (_cache[0] = (...args) => $options.elasticViewClick && $options.elasticViewClick(...args))
      }, "NBUI-ElasticView"),
      vue.createElementVNode("button", {
        type: "primary",
        onClick: _cache[1] || (_cache[1] = (...args) => $options.floatViewClick && $options.floatViewClick(...args))
      }, "NBUI-Float-View")
    ]);
  }
  const PagesIndexIndex = /* @__PURE__ */ _export_sfc(_sfc_main$1, [["render", _sfc_render], ["__file", "D:/uniApp/NBUI-test/NBUI-test/pages/index/index.vue"]]);
  __definePage("pages/index/index", PagesIndexIndex);
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
  var globalConfig = requireNativePlugin("NBUI-Uni-core");
  const _sfc_main = {
    onLaunch: function() {
      formatAppLog("log", "at App.vue:6", "App Launch");
      globalConfig.config({
        configName: "NBFloatViewConfig",
        width: 200,
        height: 200,
        position: {
          x: 100,
          y: 100
        }
      }, (msg) => {
        formatAppLog("log", "at App.vue:17", msg);
      });
      globalConfig.config({
        configName: "NBElasticPullConfig",
        elasticCoefficient: 0.95
      }, (msg) => {
        formatAppLog("log", "at App.vue:23", msg);
      });
    },
    onShow: function() {
      formatAppLog("log", "at App.vue:27", "App Show");
    },
    onHide: function() {
      formatAppLog("log", "at App.vue:30", "App Hide");
    }
  };
  const App = /* @__PURE__ */ _export_sfc(_sfc_main, [["__file", "D:/uniApp/NBUI-test/NBUI-test/App.vue"]]);
  function createApp() {
    const app = vue.createVueApp(App);
    return {
      app
    };
  }
  const { app: __app__, Vuex: __Vuex__, Pinia: __Pinia__ } = createApp();
  uni.Vuex = __Vuex__;
  uni.Pinia = __Pinia__;
  __app__.provide("__globalStyles", __uniConfig.styles);
  __app__._component.mpType = "app";
  __app__._component.render = () => {
  };
  __app__.mount("#app");
})(Vue);

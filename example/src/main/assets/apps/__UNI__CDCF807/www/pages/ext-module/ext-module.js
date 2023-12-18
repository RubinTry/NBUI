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


(()=>{var g=Object.create;var d=Object.defineProperty;var v=Object.getOwnPropertyDescriptor;var f=Object.getOwnPropertyNames;var b=Object.getPrototypeOf,x=Object.prototype.hasOwnProperty;var k=(e,o)=>()=>(o||e((o={exports:{}}).exports,o),o.exports);var w=(e,o,n,r)=>{if(o&&typeof o=="object"||typeof o=="function")for(let s of f(o))!x.call(e,s)&&s!==n&&d(e,s,{get:()=>o[s],enumerable:!(r=v(o,s))||r.enumerable});return e};var C=(e,o,n)=>(n=e!=null?g(b(e)):{},w(o||!e||!e.__esModule?d(n,"default",{value:e,enumerable:!0}):n,e));var m=k((T,_)=>{_.exports=Vue});var t=C(m());function y(e){return weex.requireModule(e)}var A=(e,o)=>{let n=e.__vccOpts||e;for(let[r,s]of o)n[r]=s;return n},l=y("TestModule"),i=y("modal"),F={onLoad(){plus.globalEvent.addEventListener("TestEvent",function(e){i.toast({message:"TestEvent\u6536\u5230\uFF1A"+e.msg,duration:1.5})})},methods:{testAsyncFunc(){l.testAsyncFunc({name:"unimp",age:1},e=>{i.toast({message:e,duration:1.5})})},testSyncFunc(){var e=l.testSyncFunc({name:"unimp",age:1});i.toast({message:e,duration:1.5})},gotoNativePage(){l.gotoNativePage()}}};function N(e,o,n,r,s,c){let u=(0,t.resolveComponent)("button");return(0,t.openBlock)(),(0,t.createElementBlock)("scroll-view",{scrollY:!0,showScrollbar:!0,enableBackToTop:!0,bubble:"true",style:{flexDirection:"column"}},[(0,t.createElementVNode)("div",null,[(0,t.createVNode)(u,{type:"primary",onClick:c.testAsyncFunc},{default:(0,t.withCtx)(()=>[(0,t.createTextVNode)("testAsyncFunc")]),_:1},8,["onClick"]),(0,t.createVNode)(u,{type:"primary",onClick:c.testSyncFunc},{default:(0,t.withCtx)(()=>[(0,t.createTextVNode)("testSyncFunc")]),_:1},8,["onClick"]),(0,t.createVNode)(u,{type:"primary",onClick:c.gotoNativePage},{default:(0,t.withCtx)(()=>[(0,t.createTextVNode)("\u8DF3\u8F6C\u539F\u751FActivity")]),_:1},8,["onClick"])])])}var a=A(F,[["render",N]]);var p=plus.webview.currentWebview();if(p){let e=parseInt(p.id),o="pages/ext-module/ext-module",n={};try{n=JSON.parse(p.__query__)}catch(s){}a.mpType="page";let r=Vue.createPageApp(a,{$store:getApp({allowDefault:!0}).$store,__pageId:e,__pagePath:o,__pageQuery:n});r.provide("__globalStyles",Vue.useCssStyles([...__uniConfig.styles,...a.styles||[]])),r.mount("#root")}})();

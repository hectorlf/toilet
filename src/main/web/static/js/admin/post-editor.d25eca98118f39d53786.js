(window.webpackJsonp=window.webpackJsonp||[]).push([[3],{5:function(t,e){var n;n=function(){return this}();try{n=n||new Function("return this")()}catch(t){"object"==typeof window&&(n=window)}t.exports=n},6:function(t,e,n){"use strict";n.r(e);var c=n(0),o=n(3),i=n(4),s=n(2);function b(t){let e,n;return{c(){e=Object(c.s)("Editing post "),n=Object(c.s)(t[0])},m(t,o){Object(c.i)(t,e,o),Object(c.i)(t,n,o)},p(t,e){1&e&&Object(c.o)(n,t[0])},d(t){t&&Object(c.e)(e),t&&Object(c.e)(n)}}}function l(t){let e;return{c(){e=Object(c.s)("New post")},m(t,n){Object(c.i)(t,e,n)},p:c.l,d(t){t&&Object(c.e)(e)}}}function r(t){let e,n,o,i,s,r,j,p;function O(t,e){return t[2]?l:b}let u=O(t),a=u(t);return{c(){e=Object(c.f)("h1"),a.c(),n=Object(c.q)(),o=Object(c.f)("label"),o.textContent="Title",i=Object(c.f)("input"),s=Object(c.q)(),r=Object(c.f)("button"),r.textContent="Save",Object(c.c)(o,"for","title"),Object(c.c)(i,"name","title")},m(b,l){Object(c.i)(b,e,l),a.m(e,null),Object(c.i)(b,n,l),Object(c.i)(b,o,l),Object(c.i)(b,i,l),Object(c.p)(i,t[1]),Object(c.i)(b,s,l),Object(c.i)(b,r,l),j||(p=[Object(c.k)(i,"input",t[5]),Object(c.k)(r,"click",t[3])],j=!0)},p(t,[n]){u===(u=O(t))&&a?a.p(t,n):(a.d(1),a=u(t),a&&(a.c(),a.m(e,null))),2&n&&i.value!==t[1]&&Object(c.p)(i,t[1])},i:c.l,o:c.l,d(t){t&&Object(c.e)(e),a.d(),t&&Object(c.e)(n),t&&Object(c.e)(o),t&&Object(c.e)(i),t&&Object(c.e)(s),t&&Object(c.e)(r),j=!1,Object(c.m)(p)}}}function j(){console.log("Saving...")}function p(t,e,n){let{baseEndpointUrl:c=""}=e,{postId:b=""}=e;const l=c+"/posts";let r="";const p=i.a.debounce(j,5e3);let O,u;return Object(o.a)((async()=>{O?console.log("Loading post from "+u):console.log("New post"),s.a.get(u).then((t=>console.log("Loaded"))).catch((t=>console.log("Error loading")))})),t.$$set=t=>{"baseEndpointUrl"in t&&n(4,c=t.baseEndpointUrl),"postId"in t&&n(0,b=t.postId)},t.$$.update=()=>{1&t.$$.dirty&&n(2,O=0==b),17&t.$$.dirty&&(u=c+"/posts/"+b)},[b,r,O,function(){O?console.log("Creating post at "+l):console.log("Saving post at "+u),p()},c,function(){r=this.value,n(1,r)}]}class O extends c.a{constructor(t){super(),Object(c.h)(this,t,p,r,c.n,{baseEndpointUrl:4,postId:0})}}var u=O;new u({target:document.getElementById("new-post")}),new u({target:document.getElementById("edit-post"),props:{postId:"1"}})}},[[6,1,2,0]]]);
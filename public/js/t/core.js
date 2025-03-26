// Compiled by ClojureScript 1.11.132 {:static-fns true, :optimize-constants true, :optimizations :advanced}
goog.provide('t.core');
goog.require('cljs.core');
goog.require('cljs.core.constants');
if((typeof t !== 'undefined') && (typeof t.core !== 'undefined') && (typeof t.core.root !== 'undefined')){
} else {
t.core.root = document.getElementById("root");
}
(t.core.root.innerHTML = "<h1>Hello World!</h1>");

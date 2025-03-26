#!/bin/sh

echo "Cleaning ..."
rm -rf public/js/
echo "Cleaning Done!"

echo "Building ..."
clj -M -m cljs.main --compile-opts '{:verbose true :asset-path "js" :output-dir "public/js" :optimizations :advanced}' --compile t.core
echo "Building Done!"

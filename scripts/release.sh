#!/bin/sh

echo "Cleaning ..."
rm -rf public/js/
echo "Cleaning Done!"

echo "Building ..."
clj -M:shadow-cljs --verbose release app
echo "Building Done!"

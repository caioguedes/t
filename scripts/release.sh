#!/bin/sh

echo "Cleaning ..."
rm -rf public/js/
rm -rf node_modules/
echo "Cleaning Done!"

echo "Installing npm dependencies ..."
yarn install

echo "Building ..."
clj -M:shadow-cljs --verbose release app
echo "Building Done!"

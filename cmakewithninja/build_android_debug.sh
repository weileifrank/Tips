#!/bin/sh
buildDir="./build/Debug"
rm -rf "$buildDir"
if [ ! -d "$buildDir" ];then
  mkdir -p "$buildDir"
fi
cd "$buildDir"
NINJA_PATH=`which ninja`
cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_MAKE_PROGRAM=$NINJA_PATH -DCMAKE_TOOLCHAIN_FILE=../../toolchain/android.toolchain.cmake -GNinja ../..
cmake --build . --config Debug
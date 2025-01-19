#!/bin/bash

SOURCES=$(find ./src -type f -name '*.java' ! -name module-info.java)

javac -d ./build -cp ./lib/lanterna-3.1.2.jar $SOURCES

cd build

ant
#!/bin/bash

cd ressources/* classes/
export CLASSPATH=`find ../lib -name "*.jar" | tr '\n' ':'`
java -cp ${CLASSPATH}:. $@
cd ..
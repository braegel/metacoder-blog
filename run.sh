#!/bin/bash
./compile.sh && 
scala -classpath target de.metacoder.blog.XMLBackend
exit $?

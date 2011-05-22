#!/bin/bash
scalac -d target -deprecation -unchecked StorageFileHandler.scala XMLBackend.scala Entities.scala App.scala && 
scala -classpath target de.metacoder.blog.XMLBackend
exit $?

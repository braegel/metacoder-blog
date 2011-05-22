#!/bin/bash

DIRECTORY="target"

if [ -d "$DIRECTORY" ]; then
    rm -rf "$DIRECTORY"
fi

mkdir "$DIRECTORY"

scalac -d "$DIRECTORY" -deprecation -unchecked StorageFileHandler.scala XMLBackend.scala Entities.scala App.scala
exit $?

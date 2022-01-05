#!/bin/sh

name1="Romain-HU"
name2="Dakini-MALLAM_GARBA"
release="D1"-$name1-$name2

rm -r $release 2> /dev/null
mkdir $release
cd ..
zip -r game-bundle/$release/game-D1-$name1-$name2.zip game-doc

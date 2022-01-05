#!/bin/sh

name1="Romain-HU"
name2="Dakini-MALLAM_GARBA"
release="D2"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
mkdir $release
# compiling and testing the back-end
cd ../game-backend
mvn clean package
cd ..
zip -r game-bundle/$release/game-D2-$name1-$name2-sources.zip game-doc game-backend -x game-backend/target/\* \*.idea/\* \*.iml

## building Docker containers
## Docker container of the backend
#cd game-backend
#docker build -t game-backend .
#cd ../game-bundle
#docker save game-backend | xz > $release/game-$name1-$name2-backend-docker.tar.xz

# docker stop game-backend-run
# docker rm game-backend-run
# docker run -it --name game-backend-run -d -p 4444:4444 game-backend

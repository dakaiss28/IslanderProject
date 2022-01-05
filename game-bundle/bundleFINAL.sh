#!/bin/sh

name1="Romain-HU"
name2="Dakini-MALLAM_GARBA"
release="FINAL"-$name1-$name2

# cleaning the release files
rm -r $release 2> /dev/null
# compiling and testing the back-end
cd ../game-backend
mvn clean package
mvn clean
# building the frontend
cd ../game-frontend
npm install
ng build --prod --build-optimizer
rm -rf node_modules/
rm -rf dist/
# creating release files
cd ../game-bundle
mkdir $release
cd ..
zip -r game-bundle/$release/game-$name1-$name2-sources.zip game-doc game-backend game-frontend/ -x \*.idea/\* \*.iml
cd game-bundle/

# building Docker containers
## Docker container of the back-end
#cd ../game-backend
#docker build -t game-backend .
# docker run -it --name game-backend-run -d -p 4444:4444 game-backend
## Docker container of the front-end
#cd ../game-frontend
#docker build -t game-frontend .
#cd ../game-bundle
#docker save game-backend | xz > $release/game-$name1-$name2-backend.tar.xz
#docker save game-frontend | xz > $release/game-$name1-$name2-frontend.tar.xz

# docker stop game-frontend-run
# docker rm game-frontend-run
# docker run -it --name game-frontend-run -d -p 8080:8080 game-frontend

# Docker stop/remove all the containers
# docker stop $(docker ps -a -q)
# docker rm $(docker ps -a -q)


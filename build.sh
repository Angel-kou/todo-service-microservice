#!/bin/bash

./gradlew clean bootRepackage

docker build --rm . --tag koumiaojuan/zuul:${VER:?invalid version}
docker push koumiaojuan/zuul:${VER:?invalid version}

#export VER
#docker stack deploy todo -c docker-compose.yml
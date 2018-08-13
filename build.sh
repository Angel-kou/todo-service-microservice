#!/bin/bash

./gradlew clean bootRepackage

docker build --rm . --tag koumiaojuan/user-service:${VER:?invalid version}
docker push koumiaojuan/user-service:${VER:?invalid version}

#export VER
#docker stack deploy todo -c docker-compose.yml
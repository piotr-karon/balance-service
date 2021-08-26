#!/bin/bash

cd ..
./gradlew jibDockerBuild

cd docker

docker-compose up -d

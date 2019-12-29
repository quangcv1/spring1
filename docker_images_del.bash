#!/usr/bin/env bash
docker image rm spring_product-composite:latest;
docker image rm spring_product:latest;
docker image rm spring_recommendation:latest;
docker image rm spring_review:latest;
./gradlew build && docker-compose build && docker-compose up -d

#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--build=gradle \
--packaging=jar \
--name=product-service \
--package-name=vn.com.acb.microservices.core.product \
--groupId=vn.com.acb.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--build=gradle \
--packaging=jar \
--name=review-service \
--package-name=vn.com.acb.microservices.core.review \
--groupId=vn.com.acb.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--build=gradle \
--packaging=jar \
--name=recommendation-service \
--package-name=vn.com.acb.microservices.core.recommendation \
--groupId=vn.com.acb.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--build=gradle \
--packaging=jar \
--name=product-composite-service \
--package-name=vn.com.acb.microservices.composite.product \
--groupId=vn.com.acb.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..

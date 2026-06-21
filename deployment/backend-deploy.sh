#!/bin/bash

cd backend

mvn clean package

docker build -t arcpredict-backend .

docker run -d \
  -p 8080:8080 \
  arcpredict-backend
#!/bin/bash

./mvnw clean package
cp server/target/server-jar-with-dependencies.jar ./build/server.jar
cp client/target/client-jar-with-dependencies.jar ./build/client.jar


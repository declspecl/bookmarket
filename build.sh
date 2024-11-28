#!/bin/bash

# clean build frontend
cd bookmarket-website
rm -rf dist
npm run build
cd ..

# clean build backend
cd bookmarket-backend
sudo mkdir -p /var/logs/bookmarket
sudo chmod -R 777 /var/logs/bookmarket
mvn clean package
cd ..

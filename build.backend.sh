#!/bin/bash

# clean build backend
cd ~/bookmarket/bookmarket-backend
sudo mkdir -p /var/logs/bookmarket
sudo chmod -R 777 /var/logs/bookmarket
mvn clean package
cd ~/bookmarket


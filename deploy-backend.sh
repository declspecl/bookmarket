#!/bin/bash

# build jar file
./build.backend.sh

# move jar file
sudo mkdir -p /opt/bookmarket/
sudo rm /opt/bookmarket/bookmarket-backend.jar
sudo mv bookmarket-backend/target/bookmarket-backend.jar /opt/bookmarket

# swap out new systemd service
sudo rm /etc/systemd/system/bookmarket.service
sudo cp conf/bookmarket.service /etc/systemd/system/

# setup service permissions on new user
sudo chmod 644 /etc/systemd/system/bookmarket.service
sudo chown root:root /etc/systemd/system/bookmarket.service
sudo useradd -r bookmarket
sudo chown -R bookmarket:bookmarket /opt/bookmarket

# (re)start bookmarket service
sudo systemctl daemon-reload
sudo systemctl enable bookmarket
sudo systemctl start bookmarket


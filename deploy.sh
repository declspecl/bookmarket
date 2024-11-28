#!/bin/bash

./build.sh

# move frontend files
sudo mkdir -p /var/www/bookmarket
sudo mv bookmarket-website/dist/* /var/www/bookmarket
sudo chmod -R 755 /var/www/bookmarket

# move backend files
sudo mkdir -p /opt/bookmarket/
sudo mv bookmarket-backend/target/bookmarket-backend.jar /opt/bookmarket

# set up nginx
sudo rm /etc/nginx/nginx.conf
sudo cp conf/nginx.conf /etc/nginx/
sudo systemctl enable nginx
sudo systemctl restart nginx

# set up bookmarket service
sudo rm /etc/systemd/system/bookmarket.service
sudo cp conf/bookmarket.service /etc/systemd/system/
sudo chmod 644 /etc/systemd/system/bookmarket.service
sudo chown root:root /etc/systemd/system/bookmarket.service
sudo useradd -r bookmarket
sudo chown -R bookmarket:bookmarket /opt/bookmarket
sudo systemctl daemon-reload
sudo systemctl enable bookmarket
sudo systemctl start bookmarket

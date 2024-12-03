#!/bin/bash

# build artifacts
./build-frontend.sh

# move artifacts
sudo mkdir -p /var/www/bookmarket
sudo rm -r /var/www/bookmarket/*
sudo mv bookmarket-website/dist/* /var/www/bookmarket
sudo chmod -R 755 /var/www/bookmarket

# swap out new nginx config
sudo rm /etc/nginx/nginx.conf
sudo cp conf/nginx.conf /etc/nginx/

# (re)start nginx
sudo systemctl enable nginx
sudo systemctl restart nginx

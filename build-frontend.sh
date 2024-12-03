#!/bin/bash

# clean build frontend
cd ~/bookmarket/bookmarket-website
rm -rf dist
npm run build
cd ~/bookmarket


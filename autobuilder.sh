#!/bin/bash

#backend build
cd backend
./mvnw install
./mvnw clean package
#frontend build
cd ../frontend
npm install
npm run build
#electron merge, build
cd ..
cp backend/target/*.jar electron/resources
cp -R frontend/dist/* electron/resources
cd electron
npm run dist
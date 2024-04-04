#!/bin/bash
cd backend
./mvnw clean package
cd ../frontend
npm run build
cd ..
cp backend/target/*.jar electron/resources
cp -R frontend/dist electron/resources
cd electron
npm run dist
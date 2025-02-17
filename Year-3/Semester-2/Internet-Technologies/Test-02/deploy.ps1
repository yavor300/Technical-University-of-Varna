./gradlew clean build
docker build -t tuvarna-tasks-manager:1.0.0 .
docker-compose up -d
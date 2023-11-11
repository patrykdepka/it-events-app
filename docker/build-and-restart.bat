docker stop it-events-app
docker rm it-events-app
docker build -t patrykdepka/it-events-app .
docker run -d --name it-events-app -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev -e DB_NAME=itevents patrykdepka/it-events-app

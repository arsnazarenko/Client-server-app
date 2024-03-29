# Client-Server application for collection management

## Used technologies:
- JDK 11
- Maven
- PostgreSQL
- Swing GUI Framework
- Docker, Docker compose


1. Build:
```shell
./build-app.sh
cd build
```
2. Run
- **Configuration**

server.properties file:
```
server.host=${SERVER_HOST}
server.port=${SERVER_PORT}
database.host=${POSTGRES_HOST}
database.port=${POSTGRES_PORT}
database.name=${POSTGRES_DB}
database.username=${POSTGRES_USERNAME}
database.password=${POSTGRES_PASSWORD}
```
> Server options can be set using environment variables or .env file. File *server.properties* must be in the same directory as server.jar
- **Server**
```shell
java -jar server.jar
```
- **Client**
```shell
java -jar client.jar [SERVER_HOST] [SERVER_PORT] [gui/cli]
```

- **PostgreSQL** in docker container for Server-side of app
```shell
docker compose up --detach
```
> Don't forget to stop the docker container

## Examples
<img src="samples/gui-reg.png" alt="Registration window" width="75%" height="75%" title="Registration window">
<img src="samples/gui-add.png" alt="New Object creation window" width="75%" height="75%" title="New Object creation window">
<img src="samples/gui-map.png" alt="Objects map window" width="75%" height="75%" title="Objects map window">
<img src="samples/cli.png" alt="Command line interface" width="75%" height="75%" title="Command line interface">

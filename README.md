# Client-Server application for collection management


1. Build:
```shell
./build.sh
```
2. Run
- *Configuration*
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
> Server options can be set using environment variables or .env file
- *Server*
```shell
java -jar server.jar
```
- *Client*
```shell
java -jar client.jar [SERVER_HOST] [SERVER_PORT] [gui/cli]
```

- PostgreSQL in docker container
```shell
docker compose up --detach
```

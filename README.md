# MovieHeaven
## Build from src
1. Start the server
```java
run main 
in src/main/java/com/cuhk/MovieHeaven/MovieHeavenApplication.java
```
2. Access the homepage from the client

```java
http://localhost:8083/movie/
```
##  Run docker image
### Get images
```shell
docker import - movie_heaven < movie_heaven.tar
docker pull mysql
docker pull redis
```
### Start containers
#### mysql
```shell
docker run --name mymysql -e MYSQL_ROOT_PASSWORD=JXZ1234@ -d -p 3306:3306  mysql
```
Enter container
```shell
sudo docker exec -it mymysql bash
```
Login
```shell
mysql -uroot -pJXZ1234@
```
Import the database
```mysql
create movie_db;
use movie_db;
/* copy everything on https://github.com/codeworm111/MovieHeaven/blob/master/SqlGeneration and paste them on the terminal after mysql> */
```
#### redis
```shell
docker run --name myredis -p 6379:6379 -d redis redis-server --appendonly yes
```
#### movieheaven
```shell
docker run -p 8083:8083 --name movieheaven  --link mymysql:emysql --link myredis:eredis -d movieheaven-image
```
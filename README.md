# MovieHeaven
##  Run docker image
### Get images
```shell
docker import - movieheaven < movie_heaven.tar
docker pull mysql
docker pull redis
```
### Start containers
#### mysql
```shell
docker run --name mymysql -e MYSQL_ROOT_PASSWORD=JXZ1234@ -d -p 3306:3306  mysql
```
##### Enter container
```shell
sudo docker exec -it mymysql bash
```
##### Login
```shell
mysql -uroot -pJXZ1234@
```
##### Import the database
copy everything on [SqlGeneration](https://github.com/codeworm111/MovieHeaven/blob/master/SqlGeneration) and paste them on the terminal after `mysql>`
#### redis
```shell
docker run --name myredis -p 6379:6379 -d redis redis-server --appendonly yes
```
#### movieheaven
```shell
docker run -p 8083:8083 --name movieheaven  --link mymysql:emysql --link myredis:eredis -d movieheaven-image
```
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
Copy SqlGeneration.sql into the container.
```shell
docker cp SqlGeneration.sql mymysql:/home/SqlGeneration.sql
```
Enter the container
```shell
sudo docker exec -it mymysql bash
```
Login mysql
```shell
mysql -uroot -pJXZ1234@
```
Import the database
```sql
source /home/SqlGeneration.sql
```
#### redis
```shell
docker run --name myredis -p 6379:6379 -d redis redis-server --appendonly yes
```
#### movieheaven
```shell
docker run -p 8083:8083 --name movieheaven  --link mymysql:emysql --link myredis:eredis -d movieheaven-image
```
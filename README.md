# Inside Code Challenge

## Solution
The code challenge solution consists of the several services run as Docker containers and communicating with each other via a Docker Compose internal network

 - **mc1** - a self-written Java/Spring Boot app implemented according to the code challenge description. Source code is located in the **mc1/** folder;
 - **mc2** - a self-written Java/Spring Boot app implemented according to the code challenge description. Source code is located in the **mc2/** folder;
 - **mc3** - a self-written Java/Spring Boot app implemented according to the code challenge description. Source code is located in the **mc3/** folder;
 - **kafka** - a [Kafka] instance for communication between **mc2** and **mc2**;
 - **zookeeper** - a [Zookeeper] instance necessary for **Kafka**;
 - **maria_db** - an instance of [Maria] database;
 - **jaeger-allinone** - an instance of [Jaeger], a monitoring tool;

## Build
Services mc1, mc2 and mc3 can be built by [Maven]. Run the following command in each service's folder:
```sh
mvn clean package
```

## Run
Run the following command to launch all services:
```sh
docker-compose up
```
or 
```sh
docker-compose up -d
```
to run Docker instances in the background. In this case one's container logs might be see by the command:
```sh
docker logs <container>
```

## Usage
### Start
To start communication between the services trigger the /start endpoint of the **mc1** microservice:
```sh
curl http://localhost:9000/mc1/start
```

### Stop
To stop communication between the services trigger the /stop endpoint of the **mc1** microservice:
```sh
curl http://localhost:9000/mc1/stop
```

### Statistics
There are several ways to get statistics on microservices' communication:
  1. **mc1** prints the amount of emitted messages and the time span to the console (```docker logs mc1```).
  2. **Jaeger** container shows nice stats on spans in UI - http://localhost:16686
  3. **MariaDB** stores data in the **_message_** table. Use whatever MySQL tool to login to the container with the following parameters: host **localhost**, port **3306**, database **invite_test_task**, user **user**, password **password**
   
   [Kafka]: <https://kafka.apache.org/>
   [Zookeeper]: <https://zookeeper.apache.org/>
   [Maria]: <https://mariadb.org/>
   [Jaeger]: <https://www.jaegertracing.io/>
   [Maven]: <https://maven.apache.org/>


## 启动流程

### 启动zookeeper
```
cd /usr/local/Celler/zookeeper/bin
./zkServer start
```

### 启动kafka
```
cd /usr/local/Celler/kafka/bin
启动kafka
./kafka-server-start /usr/local/etc/kafka/server.properties &

停止kafka
./kafka-server-stop /usr/local/etc/kafka/server.proerties &

查看所有topic
./kafka-topic --zookeeper localhost:2181 --list

查看指定topic明细
./kafka-topic --zookeeper localhost:2181  --topic TOPIC_NAME --describe

删除指定topic
./kafka-topic --delete --zookeeper localhost:2181 --topic TOPIC_NAME


```

### 启动flume
```
cd /usr/local/flume
bin/flume-ng agent -n agent -c conf/ -f conf/log-kafka.properties -Dflume.root.logger=INFO,console
```
### 启动mongodb
```

```

#### 启动redis
```

```
#### 启动Spark Streaming
```

```
#### 启动Kafka Streaming
```

```
#### 启动recommend-console
```
npm run dev
```
## 整体架构

##### 实时推荐架构
前端打分 -> 后端收集数据 -> 埋点日志 -> flume -> kafka -> kafka streaming 数据预处理 -> spark streaming 实时计算推荐 -> mongodb 数据存储

## TODO LIST

1. azkaban 调度spark离线任务/ contrab定时执行
2. spark streaming -> filnk

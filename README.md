#Kafka Commands

##zookeper start 
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

##kafka start
.\bin\windows\kafka-server-start.bat .\config\server.properties

##kafka topic creation
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic bid-stream

Spring boot app ECS Fargate deployment

Steps for creating and pushing docker image to ECR
1. create a user with admin access and download the key value.
2. configure aws cli with - aws configure - provide access id, secret id
3. aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 902231100712.dkr.ecr.ap-south-1.amazonaws.com
4. docker build -t seller-app .
5. docker tag seller-app:latest 902231100712.dkr.ecr.ap-south-1.amazonaws.com/seller-app:latest
6. docker push 902231100712.dkr.ecr.ap-south-1.amazonaws.com/seller-app:latest
7. for configuring ecr reposiroty from overwriting the image
   aws ecr put-image-tag-mutability --repository-name seller-app --image-tag-mutability IMMUTABLE --region ap-south-1
   
Steps fro creating docker image and pushing to docker hub
1. docker build -t seller-app .
2. docker tag seller-app:latest 902231100712.dkr.ecr.ap-south-1.amazonaws.com/seller-app:latest
3. docker push parveenindia90/seller-app:latest




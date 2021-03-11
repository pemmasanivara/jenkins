#!/bin/bash
echo running the script
#sudo su
#cd /home/pemmasanivara/services
echo stoping the existing services
kill $(cat ./npid.file)
kill $(cat ./cspid.file)
kill $(cat ./tppid.file)
kill $(cat ./gwpid.file)
kill $(cat ./srpid.file)
kill $(cat ./adpid.file)
kill $(cat ./uspid.file)
rm -rf *.file
echo removing the exisitng the  jar files
rm -rf *.jar
echo removing all other files
rm -rf *.txt
rm -rf  pid.file
export PATH=$PATH:/snap/bin
echo trying to load the  jar files
gsutil cp gs://jrd-beta-bucket/services/*.jar .
echo setting the test profile...
export SPRING_PROFILES_ACTIVE=test
echo starting services...
nohup java -jar ./serviceregistry-0.0.1-SNAPSHOT.jar > ./srlog.txt 2>&1 & echo $! > ./srpid.file
nohup java -jar ./gatewayservice-0.0.1-SNAPSHOT.jar > ./gwlog.txt 2>&1 & echo $! > ./gwpid.file
nohup java -jar ./therapistservice-0.0.1-SNAPSHOT.jar > ./tplog.txt 2>&1 & echo $! > ./tppid.file
nohup java -jar ./clientservice-0.0.1-SNAPSHOT.jar > ./cslog.txt 2>&1 & echo $! > ./cspid.file
nohup java -jar ./notificationservice-0.0.1-SNAPSHOT.jar > ./nlog.txt 2>&1 & echo $! > ./npid.file
nohup java -jar ./adminservice-0.0.1-SNAPSHOT.jar > ./adminlog.txt 2>&1 & echo $! > ./adpid.file
nohup java -jar ./userservice-0.0.1-SNAPSHOT.jar > ./uslog.txt 2>&1 & echo $! > ./uspid.file
echo all services are started successfully...

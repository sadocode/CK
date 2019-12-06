#!/bin/bash

SERVICE_NAME=Yonhap_Article_Crawler
PATH_TO_JAR=./crawler.jar
PID_PATH_NAME=./Yonhap_Article_Crawler.pid

case $1 in
start)
	echo "Starting $SERVICE_NAME ..."
	if [ ! -f $PID_PATH_NAME ]; then
	  if (( $# == 1 )); then
	    nohup java -jar $PATH_TO_JAR >> /dev/null &
    	  elif (( $# == 2 )); then
	    nohup java -jar $PATH_TO_JAR $2 >> /dev/null &
	  elif (( $# == 3 )); then
	    nohup java -jar $PATH_TO_JAR $2 $3 >> /dev/null &
    	  elif (( $# > 3 )); then
	    echo "There are too many arguments! only 2 input will be entered."
	    nohup java -jar $PATH_TO_JAR $2 $3 >> /dev/null &
	  fi

	  echo $! > $PID_PATH_NAME
	  echo "$SERVICE_NAME started ..."
	
  	else
	  echo "$SERVICE_NAME is already running ..."
	fi
;;
stop)
	if [ -f $PID_PATH_NAME ]; then
	 PID=$(cat $PID_PATH_NAME);
	 echo "$SERVICE_NAME stopping ..."
	 kill $PID;
	 echo "$SERVICE_NAME stopped ..."
	 rm $PID_PATH_NAME
	else
	 echo "$SERVICE_NAME is not running ..."
	fi
;;
esac

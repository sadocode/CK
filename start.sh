#!/bin/bash

SERVICE_NAME=Yonhap_Article_Crawler
PATH_TO_JAR=./crawler.jar
PID_PATH_NAME=./Yonhap_Article_Crawler.pid

TIME=300
ARTICLE_SAVE_PATH=.

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
info)
	echo "크롤링 주기 = $TIME / 기사저장위치 = $ARTICLE_SAVE_PATH"
;;
help)
	echo "--------------------------------------"
	echo "실행 방법 : ./start.sh start [option]"
	echo "실행 옵션 : 입력 X  --> 크롤링 주기 : 300, 기사 저장 위치 : ."
	echo "		: -time=xx --> 크롤링 주기 : xx초"
	echo "		: -path=yy --> 기사저장위치 : yy"
	echo "종료 방법 : ./start.sh stop"
	echo "프로그램 정보 확인 : ./start.sh info"
	echo "--------------------------------------"
;;	
esac

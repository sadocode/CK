# Kafka 예제

## 설명
* 카프카에 대한 예제로 만들어진 프로그램이다.
* 이 프로그램은 연합뉴스의 최신기사를 일정한 주기로 백그라운드에서 크롤링해주는 기능을 제공한다. 크롤링한 기사들을 프로듀서가 카프카에 게시하고, 컨슈머가 구독한다.
* 한계 1 : 대용량 데이터를 전송하는 것이 아니므로 카프카의 성능 측정에 제한이 있다.
* 한계 2 : 로컬에서만 이루어진다.

/br

## 사용법
1. cp ./Crawler/target/Crawler-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./crawler.jar 명령어 입력
2. ./start.sh start 입력 -> 백그라운드에서 크롤링 프로그램 작동됨
3. ./start.sh stop 입력 -> 크롤링 프로그램 종료

/br

## 현재 버전
* [YCKE-1]

/br

## 버전
* [YCKE-1] : 백그라운드에서 연합뉴스 최신기사의 크롤링이 실행됨. 

app-jar:
	mvn package && java -Xmx256m -Xmx512m -jar  target/stock-app-2-0.0.1-SNAPSHOT.jar

app-docker:
	mvn clean verify && docker-compose up -d

build:
	docker-compose build --no-cache

rebuild:
	docker-compose build
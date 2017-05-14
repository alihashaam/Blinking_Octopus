Blinking_Octopus

FOPJ, SoSe 17

Start Blinktopus:
1. Download Blinking_Octopus
2. Go to Blinking_Octopus/blinktopus
3. To start:
  java -jar target/blinktopus-0.0.1-SNAPSHOT.jar server config.yml
4. Access:
  localhost:8080/log <- show all entries in the log
  localhost:8080/sv <- show all SVs //not implemented yet
  http://localhost:8080/query?SVid=log&type=Order&attr=totalPrice&lower=111.20&higher=4000.0&create=false
  localhost:8081/ <- metrics
5. If you change any classes:
   mvn package

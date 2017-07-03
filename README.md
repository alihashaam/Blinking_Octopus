Blinking_Octopus

FOPJ, SoSe 17

Dropwizard application. Requires Maven.

 Start:

1. mvn clean

2. mvn install

3. in blinktopus/ java -jar target/blinktopus-0.0.1-SNAPSHOT.jar server config.yml

Functionality:

1. View all entries in log: localhost:8080/log

2. View all SVs: localhost:8080/sv/all

3. Delete all SVs: localhost:8080/sv/clear

4. Query: http://localhost:8080/query?SVid=aqp&type=aqp&table=orders&attr=totalPrice&lower=100&higher=5000&create=false&distinct=false 

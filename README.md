## Download the driver if you haven't
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar

# Compile
javac StudentRecordManager.java

# Run with driver in classpath
java -cp .:mysql-connector-j-8.2.0.jar StudentRecordManager endsem_sse

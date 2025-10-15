# Use OpenJDK base image
FROM openjdk:17-slim

# Set working directory
WORKDIR /app

# Install MySQL client (for testing)
RUN apt-get update && apt-get install -y default-mysql-client wget && rm -rf /var/lib/apt/lists/*

# Download MySQL JDBC Driver
RUN wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar

# Copy Java application
COPY StudentRecordManager.java .

# Compile the Java application
RUN javac StudentRecordManager.java

# Expose port (not really needed for CLI app, but good practice)
EXPOSE 8080

# Run the application
CMD ["java", "-cp", ".:mysql-connector-j-8.2.0.jar", "StudentRecordManager"]

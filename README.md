# Student Record Manager - Secure DevOps Lab

**Lab Documentation Reference:** [ch.en.u4cys22043_sse_lab.pdf](./ch.en.u4cys22043_sse_lab.pdf)

---

## Project Overview
**Student Record Manager (SRM)** is a Java + MySQL CLI application for managing student records securely, including login authentication and CRUD operations. This lab demonstrates a full secure DevOps lifecycle: SRS, DFD, threat analysis, attack trees, test cases, code refactoring, Dockerization, and Kubernetes deployment.

---

## 1. Requirements & Planning
- Mini SRS, functional & non-functional requirements, constraints, acceptance criteria
- Agile planning: user stories & sprint plan
- Reference: see PDF documentation

---

## 2. Design
- Level-0 & Level-1 DFD diagrams for User ↔ SRM ↔ Database interactions
- Diagram scripts (Mermaid / Draw.io) included in PDF
- Focus: data flow, authentication, CRUD operations

---

## 3. Threat Analysis & Attack Trees
- STRIDE analysis at two trust boundaries:
  1. TB1: User ↔ SRM  
  2. TB2: SRM ↔ Database  
- Sample attack tree for bypassing login included
- Severity scores provided in PDF

---

## 4. Test Cases
- Functional, security, and negative test cases included:
  - Login, add/update/delete student records
  - SQL Injection & XSS prevention
  - Role-based access control and brute force handling
- Details in PDF

---

## 5. Code Refactoring
- Focus on **input validation** and **SQL injection prevention**
- Sample before/after code snippets included in PDF

---

## 6. Docker Deployment
- Dockerfile for Java + MySQL client
- Multi-container setup: Student app + MySQL
- Steps: build image, create network, run MySQL, run app, verify deployment
- Screenshots of `docker ps` and container logs in PDF

---

## 7. Kubernetes Deployment
- Minikube deployment with Deployment + Service YAML
- Steps: start minikube, build image inside minikube, apply YAML, verify pods/services, run app inside pod
- Screenshots and verification steps in PDF

---

## 8. Conclusion
This lab demonstrates the complete secure DevOps lifecycle for SRM:
- Requirements gathering, design, and threat analysis
- Security-focused code refactoring
- Containerization and Kubernetes deployment
- Functional & security testing  
Full details, diagrams, and screenshots are in [ch.en.u4cys22043_sse_lab.pdf](./ch.en.u4cys22043_sse_lab.pdf)



## Download the driver if you haven't
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar

# Compile
javac StudentRecordManager.java

# Run with driver in classpath
java -cp .:mysql-connector-j-8.2.0.jar StudentRecordManager endsem_sse

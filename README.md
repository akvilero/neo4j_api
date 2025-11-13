# Neo4j Flight Management API
This project uses Spring Boot with Neo4j Database to manage and query flight connections between cities and airports.
It was originally created during my **3rd year of Computer Science studies** as a part of a university assignment.

## Technologies Used
- **Java 21**
- **Spring Boot**
- **Neo4j**
- **Maven**
- **Docker**

## Setup & Run Instrutions
1. Run Neo4j using Docker
```bash
docker run --name neo4j -p 7474:7474 -p 7687:7687 -e NEO4J_AUTH=neo4j/testpassword -d neo4j
```
2. Build and run API

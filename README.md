# RecycleFlow

## Overview

RecycleFlow is a Java SpringBoot-based API developed as part of a graduate program assessment. The application is designed to assist with sorting waste and categorizing it for educational purposes. The API provides endpoints for waste sorting, categorization, and retrieving relevant educational data. It is designed to be consumed by the mobile application developed by **enviro365**.

## Features

- Categorize different types of waste (e.g., paper, plastic, metal, organic).
- Provide educational resources about proper waste sorting.
- RESTful API for easy integration with other applications.
- Simple and efficient design for waste management and sorting.
- Support for mobile application consumption (enviro365 mobile app).

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (for local development)
- RESTful API design
- Maven for dependency management

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.8 or later
- IDE (e.g., VSCode, IntelliJ IDEA, Eclipse)
- Postman or similar tool to test API endpoints

### Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/kingslytshepiso/recycleflow.git

   ```

2. Navigate to the project directory:
   ```bash
   cd RecycleFlow
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Database

RecycleFlow uses an H2 database in development for storing waste categories and related data.

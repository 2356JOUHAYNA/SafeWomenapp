# SafeWomen: Mobile Safety Companion for Women

SafeWomen is a mobile and web application dedicated to enhancing women's safety through real-time SOS alerts, geolocation, audio capture, and smart chatbot assistance. Built using Android SDK, Spring Boot, and Flask AI microservices, SafeWomen is an open-source solution designed for awareness, protection, and research in gender-based safety technologies.

## Table of Contents

- [Architecture](#architecture)
- [Docker Deployment](#docker-deployment)
- [Features](#features)
- [Technical Stack](#technical-stack)
- [Backend Structure](#backend-structure)
- [Getting Started](#getting-started)
- [Screenshots](#screenshots)
- [Contributors](#contributors)
- [Contact](#contact)
- [License](#license)

## Architecture

SafeWomen is composed of:

- Android Mobile App (Java): SOS triggering, location tracking, audio capture
- Spring Boot Backend: Authentication, API management, database
- Flask Microservice: AI-powered chatbot assistant
- PostgreSQL DB: Persistent user, alert, and safe zone data
- Dockerized Services: For easy deployment and scalability
![WhatsApp Image 2025-05-26 à 23 07 44_bdae4278](https://github.com/user-attachments/assets/9e7f709d-92da-489b-a19c-35d3a5225ac1)



## Docker Deployment

Backend (docker-compose.yml):

Includes:
- PostgreSQL
- Spring Boot
- phpPgAdmin (optional)

```bash
cd backend
docker-compose up -d
```

Access services:

API: http://localhost:8082  
DB: localhost:5432

## Features

- SOS Alerts: Instant location and audio recording in emergencies  
- Safe Zone Map: Locate safe places nearby  
- SafeChat: AI-powered chatbot for guidance  
- Audio Recorder: Background audio capture on alert  
- User Dashboard: View and track your alert history  

## Technical Stack

- Frontend Mobile: Android SDK, Retrofit, ZXing  
- Backend: Spring Boot, Spring Security, PostgreSQL  
- AI Services: Flask, Scikit-learn  
- DevOps: Docker, GitHub, IntelliJ, Android Studio  

## Backend Structure

java/
└── com.example.demo
    ├── config         
    ├── controller      
    ├── dto             
    ├── model          
    ├── repository      
    └── service         




## Getting Started

### Prerequisites

- Java 22  
- PostgreSQL  
- Android Studio  

### Setup

```bash
git clone https://github.com/2356JOUHAYNA/SafeWomenapp.git
cd SafeWomenapp/backend
mvn spring-boot:run
```

### Android App

Open in Android Studio  
Set API URL in Constants.java   
Build and run  

 
## Contributors

- Jouhayna Koubichate  
- Aliae Chahbar  

## Contact

- koubichatej@gmail.com  


## License

This project is licensed under the MIT License - see the LICENSE file for details.

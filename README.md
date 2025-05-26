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

## Docker Deployment

Backend (docker-compose.yml):

Includes:
- PostgreSQL
- Spring Boot
- phpPgAdmin (optional)

```bash
cd backend
docker-compose up -d

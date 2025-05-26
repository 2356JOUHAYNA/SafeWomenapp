SafeWomen: Mobile Safety Companion for Women 
SafeWomen est une application mobile et web conçue pour améliorer la sécurité des femmes à travers des alertes SOS géolocalisées, l'enregistrement audio instantané, un chatbot intelligent et une cartographie des lieux sûrs. Développée avec Android SDK, Spring Boot, et Flask AI, SafeWomen est une solution open source destinée à l’éducation, à la recherche, et à la protection citoyenne.

Table des matières
Architecture

Docker Deployment

Fonctionnalités

Stack Technique

Structure Backend

Getting Started

Captures d’écran

Contributeurs

Contact

Licence

Architecture
SafeWomen est composé de :

Application mobile Android (Java) : Déclenchement SOS, audio, localisation, chatbot.

Backend Spring Boot : Authentification, API REST, base de données.

Microservice Flask : IA pour le chatbot.

Base de données PostgreSQL : Données utilisateurs, alertes et lieux sûrs.

Services Dockerisés : Déploiement rapide et évolutif.

Docker Deployment
Backend (docker-compose.yml) :

PostgreSQL

Spring Boot API

phpPgAdmin (optionnel)

bash
Copy
Edit
cd backend
docker-compose up -d
Services accessibles :

API : http://localhost:8082

DB : localhost:5432

Fonctionnalités
Déclenchement SOS : Enregistrement audio + géolocalisation en un clic.

Cartographie des lieux sûrs : Localise les établissements sécurisés à proximité.

SafeChat : Chatbot intelligent basé sur Flask et données contextuelles.

Enregistrement intelligent : Audio + envoi automatique au serveur.

Profil utilisateur : Authentification et suivi d’alertes personnelles.

Stack Technique
Frontend Mobile : Android SDK, Retrofit, ZXing

Backend : Spring Boot, Spring Security, PostgreSQL

IA & Services : Flask, Scikit-learn

Outils : Docker, GitHub, Postman, IntelliJ, Android Studio

Structure Backend
bash
Copy
Edit
├── controller         # Contrôleurs REST
├── services           # Logique métier
├── repository         # Accès à la BDD
├── model              # Entités JPA
├── config             # Sécurité JWT & config Spring
├── uploads/audio      # Enregistrements SOS
└── resources
Getting Started
Pré-requis :
Java 22

PostgreSQL

Android Studio

Node.js (si web prévu)

Lancer le backend :
bash
Copy
Edit
git clone https://github.com/2356JOUHAYNA/SafeWomenapp.git
cd SafeWomenapp/backend
mvn spring-boot:run
Application Android :
Ouvrir le projet dans Android Studio

Modifier Constants.java pour y mettre l’URL de l’API (http://10.0.2.2:8082)

Build & Run

Captures d’écran
Placez les images dans le dossier /screenshots :

Écran SOS

SafeMap

Profil

Historique des alertes

Chatbot

Contributeurs

Jouhayna Koubichate

Aliae Chahbar
Contact
khaoulaaguabdre@gmail.com
safewomen.contact@gmail.com

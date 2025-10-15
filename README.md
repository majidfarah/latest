# STEM Learning Guide Platform

A full-stack platform that helps students discover STEM subjects, generate personalized learning paths, and track their progress. The system is composed of a Spring Boot backend, a FastAPI AI microservice, a simple frontend, and a PostgreSQL database.

## Project Structure

```
STEM-Guide/
├── backend-java/        # Spring Boot API
├── ai-python/           # FastAPI recommendation microservice
├── frontend/            # HTML/CSS/JS interface
├── database/            # PostgreSQL setup notes
└── README.md
```

## Services

### Java Backend (Spring Boot)

* Exposes REST APIs for subjects, learning resources, user profiles, and progress tracking.
* Integrates with the AI microservice to generate personalized learning paths.
* Seeds sample STEM subjects and resources on startup.
* Configuration located at `backend-java/src/main/resources/application.yml`.

Run locally:

```bash
cd backend-java
./gradlew bootRun
```

Run the backend test suite (uses an in-memory H2 database and stubs the AI service):

```bash
cd backend-java
./gradlew test
```

### AI Recommendation Service (FastAPI)

* Accepts subject, level, and goals to generate curated resource recommendations and milestones.

```bash
cd ai-python
pip install -r requirements.txt
uvicorn app:app --reload --port 8000
```

### Frontend

Simple static HTML/CSS/JS client that consumes the backend APIs. Serve it with any static file server:

```bash
cd frontend
python -m http.server 3000
```

Update the `backendBaseUrl` in `frontend/main.js` if hosting the backend elsewhere.

### Database

Create a PostgreSQL database using the instructions in `database/README.md`. The backend expects the database to be accessible at `jdbc:postgresql://localhost:5432/stem_guide` with credentials `stem_guide/stem_guide`.

## API Overview

* `GET /api/v1/subjects` – list STEM subjects
* `POST /api/v1/subjects` – create subject
* `GET /api/v1/resources/subject/{subjectId}` – list resources for a subject
* `POST /api/v1/resources` – create a new resource
* `POST /api/v1/recommendations` – request personalized learning path from AI service
* `GET /api/v1/progress/user/{userId}` – list learning progress for a user
* `POST /api/v1/progress` – create progress entry

Use the endpoints to build richer clients or integrate with other systems.

## Next Steps

* Add authentication (e.g., JWT) and fine-grained authorization.
* Replace seed data with managed migrations and admin tooling.
* Deploy the services with container orchestration (Docker Compose, Kubernetes).

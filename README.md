# technical-writing-platform-api

Technical Writing Platform의 백엔드 API입니다. 노션형 문서 워크스페이스, 기술 문서 라이브러리, 리뷰 이슈, 발행 이벤트, 문서 품질 목표를 관리합니다.

## Stack

- Kotlin 2.2.x
- JVM 21
- Spring Boot 3.5.x
- Gradle multi-module
- Spring MVC, Spring Data JPA, Validation
- PostgreSQL, Flyway
- Spring Security OAuth2 Resource Server, Keycloak 연동 준비

## Module Layout

```text
common      # shared entity base and current-user abstraction
workspace   # documentation workspace and owning team API
document    # technical document metadata, status, freshness API
review      # review issues, severity, status, quality debt API
event       # draft, review, publish, archive event API
target      # coverage and review SLA target API
ops         # documentation operations summary API
app         # Spring Boot bootstrap, security, Flyway, tests
```

## Run

```bash
./gradlew test
./gradlew :app:bootRun
```

기본 개발 모드는 `TECHWRITE_SECURITY_ENABLED=false`라서 로컬에서 바로 API를 호출할 수 있습니다. Keycloak을 붙일 때는 아래처럼 켭니다.

```bash
TECHWRITE_SECURITY_ENABLED=true \
KEYCLOAK_ISSUER_URI=http://localhost:8081/realms/techwrite \
DB_URL=jdbc:postgresql://localhost:5432/techwrite \
DB_USERNAME=techwrite \
DB_PASSWORD=change-me \
./gradlew :app:bootRun
```

## API Surface

- `GET /api/v1/ops/summary?month=2026-07`
- `GET/POST /api/v1/workspaces`
- `GET/POST /api/v1/documents`
- `GET/POST /api/v1/reviews`
- `GET/POST /api/v1/events`
- `GET/POST /api/v1/targets`

## Repository Positioning

추천 설명:

> Backend API for a technical writing platform with workspaces, document metadata, review issues, publishing events, and documentation ops metrics.

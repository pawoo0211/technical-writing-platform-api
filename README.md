# exit-asset-api

EXIT 개인 자산 관리 시스템의 백엔드 API입니다. Notion MVP 기준으로 자산, 부채, 거래, 목표 자산을 기록하고 순자산/현금흐름/Exit 목표 달성률을 계산합니다.

## Stack

- Java 21
- Spring Boot 3.5.x
- Gradle multi-module
- Spring MVC, Spring Data JPA, Validation
- PostgreSQL, Flyway
- Spring Security OAuth2 Resource Server, Keycloak 연동 준비

## Module Layout

```text
common      # shared entity base and current-user abstraction
account     # asset account model, repository, REST API
asset       # asset item model, repository, REST API
liability   # liability model, repository, REST API
cashflow    # transaction and monthly cash-flow records
goal        # Exit goal model, repository, REST API
portfolio   # cross-domain portfolio summary and simulation API
app         # Spring Boot bootstrap, security, Flyway, tests
```

## Run

```bash
./gradlew test
./gradlew :app:bootRun
```

기본 개발 모드는 `EXIT_SECURITY_ENABLED=false`라서 로컬에서 바로 API를 호출할 수 있습니다. Keycloak을 붙일 때는 아래처럼 켭니다.

```bash
EXIT_SECURITY_ENABLED=true \
KEYCLOAK_ISSUER_URI=http://localhost:8081/realms/exit \
DB_URL=jdbc:postgresql://localhost:5432/exit_asset \
DB_USERNAME=exit_asset \
DB_PASSWORD=change-me \
./gradlew :app:bootRun
```

## API

- `GET /api/v1/portfolio/summary?month=2026-06`
- `GET/POST /api/v1/accounts`
- `GET/POST /api/v1/assets`
- `GET/POST /api/v1/liabilities`
- `GET/POST /api/v1/transactions`
- `GET/POST /api/v1/goals`
Exit asset management system backend API

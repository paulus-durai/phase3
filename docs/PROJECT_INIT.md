Project Initialization Document
==============================

Overview
--------
This document describes the proposed multi-module Maven layout, dependencies, conventions, and steps to convert the existing single-module project into a modular architecture suitable for production-grade Spring Boot 3.x + Java 17.

Goals
-----
- Split by responsibility: `api`, `service`, `repository`, `domain`, `security`, `integration-test`.
- Enforce separation of concerns and easier CI/CD, testing, and independent module versioning.
- Use a parent POM for dependency management and plugin configuration.

Proposed Module Layout
-----------------------
- `pom.xml` (parent) – packaging `pom`, centralizes versions and modules list.
- `api/` – REST controllers, DTOs, request/response models, OpenAPI config.
- `service/` – Service interfaces and `impl` packages. Transactional business logic.
- `repository/` – Spring Data JPA repositories and DB-related configuration.
- `domain/` – JPA entities and mapping configurations.
- `security/` – JWT provider, filters, security config, `UserDetails` implementations.
- `integration-test/` – end-to-end integration tests and test utilities.

Dependency & Technology Versions
-------------------------------
- Java: 17
- Spring Boot: 3.1.x (recommend latest 3.x)
- jjwt: use io.jsonwebtoken:jjwt-api + jjwt-impl + jjwt-jackson (or use Nimbus JOSE)
- H2: latest 2.x
- Lombok: latest
- JUnit 5, Mockito
- JaCoCo for coverage

Parent POM (template)
---------------------
A parent POM will centralize dependencyManagement, pluginManagement (compiler, surefire, jacoco), and define modules. See `docs/parent-pom-template.xml` for a ready template.

Scaffolding Created
-------------------
The following skeletons were added to the repo (empty modules with pom templates and READMEs):
- `api/`
- `service/`
- `repository/`
- `domain/`
- `security/`
- `integration-test/`

Migration Strategy (step-by-step)
--------------------------------
1. Create parent POM (update root `pom.xml` to use packaging `pom`).
2. Create module `core` (or reuse existing code) and move current `src/` into `core/src/` (or split into `domain`, `repository`, `service`, `api`, `security`).
3. Update module POMs to reference parent and align versions.
4. Run `mvn -pl :<module-artifactId> test` per module during migration.
5. Add CI pipeline to run `mvn -T 1C clean verify` and JaCoCo coverage report aggregation.

Commands (PowerShell)
---------------------
Run project locally (current single-module):
```powershell
.\mvnw clean package -DskipTests
.\mvnw spring-boot:run
```

To create parent POM and build modules (after refactor):
```powershell
.\mvnw -N archetype:generate -DgroupId=com.abc.telecom -DartifactId=abc-telecom-postpaid-billing -DarchetypeArtifactId=maven-archetype-quickstart
```

Notes & Next Steps
------------------
- I created module skeletons and template POMs in the repo so we can iterate safely. I did not move sources yet to avoid accidental breakage. When you confirm, I will perform the planned refactor to move code into modules and wire the parent `pom.xml`.

Acceptance Criteria for Phase 1
-------------------------------
- `docs/PROJECT_INIT.md` exists (this file).
- Module skeletons with `pom.xml` templates exist.
- Parent POM template provided in `docs/parent-pom-template.xml`.

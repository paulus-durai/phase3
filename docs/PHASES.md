# Project Phases and Status (Postpaid Billing Backend)

This document summarizes the Phase-by-Phase plan for the Postpaid Billing Backend, current status, acceptance criteria, and immediate next steps.

Phase 0 — DB & Sample Data
- Goal: Provide a development DB schema and seed data so services can run locally.
- Deliverables: `src/main/resources/db/migration/V1__init.sql`, H2-enabled `application.yml`.
- Acceptance: App boots against H2; schema tables exist; demo customers, services, usage, invoices, payments visible via H2 console.
- Status: Completed
- Notes / Next: Users/roles seeded at startup (ApplicationRunner) to avoid storing raw password hashes in migration.

Phase 1 — Project Init Docs + Module Skeletons
- Goal: Create documentation and multi-module scaffold (parent POM template and module READMEs).
- Deliverables: `docs/PROJECT_INIT.md`, `docs/parent-pom-template.xml`, skeleton folders for `api/`, `service/`, `repository/`, `domain/`, `security/`, `integration-test/`.
- Acceptance: README and parent POM explain module responsibilities and migration path.
- Status: Completed

Phase 2 — Code Generation (Entities, DTOs, Repos, Services, Controllers, Security)
- Goal: Implement the core Java code: domain entities, repositories, service layer, controllers, JWT-based auth, RBAC, and DTO mappings.
- Deliverables: Java sources under `src/main/java/...` (entities, repositories, services, controllers, security classes), DTOs, mappers.
- Acceptance: REST endpoints for Customers, Accounts, Services, UsageRecords, Invoices, Payments exist and are protected with JWT; admin/customer roles enforced; basic invoice generation flows scaffolded.
 - Status: Completed
 - Current progress: Core domain entities, repositories, services, controllers, and JWT-based authentication implemented. Controllers were converted to use DTOs with `ModelMapper`, `Customer` now links to `User` via `user_id` FK, `SecurityService` ownership checks were updated to prefer the FK, and `BillingService` was hardened with config-driven rates and account linking. DB migration (`V1__init.sql`) was updated to align with entities.
 - Next steps: Phase 6 (tests) — add unit and integration tests to validate ownership, billing calculations, and endpoint security. Phase 7 will run coverage and reach the target.
 - Screenshots / Artifacts: `docs/screenshots/phase2-01-dto-mapping.png` (DTO mapping result),
     `docs/screenshots/phase2-02-auth-seed.png` (H2 console showing seeded users/roles),
     `docs/screenshots/phase2-03-api-swagger.png` (Swagger / OpenAPI sample endpoints screenshot).

Phase 2 - Current Artifact Notes
 - Location: `src/main/java/com/abc/telecom/billing` (entities, controllers, services, security, DTOs)
 - Notes: Controllers were converted to use DTOs via `ModelMapper`. `AbcTelecomBillingApplication` seeds roles/users and links the demo `customer1` user to its `Customer` record. `docs/migration_USER_TO_CUSTOMER.md` documents the FK change and backfill guidance.


Phase 3 — Automated Code Review
- Goal: Run static analysis and linting (SpotBugs, PMD, Checkstyle) and collect findings.
- Deliverables: Findings report with severity, file locations, and suggested fixes.
- Acceptance: Report created and actionable.
- Status: Not-started

Phase 4 — Apply Fixes from Code Review
- Goal: Implement high-priority fixes flagged in Phase 3.
- Deliverables: PR-ready code changes and a Fixes Report.
- Acceptance: No high-severity static issues remain.
- Status: Not-started

Phase 5 — Functional Specification Document
- Goal: Produce a concise FSD describing flows for billing, payments, invoice lifecycle, and security.
- Deliverables: `docs/FUNCTIONAL_SPEC.md` with ASCII flow diagrams for key user stories.
- Acceptance: FSD reviewed and approved by the team.
- Status: Not-started

Phase 6 — Test Setup & Generation
- Goal: Configure testing toolchain (JUnit5, Mockito, Spring Boot Test) and generate unit/integration tests.
- Deliverables: Test classes for controllers/services/repositories/security; test utilities and test profiles.
- Acceptance: Tests run locally and CI; majority of critical paths covered.
- Status: Not-started

Phase 7 — Run Tests & Coverage
- Goal: Execute tests, fix failures, and reach >80% coverage (JaCoCo report).
- Deliverables: `target/site/jacoco/index.html`, coverage summary table, failing tests fixed.
- Acceptance: ≥80% line coverage for core modules or a documented mitigation plan.
- Status: Not-started

Phase 8 — Functional Testcases
- Goal: Author manual/functional testcases for US1–US6 (payloads, expected responses).
- Deliverables: `docs/functional-testcases.md` with sample curl/HTTP requests.
- Acceptance: Testcases executable and reproducible by QA.
- Status: Not-started

Phase 9 — Test Run Report
- Goal: Produce test run summaries and screenshots demonstrating passing tests and coverage.
- Deliverables: Test run report with screenshots, logs, and analysis.
- Acceptance: Artifacts attached and accessible.
- Status: Not-started

Phase 10 — High-Value Prompt Guide
- Goal: Capture and improve prompts used to generate code, tests, and docs for reproducibility.
- Deliverables: `docs/prompt-guide.md` with curated prompts and improved versions.
- Acceptance: Prompts produce consistent useful outputs.
- Status: Not-started

Phase 11 — API Validation with JWT
- Goal: Produce demo artifacts that log-in, obtain a JWT, and exercise endpoints demonstrating RBAC behavior.
- Deliverables: `docs/demo-requests.md` with example `curl` commands and expected outputs.
- Acceptance: Demo steps produce expected role-restricted responses.
- Status: Not-started

- Screenshots / Artifacts: `docs/screenshots/phase11-01-login-jwt.png` (login -> JWT),
  `docs/screenshots/phase11-02-curl-admin-access.png`, `docs/screenshots/phase11-03-curl-customer-denied.png`.

----

Immediate next action I will take (with your confirmation):
- Implement DTO classes and mappers (MapStruct) then convert controllers to use DTOs and add ownership checks for customer-limited access. After that, I will scaffold unit tests for the modified controllers.

If you want me to proceed now, reply: **Proceed with DTOs and mappers**. If you prefer a different priority, tell me which Phase to start next.

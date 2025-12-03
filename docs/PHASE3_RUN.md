**Phase 3 — Automated Code Review: Run & Report**

- **Purpose:** Run Checkstyle, PMD and SpotBugs to produce machine-readable and human-friendly reports; use them to prioritize fixes.
- **Where:** The project already contains plugin entries in `pom.xml` and a minimal `config/checkstyle/checkstyle.xml` ruleset.

Local run (recommended):
```powershell
cd "c:\elecom Postpaid Billing System\abc-telecom-postpaid-billing"
.\mvnw.cmd -DskipTests verify
```

If `mvnw` fails because Maven is not available in the environment, run with a system Maven installation:
```powershell
cd "c:\elecom Postpaid Billing System\abc-telecom-postpaid-billing"
mvn -DskipTests verify
```

What the run produces (paths):
- Checkstyle HTML: `target/site/checkstyle.html`
- PMD HTML: `target/site/pmd.html`
- SpotBugs XML: `target/spotbugsXml.xml` (and HTML under `target/spotbugs` if configured)

If you prefer not to run locally, CI is configured: push to `main` (or open a PR) or run the workflow manually via Actions -> "Static Analysis" -> Run workflow. The workflow uploads the above reports as build artifacts.

What I will do once you provide the reports (or CI artifacts):
- Parse the reports and create a prioritized findings report (high/medium/low) with exact file/line locations.
- For high/medium issues, propose and apply minimal, safe fixes (small code changes and tests if needed).
- For large structural issues, provide a remediation plan and suggested milestones.

If you'd like, I can also:
- Add a `sonar-project.properties` and help wire SonarCloud or SonarQube for continuous quality gates.
- Add a GitHub Action that fails PRs on high-severity issues (requires configuring thresholds).

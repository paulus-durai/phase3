Phase 3: Static Analysis Run

This folder contains helper instructions to run the static analysis locally and package the artifacts for review.

Steps (PowerShell):

1. From the repo root run the helper script:

```powershell
PowerShell -ExecutionPolicy Bypass -File tools\run-phase3.ps1
```

2. The script runs `mvn -DskipTests verify` and creates `build.log` and `phase3-artifacts.zip` containing whatever reports were produced (Checkstyle, PMD, SpotBugs).

3. Upload `phase3-artifacts.zip` here (or share the GitHub Actions run URL) so the reviewer can parse and finalize Phase 3.

Notes:
- If `mvn` is not on your PATH, run the Maven wrapper instead: `./mvnw -DskipTests verify` on Unix or `mvnw.cmd -DskipTests verify` on Windows.
- If files are missing, open `build.log` to inspect plugin failures.

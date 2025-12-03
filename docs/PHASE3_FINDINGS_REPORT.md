Phase 3 — Automated Code Review Findings Report

Overview
- Date: [YYYY-MM-DD]
- Tools used: SpotBugs, PMD, Checkstyle (Maven plugins)
- Commands run:

```powershell
cd "c:\elecom Postpaid Billing System\abc-telecom-postpaid-billing"
# run all checks and generate reports
.\mvnw.cmd verify
# or with installed Maven
mvn verify
```

Report structure
- Summary: high-level counts and severity breakdown
- SpotBugs findings: list (file:line) + description + priority
- PMD findings: list (rule, file:line) + description
- Checkstyle findings: list (rule, file:line)
- Recommendations: actionable items grouped by priority (Critical, High, Medium, Low)
- Follow-up tasks: suggested fixes and owners

How to gather the raw reports
- SpotBugs XML: `target/spotbugs/spotbugsXml.xml` (or `target/spotbugs` folder)
- PMD reports: `target/site/pmd.html` and `target/pmd.xml`
- Checkstyle report: `target/site/checkstyle.html` and `target/checkstyle-result.xml`

Template entries (example)

### Summary
- SpotBugs: 3 warnings (1 high, 2 medium)
- PMD: 5 issues (2 high, 3 medium)
- Checkstyle: 12 style warnings

### SpotBugs
- com.abc.telecom.billing.service.BillingService:generateInvoiceForCustomer: Null pointer possible when `customer` is null (Priority: High)
  - Recommendation: add null-check and throw informative exception.

### PMD
- com.abc.telecom.billing.controller.CustomerController: Avoid duplicated code in mapping results (Rule: Duplicate Code)
  - Recommendation: extract a helper mapper method.

### Checkstyle
- com.abc.telecom.billing.entity.Customer: Missing Javadoc (Rule: JavadocType)
  - Recommendation: add class and method Javadoc where public API exists.

---

Next steps
- Run the checks locally using `mvn verify` and attach generated XML/HTML reports to this document.
- Prioritize and fix Critical/High issues first, then re-run checks.


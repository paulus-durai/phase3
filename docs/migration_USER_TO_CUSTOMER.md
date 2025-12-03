Linking Users to Customers (DB + App)

Summary
- Adds an explicit foreign key relationship from `customers.user_id` -> `users.id`.
- Updates application seed logic to associate a seeded `customer1` user with the created `Customer` record.
- Updates ownership checks to use the FK when available, falling back to previous heuristics.

Files changed
- `src/main/resources/db/migration/V1__init.sql` — align `users` table `id` column, add `email` to `customers`, and reference users(id) in FK.
- `src/main/java/com/abc/telecom/billing/entity/Customer.java` — add `@ManyToOne` `user` field mapped to `user_id`, and map column names used by migration.
- `src/main/java/com/abc/telecom/billing/AbcTelecomBillingApplication.java` — set the `user` on the seeded `Customer` before saving.
- `src/main/java/com/abc/telecom/billing/security/SecurityService.java` — ownership checks now prefer the FK link.

Migration / Deployment notes
- For new deployments (fresh DB): Flyway will apply the updated `V1__init.sql` and the application runner will create roles/users and link the demo customer.
- For existing databases with data already present: a manual migration/backfill is required. Suggested steps:
  1. Add `user_id` column to `customers` (nullable) if missing and create FK constraint.
  2. For each customer, determine the owning user (by email or other matching rule) and update `customers.user_id` to point at the correct `users.id`.
  3. After backfill, you may add a NOT NULL constraint on `customers.user_id` if appropriate.

Backfill example (SQL):

-- add nullable column
ALTER TABLE customers ADD COLUMN user_id BIGINT NULL;

-- example backfill using email
UPDATE customers c
SET user_id = (SELECT u.id FROM users u WHERE LOWER(u.email) = LOWER(c.email) LIMIT 1)
WHERE c.email IS NOT NULL;

-- add FK
ALTER TABLE customers ADD CONSTRAINT fk_customers_user FOREIGN KEY (user_id) REFERENCES users(id);

Testing
- Start application locally and verify:
  - `customer1` user exists and has a corresponding `customers` row with `user_id` set.
  - Calling `GET /api/customers/{id}` as `customer1` returns the customer details (ownership allowed).
  - Admin user can access any customer.

Notes
- The FK-based ownership is deterministic and recommended for production. If you have multiple users per customer or different ownership models, adjust the relationship accordingly (e.g., join table or separate `account` entity).

API module
==========

Purpose
-------
Houses REST controllers and DTOs. Depends on `service` and `domain`.

Notes
-----
Keep controllers thin — orchestrate via service layer. Only map DTOs here.

DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'appuser') THEN
      CREATE USER appuser WITH PASSWORD 'apppass';
END IF;
END
$$;

GRANT ALL PRIVILEGES ON DATABASE userdb TO appuser;
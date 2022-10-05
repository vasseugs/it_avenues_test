CREATE OR REPLACE FUNCTION public.truncate_tables() RETURNS void AS $$
DECLARE r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
            EXECUTE 'DELETE FROM ' || quote_ident(r.tablename) || '';
        END LOOP;
END;
$$ LANGUAGE plpgsql;
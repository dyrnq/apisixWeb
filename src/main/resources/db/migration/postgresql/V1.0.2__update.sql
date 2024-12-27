ALTER TABLE public.inst
    ALTER COLUMN agent_url SET DATA TYPE varchar(256),
    ALTER COLUMN agent_api_key SET DATA TYPE varchar(256),
    ALTER COLUMN name SET DATA TYPE varchar(256),
    ALTER COLUMN url SET DATA TYPE varchar(256),
    ALTER COLUMN api_key SET DATA TYPE varchar(256);
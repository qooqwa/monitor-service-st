CREATE TABLE IF NOT EXISTS agent_registry (
id SERIAL PRIMARY KEY,
snapshot_num INTEGER,
snapshot_open_dt TIMESTAMP WITH TIME ZONE,
snapshot_close_dt TIMESTAMP WITH TIME ZONE,
update_num INTEGER,
agent_list JSONB
);

CREATE TABLE IF NOT EXISTS agent_release (
   agent_type VARCHAR(255) NOT NULL,
   version VARCHAR(50) NOT NULL,
   release_note TEXT,
   storage_id UUID NOT NULL PRIMARY KEY,
   def_dependency_tags JSONB
);

CREATE TABLE IF NOT EXISTS agent (
    agent_nick VARCHAR(255) NOT NULL,
    agent_id UUID NOT NULL PRIMARY KEY,
    group_nick VARCHAR(255),
    agent_note TEXT,
    dependency_tags JSONB,
    plugin_list JSONB
    );

    CREATE TABLE IF NOT EXISTS plugin_release (
    plugin_nick VARCHAR(255) NOT NULL PRIMARY KEY,
    plugin_type VARCHAR(255) NOT NULL,
    version VARCHAR(50) NOT NULL,
    plugin_name VARCHAR(255) NOT NULL,
    storage_id UUID NOT NULL,
    dependency_tags JSONB NOT NULL
    );
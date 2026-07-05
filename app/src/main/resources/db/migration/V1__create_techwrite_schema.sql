create table documentation_workspaces (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(100) not null,
    type varchar(40) not null,
    owning_team varchar(100) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table technical_documents (
    id uuid primary key,
    owner_id varchar(128) not null,
    title varchar(160) not null,
    type varchar(40) not null,
    status varchar(40) not null,
    workspace_name varchar(120) not null,
    author varchar(120) not null,
    freshness_score integer not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table review_issues (
    id uuid primary key,
    owner_id varchar(128) not null,
    document_title varchar(160) not null,
    category varchar(80) not null,
    severity varchar(30) not null,
    status varchar(30) not null,
    message varchar(240) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table publication_events (
    id uuid primary key,
    owner_id varchar(128) not null,
    occurred_on date not null,
    type varchar(40) not null,
    document_title varchar(160) not null,
    actor varchar(120) not null,
    note varchar(240) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table documentation_targets (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(120) not null,
    target_coverage integer not null,
    review_sla_hours integer not null,
    target_date date not null,
    active boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create index idx_documentation_workspaces_owner on documentation_workspaces(owner_id);
create index idx_technical_documents_owner on technical_documents(owner_id);
create index idx_review_issues_owner on review_issues(owner_id);
create index idx_publication_events_owner_date on publication_events(owner_id, occurred_on);
create index idx_documentation_targets_owner_active on documentation_targets(owner_id, active);

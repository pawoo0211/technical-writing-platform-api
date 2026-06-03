create table asset_accounts (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(100) not null,
    kind varchar(30) not null,
    institution varchar(80) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table asset_items (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(120) not null,
    type varchar(30) not null,
    current_value numeric(19, 2) not null,
    currency varchar(3) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table liabilities (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(120) not null,
    type varchar(30) not null,
    outstanding_balance numeric(19, 2) not null,
    annual_interest_rate numeric(6, 3) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table cash_flow_transactions (
    id uuid primary key,
    owner_id varchar(128) not null,
    transacted_on date not null,
    type varchar(30) not null,
    category varchar(80) not null,
    memo varchar(160) not null,
    amount numeric(19, 2) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create table exit_goals (
    id uuid primary key,
    owner_id varchar(128) not null,
    name varchar(120) not null,
    target_net_worth numeric(19, 2) not null,
    target_date date not null,
    target_savings_rate numeric(5, 2) not null,
    active boolean not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null
);

create index idx_asset_accounts_owner on asset_accounts(owner_id);
create index idx_asset_items_owner on asset_items(owner_id);
create index idx_liabilities_owner on liabilities(owner_id);
create index idx_transactions_owner_date on cash_flow_transactions(owner_id, transacted_on);
create index idx_exit_goals_owner_active on exit_goals(owner_id, active);

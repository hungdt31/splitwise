-- =============================================
-- EVENTMATE DATABASE SCHEMA (PostgreSQL)
-- =============================================

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =============================================
-- 1. USERS
-- =============================================
CREATE TABLE users (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email               VARCHAR(255) UNIQUE NOT NULL,
    password_hash       VARCHAR(255),                    -- null nếu dùng OAuth
    google_id           VARCHAR(255) UNIQUE,
    full_name           VARCHAR(100) NOT NULL,
    phone_number        VARCHAR(20),
    avatar_url          TEXT,
    bank_account_info   JSONB,                           -- {bankName, accountNumber, accountName, ...}
    notification_preferences JSONB DEFAULT '{"event": true, "expense": true, "payment": true, "reminder": true}'::jsonb,
    is_email_verified   BOOLEAN DEFAULT FALSE,
    status              VARCHAR(20) DEFAULT 'ACTIVE',    -- ACTIVE, INACTIVE, DELETED
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_google_id ON users(google_id);

-- =============================================
-- 2. GROUPS
-- =============================================
CREATE TABLE groups (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name                VARCHAR(100) NOT NULL,
    cover_image_url     TEXT,
    description         TEXT,
    created_by          UUID NOT NULL REFERENCES users(id),
    status              VARCHAR(20) DEFAULT 'ACTIVE',    -- ACTIVE, ARCHIVED, DELETED
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_groups_created_by ON groups(created_by);

-- Group Members
CREATE TABLE group_members (
    group_id            UUID NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role                VARCHAR(20) DEFAULT 'MEMBER',    -- ADMIN, MEMBER
    joined_at           TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (group_id, user_id)
);

-- =============================================
-- 3. EVENTS
-- =============================================
CREATE TABLE events (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_id            UUID REFERENCES groups(id) ON DELETE SET NULL,
    name                VARCHAR(200) NOT NULL,
    description         TEXT,
    category            VARCHAR(50),                     -- SPORT, DINING, TRAVEL, OTHER (Enum)
    location            TEXT,
    latitude            DECIMAL(10,8),
    longitude           DECIMAL(11,8),
    start_time          TIMESTAMPTZ NOT NULL,
    end_time            TIMESTAMPTZ,
    status              VARCHAR(20) DEFAULT 'UPCOMING',  -- UPCOMING, ONGOING, COMPLETED, CANCELLED
    created_by          UUID NOT NULL REFERENCES users(id),
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW(),
    deleted_at          TIMESTAMPTZ                      -- Soft delete
);

CREATE INDEX idx_events_group_id ON events(group_id);
CREATE INDEX idx_events_start_time ON events(start_time);
CREATE INDEX idx_events_status ON events(status);

-- Event Participants (RSVP)
CREATE TABLE event_participants (
    event_id            UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    rsvp_status         VARCHAR(20) DEFAULT 'NO_RESPONSE', -- GOING, MAYBE, NOT_GOING, NO_RESPONSE
    joined_at           TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (event_id, user_id)
);

-- =============================================
-- 4. EXPENSES
-- =============================================
CREATE TABLE expenses (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_id            UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    payer_id            UUID NOT NULL REFERENCES users(id),
    amount              DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    title               VARCHAR(200) NOT NULL,
    category            VARCHAR(50),                     -- VENUE, FOOD, DRINK, TRANSPORT, OTHER
    expense_date        DATE NOT NULL,
    description         TEXT,
    receipt_url         TEXT,                            -- S3 link
    included_participants JSONB,                         -- Array of user_id (nếu muốn loại trừ)
    created_by          UUID NOT NULL REFERENCES users(id),
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_expenses_event_id ON expenses(event_id);
CREATE INDEX idx_expenses_payer_id ON expenses(payer_id);

-- =============================================
-- 5. SPLITS & DEBTS (Tính toán chia tiền)
-- =============================================
-- Lưu kết quả chia tiền sau khi áp dụng strategy
CREATE TABLE expense_splits (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    expense_id          UUID NOT NULL REFERENCES expenses(id) ON DELETE CASCADE,
    user_id             UUID NOT NULL REFERENCES users(id),
    share_amount        DECIMAL(15,2) NOT NULL,
    split_strategy      VARCHAR(30) NOT NULL,            -- EQUAL, PERCENTAGE, CUSTOM_AMOUNT
    percentage          DECIMAL(5,2),                    -- null nếu không dùng PERCENTAGE
    created_at          TIMESTAMPTZ DEFAULT NOW()
);

-- Debt Summary (đã simplify debts giữa các user trong event)
CREATE TABLE debts (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_id            UUID REFERENCES events(id) ON DELETE CASCADE,
    from_user_id        UUID NOT NULL REFERENCES users(id),
    to_user_id          UUID NOT NULL REFERENCES users(id),
    amount              DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    status              VARCHAR(20) DEFAULT 'PENDING',   -- PENDING, PARTIAL, SETTLED
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW(),
    
    UNIQUE(event_id, from_user_id, to_user_id)
);

-- =============================================
-- 6. PAYMENTS
-- =============================================
CREATE TABLE payments (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_id            UUID REFERENCES events(id) ON DELETE SET NULL,
    from_user_id        UUID NOT NULL REFERENCES users(id),
    to_user_id          UUID NOT NULL REFERENCES users(id),
    amount              DECIMAL(15,2) NOT NULL,
    note                TEXT,
    proof_image_url     TEXT,
    status              VARCHAR(30) DEFAULT 'PENDING_CONFIRMATION', 
        -- PENDING_CONFIRMATION, CONFIRMED, REJECTED
    rejection_reason    TEXT,
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    confirmed_at        TIMESTAMPTZ
);

CREATE INDEX idx_payments_event_id ON payments(event_id);
CREATE INDEX idx_payments_from_to ON payments(from_user_id, to_user_id);

-- =============================================
-- 7. NOTIFICATIONS & ACTIVITY
-- =============================================
CREATE TABLE notifications (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type                VARCHAR(50) NOT NULL,            -- NEW_EXPENSE, PAYMENT_CONFIRMED, EVENT_INVITE, DEBT_REMINDER, ...
    title               VARCHAR(200),
    message             TEXT,
    data                JSONB,                           -- chứa event_id, expense_id, ...
    is_read             BOOLEAN DEFAULT FALSE,
    created_at          TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_notifications_user ON notifications(user_id, is_read);

CREATE TABLE activity_logs (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_id            UUID REFERENCES events(id) ON DELETE CASCADE,
    group_id            UUID REFERENCES groups(id) ON DELETE CASCADE,
    actor_id            UUID NOT NULL REFERENCES users(id),
    action_type         VARCHAR(50) NOT NULL,            -- ADDED_EXPENSE, UPDATED_EVENT, PAYMENT_CONFIRMED, MEMBER_JOINED,...
    target_id           UUID,                            -- expense_id, payment_id,...
    metadata            JSONB,
    created_at          TIMESTAMPTZ DEFAULT NOW()
);

-- =============================================
-- 8. INVITATIONS
-- =============================================
CREATE TABLE group_invitations (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_id            UUID NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    invited_by          UUID NOT NULL REFERENCES users(id),
    email               VARCHAR(255),
    token               VARCHAR(100) UNIQUE NOT NULL,
    expires_at          TIMESTAMPTZ NOT NULL,
    status              VARCHAR(20) DEFAULT 'PENDING',   -- PENDING, ACCEPTED, EXPIRED, CANCELLED
    created_at          TIMESTAMPTZ DEFAULT NOW()
);

-- =============================================
-- Triggers & Functions
-- =============================================
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Auto update updated_at
CREATE TRIGGER trg_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_groups_updated_at
    BEFORE UPDATE ON groups
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_events_updated_at
    BEFORE UPDATE ON events
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER trg_expenses_updated_at
    BEFORE UPDATE ON expenses
    FOR EACH ROW EXECUTE FUNCTION update_timestamp();

-- Indexes bổ sung cho performance
CREATE INDEX idx_debts_from_user ON debts(from_user_id);
CREATE INDEX idx_debts_to_user ON debts(to_user_id);
CREATE INDEX idx_activity_event ON activity_logs(event_id);
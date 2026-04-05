CREATE TABLE membership_plans (
    plan_id UUID PRIMARY KEY,
    plan_name VARCHAR(255) NOT NULL,
    duration_in_months INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE members (
    member_id VARCHAR(36) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL,
    membership_plan_id UUID NOT NULL,
    membership_status VARCHAR(50) NOT NULL,
    membership_start_date DATE NOT NULL,
    membership_end_date DATE NOT NULL,
    CONSTRAINT fk_members_membership_plan
        FOREIGN KEY (membership_plan_id) REFERENCES membership_plans (plan_id)
);

CREATE TABLE enrollments (
    enrollment_id VARCHAR(36) PRIMARY KEY,
    member_id VARCHAR(36) NOT NULL,
    CONSTRAINT fk_enrollments_member
        FOREIGN KEY (member_id) REFERENCES members (member_id)
);

CREATE TABLE enrollment_items (
    registration_id UUID PRIMARY KEY,
    enrollment_id VARCHAR(36) NOT NULL,
    enrollment_date TIMESTAMP NOT NULL,
    enrollment_status VARCHAR(50) NOT NULL,
    class_session_id VARCHAR(36) NOT NULL,
    seat_number INTEGER,
    -- TODO: trainer_id VARCHAR(36),
    -- TODO: schedule_id VARCHAR(36),
    CONSTRAINT fk_enrollment_items_enrollment
        FOREIGN KEY (enrollment_id) REFERENCES enrollments (enrollment_id) ON DELETE CASCADE
);

-- ============================================
-- TEST DATA
-- ============================================

-- Membership Plans
INSERT INTO membership_plans (plan_id, plan_name, duration_in_months, price)
VALUES ('cab08990-8bbd-4210-b905-ff3ab3665622', 'Standard Plan', 12, 499.99);

-- Members
INSERT INTO members (
    member_id,
    full_name,
    date_of_birth,
    email,
    phone,
    membership_plan_id,
    membership_status,
    membership_start_date,
    membership_end_date
)
VALUES
    ('21f9d274-2aa5-4bfc-b3d2-b641046b0d0b', 'Alice Johnson', '1990-01-15', 'alice.johnson@example.com', '+15145550001', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-01-01', '2026-12-31'),
    ('b6d8ed6a-b254-4d94-8654-2fa957ceaec5', 'Brian Smith', '1988-03-22', 'brian.smith@example.com', '+15145550002', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-01-01', '2026-12-31'),
    ('3d872866-67d0-471b-97f1-d69552eb1134', 'Carla Mendes', '1995-07-09', 'carla.mendes@example.com', '+15145550003', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-02-01', '2027-01-31'),
    ('e71a6097-bc04-4f15-8567-66bdca3904b2', 'Daniel Brown', '1992-11-30', 'daniel.brown@example.com', '+15145550004', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'INACTIVE', '2025-01-01', '2025-12-31'),
    ('860340e1-6aaf-4bdd-90ae-7d3f7b925f8b', 'Elena Garcia', '1998-05-18', 'elena.garcia@example.com', '+15145550005', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-03-01', '2027-02-28'),
    ('ac8831ea-5162-4f8e-91c4-1bf610040655', 'Farid Khan', '1985-09-12', 'farid.khan@example.com', '+15145550006', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'SUSPENDED', '2026-01-15', '2027-01-14'),
    ('49d3c2d4-eb15-407c-91a9-2db3c19a8253', 'Grace Lee', '1993-04-27', 'grace.lee@example.com', '+15145550007', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-04-01', '2027-03-31'),
    ('4a0376a4-cb45-4de8-8e82-f21a166d0c67', 'Henry Walker', '1989-12-03', 'henry.walker@example.com', '+15145550008', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'CANCELLED', '2025-06-01', '2026-05-31'),
    ('bc5b6681-ae4f-493d-b14f-4cea299f25f0', 'Isabella Martin', '1997-08-21', 'isabella.martin@example.com', '+15145550009', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-02-15', '2027-02-14'),
    ('076d07d8-7189-431c-9508-f19c43209e0f', 'Jason Clark', '1991-06-10', 'jason.clark@example.com', '+15145550010', 'cab08990-8bbd-4210-b905-ff3ab3665622', 'ACTIVE', '2026-01-10', '2027-01-09');

-- Enrollments (for active members)
INSERT INTO enrollments (enrollment_id, member_id)
VALUES
    ('a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', '21f9d274-2aa5-4bfc-b3d2-b641046b0d0b'),  -- Alice
    ('b2c3d4e5-f6a7-5b6c-9d0e-1f2a3b4c5d6e', 'b6d8ed6a-b254-4d94-8654-2fa957ceaec5'),  -- Brian
    ('c3d4e5f6-a7b8-6c7d-0e1f-2a3b4c5d6e7f', '3d872866-67d0-471b-97f1-d69552eb1134'),  -- Carla
    ('d4e5f6a7-b8c9-7d8e-1f2a-3b4c5d6e7f8a', '860340e1-6aaf-4bdd-90ae-7d3f7b925f8b'),  -- Elena
    ('e5f6a7b8-c9d0-8e9f-2a3b-4c5d6e7f8a9b', '49d3c2d4-eb15-407c-91a9-2db3c19a8253');  -- Grace

-- Enrollment Items (class registrations)
INSERT INTO enrollment_items (registration_id, enrollment_id, enrollment_date, enrollment_status, class_session_id, seat_number)
VALUES
    -- Alice: 2 classes
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', '2026-04-01 09:00:00', 'ENROLLED', 'a1a1a1a1-1111-1111-1111-111111111111', 1),
    ('7c9e6679-7425-40de-944b-e07fc1f90ae7', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', '2026-04-01 10:00:00', 'ENROLLED', 'b2b2b2b2-2222-2222-2222-222222222222', 5),
    -- Brian: 1 class
    ('550e8400-e29b-41d4-a716-446655440000', 'b2c3d4e5-f6a7-5b6c-9d0e-1f2a3b4c5d6e', '2026-04-02 08:30:00', 'ENROLLED', 'c3c3c3c3-3333-3333-3333-333333333333', 3),
    -- Carla: 1 cancelled class
    ('6ba7b810-9dad-11d1-80b4-00c04fd430c8', 'c3d4e5f6-a7b8-6c7d-0e1f-2a3b4c5d6e7f', '2026-03-15 14:00:00', 'CANCELLED', 'd4d4d4d4-4444-4444-4444-444444444444', NULL),
    -- Elena: 2 classes (1 pending)
    ('6ba7b811-9dad-11d1-80b4-00c04fd430c8', 'd4e5f6a7-b8c9-7d8e-1f2a-3b4c5d6e7f8a', '2026-04-03 11:00:00', 'ENROLLED', 'e5e5e5e5-5555-5555-5555-555555555555', 2),
    ('6ba7b812-9dad-11d1-80b4-00c04fd430c8', 'd4e5f6a7-b8c9-7d8e-1f2a-3b4c5d6e7f8a', '2026-04-03 12:00:00', 'PENDING', 'f6f6f6f6-6666-6666-6666-666666666666', NULL),
    -- Grace: 1 class
    ('6ba7b814-9dad-11d1-80b4-00c04fd430c8', 'e5f6a7b8-c9d0-8e9f-2a3b-4c5d6e7f8a9b', '2026-04-04 16:00:00', 'ENROLLED', 'a7a7a7a7-7777-7777-7777-777777777777', 8);

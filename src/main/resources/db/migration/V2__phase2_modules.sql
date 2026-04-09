CREATE TABLE trainers (
    trainer_id VARCHAR(36) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    specialty VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE trainer_certifications (
    certification_id UUID PRIMARY KEY,
    trainer_id VARCHAR(36) NOT NULL,
    certificate_name VARCHAR(255) NOT NULL,
    issued_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    CONSTRAINT fk_trainer_certifications_trainer
        FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id) ON DELETE CASCADE
);

CREATE TABLE schedules (
    schedule_id VARCHAR(36) PRIMARY KEY,
    class_name VARCHAR(255) NOT NULL,
    class_type VARCHAR(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    room_name VARCHAR(255) NOT NULL,
    room_capacity INTEGER NOT NULL,
    trainer_id VARCHAR(36) NOT NULL,
    max_capacity INTEGER NOT NULL,
    enrolled_count INTEGER NOT NULL,
    class_session_id VARCHAR(36) NOT NULL UNIQUE,
    session_date DATE NOT NULL,
    session_status VARCHAR(50) NOT NULL
);

ALTER TABLE enrollment_items ADD COLUMN trainer_id VARCHAR(36);
ALTER TABLE enrollment_items ADD COLUMN schedule_id VARCHAR(36);

INSERT INTO trainers (trainer_id, full_name, email, specialty, active)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Sophia Tran', 'sophia.tran@gym.local', 'Yoga', TRUE),
    ('22222222-2222-2222-2222-222222222222', 'Marcus Bell', 'marcus.bell@gym.local', 'Spin', TRUE),
    ('33333333-3333-3333-3333-333333333333', 'Naomi Brooks', 'naomi.brooks@gym.local', 'Boxing', TRUE),
    ('44444444-4444-4444-4444-444444444444', 'Julian Park', 'julian.park@gym.local', 'Pilates', TRUE),
    ('55555555-5555-5555-5555-555555555555', 'Ava Chen', 'ava.chen@gym.local', 'Strength', TRUE),
    ('66666666-6666-6666-6666-666666666666', 'Leo Carter', 'leo.carter@gym.local', 'HIIT', TRUE),
    ('77777777-7777-7777-7777-777777777777', 'Mila Singh', 'mila.singh@gym.local', 'Dance', TRUE);

INSERT INTO trainer_certifications (certification_id, trainer_id, certificate_name, issued_date, expiry_date)
VALUES
    ('10000000-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 'Registered Yoga Instructor', '2024-01-15', '2027-01-15'),
    ('10000000-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222', 'Indoor Cycling Coach', '2024-02-20', '2027-02-20'),
    ('10000000-0000-0000-0000-000000000003', '33333333-3333-3333-3333-333333333333', 'BoxFit Trainer', '2024-03-10', '2027-03-10'),
    ('10000000-0000-0000-0000-000000000004', '44444444-4444-4444-4444-444444444444', 'Pilates Foundation', '2024-04-05', '2027-04-05'),
    ('10000000-0000-0000-0000-000000000005', '55555555-5555-5555-5555-555555555555', 'Strength and Conditioning', '2024-05-01', '2027-05-01'),
    ('10000000-0000-0000-0000-000000000006', '66666666-6666-6666-6666-666666666666', 'HIIT Performance', '2024-06-12', '2027-06-12'),
    ('10000000-0000-0000-0000-000000000007', '77777777-7777-7777-7777-777777777777', 'Dance Cardio Basics', '2024-07-18', '2027-07-18');

INSERT INTO schedules (
    schedule_id,
    class_name,
    class_type,
    start_time,
    end_time,
    room_id,
    room_name,
    room_capacity,
    trainer_id,
    max_capacity,
    enrolled_count,
    class_session_id,
    session_date,
    session_status
)
VALUES
    ('81000000-0000-0000-0000-000000000001', 'Sunrise Yoga', 'Yoga', '2026-04-10 09:00:00', '2026-04-10 10:00:00', 'r101', 'Studio A', 20, '11111111-1111-1111-1111-111111111111', 20, 1, 'a1a1a1a1-1111-1111-1111-111111111111', '2026-04-10', 'SCHEDULED'),
    ('81000000-0000-0000-0000-000000000002', 'Power Spin', 'Spin', '2026-04-10 10:15:00', '2026-04-10 11:00:00', 'r102', 'Cycle Room', 16, '22222222-2222-2222-2222-222222222222', 16, 1, 'b2b2b2b2-2222-2222-2222-222222222222', '2026-04-10', 'SCHEDULED'),
    ('81000000-0000-0000-0000-000000000003', 'Boxing Basics', 'Boxing', '2026-04-11 08:30:00', '2026-04-11 09:30:00', 'r103', 'Combat Room', 18, '33333333-3333-3333-3333-333333333333', 18, 1, 'c3c3c3c3-3333-3333-3333-333333333333', '2026-04-11', 'SCHEDULED'),
    ('81000000-0000-0000-0000-000000000004', 'Pilates Flow', 'Pilates', '2026-04-12 14:00:00', '2026-04-12 15:00:00', 'r101', 'Studio A', 20, '44444444-4444-4444-4444-444444444444', 20, 0, 'd4d4d4d4-4444-4444-4444-444444444444', '2026-04-12', 'CANCELLED'),
    ('81000000-0000-0000-0000-000000000005', 'Strength Circuit', 'Strength', '2026-04-13 11:00:00', '2026-04-13 12:00:00', 'r104', 'Weights Room', 24, '55555555-5555-5555-5555-555555555555', 24, 1, 'e5e5e5e5-5555-5555-5555-555555555555', '2026-04-13', 'SCHEDULED'),
    ('81000000-0000-0000-0000-000000000006', 'Lunch HIIT', 'HIIT', '2026-04-13 12:15:00', '2026-04-13 13:00:00', 'r104', 'Weights Room', 24, '66666666-6666-6666-6666-666666666666', 24, 0, 'f6f6f6f6-6666-6666-6666-666666666666', '2026-04-13', 'SCHEDULED'),
    ('81000000-0000-0000-0000-000000000007', 'Dance Cardio', 'Dance', '2026-04-14 16:00:00', '2026-04-14 17:00:00', 'r105', 'Studio B', 18, '77777777-7777-7777-7777-777777777777', 18, 1, 'a7a7a7a7-7777-7777-7777-777777777777', '2026-04-14', 'SCHEDULED');

UPDATE enrollment_items
SET trainer_id = '11111111-1111-1111-1111-111111111111',
    schedule_id = '81000000-0000-0000-0000-000000000001'
WHERE class_session_id = 'a1a1a1a1-1111-1111-1111-111111111111';

UPDATE enrollment_items
SET trainer_id = '22222222-2222-2222-2222-222222222222',
    schedule_id = '81000000-0000-0000-0000-000000000002'
WHERE class_session_id = 'b2b2b2b2-2222-2222-2222-222222222222';

UPDATE enrollment_items
SET trainer_id = '33333333-3333-3333-3333-333333333333',
    schedule_id = '81000000-0000-0000-0000-000000000003'
WHERE class_session_id = 'c3c3c3c3-3333-3333-3333-333333333333';

UPDATE enrollment_items
SET trainer_id = '44444444-4444-4444-4444-444444444444',
    schedule_id = '81000000-0000-0000-0000-000000000004'
WHERE class_session_id = 'd4d4d4d4-4444-4444-4444-444444444444';

UPDATE enrollment_items
SET trainer_id = '55555555-5555-5555-5555-555555555555',
    schedule_id = '81000000-0000-0000-0000-000000000005'
WHERE class_session_id = 'e5e5e5e5-5555-5555-5555-555555555555';

UPDATE enrollment_items
SET trainer_id = '66666666-6666-6666-6666-666666666666',
    schedule_id = '81000000-0000-0000-0000-000000000006'
WHERE class_session_id = 'f6f6f6f6-6666-6666-6666-666666666666';

UPDATE enrollment_items
SET trainer_id = '77777777-7777-7777-7777-777777777777',
    schedule_id = '81000000-0000-0000-0000-000000000007'
WHERE class_session_id = 'a7a7a7a7-7777-7777-7777-777777777777';

ALTER TABLE enrollment_items ALTER COLUMN trainer_id SET NOT NULL;
ALTER TABLE enrollment_items ALTER COLUMN schedule_id SET NOT NULL;

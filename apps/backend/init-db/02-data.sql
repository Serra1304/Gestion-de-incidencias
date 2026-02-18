-- ============================================================================
--  INSERT SAMPLE USERS
-- ============================================================================

-- Passwords used:
-- password123  → bcrypt →  $2a$10$ibw9RE8d0l55ruzclHrT2ONoTdT0MW/qr0sb7f4Fk2kGYizopKBxy

-- ============================================================================ 
-- INSERT ADMIN USER
-- ============================================================================ 
INSERT INTO auth_user (id, email, password_hash, security_role, enabled)
VALUES (
    UUID(),
    'admin@example.com',
    '$2a$10$ibw9RE8d0l55ruzclHrT2ONoTdT0MW/qr0sb7f4Fk2kGYizopKBxy',
    'ROLE_SUPERADMIN',
    TRUE
);

INSERT INTO user_profile (id, user_id, name, last_name, second_last_name,
        address, address_number, city, province, postal_code, phone, phone_business, phone_extension)
SELECT
    UUID(),
    id,
    'System',
    'Administrator',
    'Administrator',
    'Ramon y Cajal',
    '55',
    'Mijas',
    'Malaga',
    '28001',
    '666362514',
    '666142536',
    '7025'
FROM auth_user
WHERE email = 'admin@example.com';



-- ============================================================================ 
-- INSERT NORMAL USER
-- ============================================================================ 
INSERT INTO auth_user (id, email, password_hash, security_role, enabled)
VALUES (
    UUID(),
    'user@example.com',
    '$2a$10$ibw9RE8d0l55ruzclHrT2ONoTdT0MW/qr0sb7f4Fk2kGYizopKBxy',
    'ROLE_USER',
    TRUE
);

INSERT INTO user_profile (id, user_id, name, last_name, second_last_name,
        address, address_number, city, province, postal_code, phone, phone_business, phone_extension)
SELECT
    UUID(),
    id,
    'Pepe',
    'Torres',
    'Garcia',
    'San Benito',
    '87',
    'Osuna',
    'Sevilla',
    '28771',
    '666385204',
    '660147536',
    '7095'
FROM auth_user
WHERE email = 'user@example.com';


-- ================================
--   INSERT WORK GROUP
-- ================================

INSERT INTO work_group (id, name, description, active)
VALUES
    (UUID(), 'Electricidad', 'Equipo encargado de incidencias eléctricas', TRUE),
    (UUID(), 'Mantenimiento General', 'Equipo de mantenimiento general del edificio', TRUE),
    (UUID(), 'Soporte TI', 'Grupo encargado del soporte de sistemas', TRUE),
    (UUID(), 'Climatización', 'Equipo especializado en aire acondicionado y HVAC', TRUE);


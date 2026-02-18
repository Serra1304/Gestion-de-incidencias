-- Tabla para login / autenticación
CREATE TABLE IF NOT EXISTS auth_user (
    id UUID PRIMARY KEY DEFAULT UUID(),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    security_role VARCHAR(50) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para perfil de usuario
CREATE TABLE IF NOT EXISTS user_profile (
    id UUID PRIMARY KEY DEFAULT UUID(),
    user_id UUID NOT NULL REFERENCES auth_user(id) ON DELETE CASCADE,
    name VARCHAR(50),
    last_name VARCHAR(50),
    second_last_name VARCHAR(50),
    address VARCHAR(150),
    address_number VARCHAR(10),
    city VARCHAR(150),
    province VARCHAR(150),
    postal_code VARCHAR(10),
    phone VARCHAR(20),
    phone_business VARCHAR(20),
    phone_extension VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para la invalidacion de tokens
CREATE TABLE IF NOT EXISTS invalid_token (
    id UUID PRIMARY KEY DEFAULT UUID(),
    token VARCHAR(500) NOT NULL,
    user_id UUID NOT NULL REFERENCES auth_user(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para grupos de trabajo
CREATE TABLE IF NOT EXISTS work_group (
    id UUID PRIMARY KEY DEFAULT UUID(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla intermedia grupos de trabajo / usuarios
CREATE TABLE IF NOT EXISTS work_group_user (
    group_id UUID NOT NULL REFERENCES work_group(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES auth_user(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (group_id, user_id)
);
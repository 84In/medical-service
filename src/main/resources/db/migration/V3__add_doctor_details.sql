-- ALTER bảng doctors để thêm phone và email
ALTER TABLE doctors
    ADD COLUMN phone VARCHAR(20),
  ADD COLUMN email VARCHAR(100);

-- Tạo bảng specialties
CREATE TABLE IF NOT EXISTS specialties
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    name
    VARCHAR
(
    100
) NOT NULL UNIQUE,
    description TEXT,
    status TINYINT UNSIGNED NOT NULL DEFAULT 1 CHECK
(
    status
    BETWEEN
    0
    AND
    3
)
    );

-- Bảng liên kết doctor - specialty (nhiều-nhiều)
CREATE TABLE IF NOT EXISTS doctor_specialty
(
    doctor_id
    INT
    NOT
    NULL,
    specialty_id
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    doctor_id,
    specialty_id
),
    FOREIGN KEY
(
    doctor_id
) REFERENCES doctors
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    specialty_id
) REFERENCES specialties
(
    id
)
  ON DELETE CASCADE
    );

-- Bảng lưu thông tin học vấn của bác sĩ
CREATE TABLE IF NOT EXISTS educations
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    degree
    VARCHAR
(
    100
) NOT NULL,
    institution VARCHAR
(
    255
) NOT NULL,
    year VARCHAR
(
    10
),
    description TEXT,
    doctor_id INT NOT NULL,
    FOREIGN KEY
(
    doctor_id
) REFERENCES doctors
(
    id
) ON DELETE CASCADE
    );

-- Bảng lưu kinh nghiệm làm việc
CREATE TABLE IF NOT EXISTS experiences
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    position
    VARCHAR
(
    100
),
    organization VARCHAR
(
    255
),
    start_year VARCHAR
(
    10
),
    end_year VARCHAR
(
    10
),
    description TEXT,
    doctor_id INT NOT NULL,
    FOREIGN KEY
(
    doctor_id
) REFERENCES doctors
(
    id
) ON DELETE CASCADE
    );

-- Bảng lưu thành tựu (giải thưởng, chứng nhận, nghiên cứu,...)
CREATE TABLE IF NOT EXISTS achievements
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    title
    VARCHAR
(
    255
),
    year VARCHAR
(
    10
),
    description TEXT,
    type ENUM
(
    'AWARD',
    'CERTIFICATION',
    'PUBLICATION',
    'RESEARCH'
) NOT NULL,
    doctor_id INT NOT NULL,
    FOREIGN KEY
(
    doctor_id
) REFERENCES doctors
(
    id
) ON DELETE CASCADE
    );

-- Bảng lưu giờ làm việc của bác sĩ
CREATE TABLE IF NOT EXISTS working_hours
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    day_of_week
    ENUM
(
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    doctor_id INT NOT NULL,
    FOREIGN KEY
(
    doctor_id
) REFERENCES doctors
(
    id
) ON DELETE CASCADE
    );

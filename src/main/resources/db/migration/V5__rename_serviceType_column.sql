
ALTER TABLE services CHANGE COLUMN serviceType_id service_type_id INT;

ALTER TABLE services DROP FOREIGN KEY services_ibfk_1;

ALTER TABLE services
    ADD CONSTRAINT fk_service_type
        FOREIGN KEY (service_type_id) REFERENCES service_types(id)
            ON DELETE SET NULL;

-- Table for storing pincode, latitude, and longitude information
CREATE TABLE IF NOT EXISTS pincode_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pincode VARCHAR(10) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

-- Insert initial data with pincode, latitude, and longitude
INSERT INTO pincode_entity (pincode, latitude, longitude) VALUES
('411014', 18.5245, 73.8472), -- Pune
('110001', 28.6139, 77.2090), -- New Delhi
('500001', 17.3850, 78.4867), -- Hyderabad
('560001', 12.9716, 77.5946), -- Bangalore
('600001', 13.0827, 80.2707), -- Chennai
('302001', 26.9124, 75.7873), -- Jaipur
('700001', 22.5726, 88.3639), -- Kolkata
('390001', 22.3072, 73.1812), -- Vadodara
('462001', 23.2599, 77.4126), -- Bhopal
('682001', 9.9312, 76.2673); -- Kochi

-- Table for storing weather information for a pincode and a specific date
CREATE TABLE IF NOT EXISTS weather_info_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pincode VARCHAR(10) NOT NULL,
    temperature DOUBLE,
    humidity DOUBLE,
    description VARCHAR(255),
    weather_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pincode) REFERENCES pincode_entity(pincode)
);

-- Optionally, insert some initial weather data (if available)
-- This is an example, you would fetch real-time data when calling the API.
INSERT INTO weather_info_entity (pincode, temperature, humidity, description, weather_date) VALUES
('411014', 25.0, 60.0, 'Clear', '2020-10-15'),
('110001', 22.5, 55.0, 'Cloudy', '2020-10-15'),
('500001', 29.5, 70.0, 'Sunny', '2020-10-15');

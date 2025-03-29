CREATE TABLE POSTING (
     POST_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
     TITLE VARCHAR(255) NOT NULL COMMENT 'Title',
     IMAGE_URL VARCHAR(255) COMMENT 'Image URL',
     CONTENT TEXT COMMENT 'Content',
     UPLOAD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Upload Date',
     MODIFY_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Modify Date'
);


INSERT INTO posting (TITLE, IMAGE_URL, CONTENT, UPLOAD_DATE, MODIFY_DATE)
VALUES
    ('Spring Boot Tutorial', 'https://example.com/image1.jpg', 'Introduction to Spring Boot', NOW(), NOW()),
    ('JPA Best Practices', 'https://example.com/image2.jpg', 'Guide to using JPA effectively', NOW(), NOW()),
    ('Understanding Hibernate', 'https://example.com/image3.jpg', 'Deep dive into Hibernate ORM', NOW(), NOW()),
    ('Effective Java', 'https://example.com/java.jpg', 'Best practices for Java development', NOW(), NOW()),
    ('Microservices with Spring', 'https://example.com/microservices.jpg', 'How to build microservices using Spring Boot', NOW(), NOW()),
    ('REST API Design', 'https://example.com/rest.jpg', 'Principles of designing a RESTful API', NOW(), NOW());

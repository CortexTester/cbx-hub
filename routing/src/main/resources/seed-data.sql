--spring.jpa.hibernate.ddl-auto=create   it is not working. this is a work around. will revisit late
TRUNCATE Party RESTART IDENTITY;

INSERT INTO Party(apiKey, dialect, name, partyId, url)
values
       ('TestKey1', 'UBL2', 'GoGo Travel', 1, 'http://localhost:8091'),
       ('TestKey2', 'UBL2', 'Cruise Line', 2, 'http://localhost:8092');
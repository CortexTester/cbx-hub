--spring.jpa.hibernate.ddl-auto=create   it is not working. this is a work around. will revisit late
-- TRUNCATE Party_Dialect RESTART IDENTITY;
-- TRUNCATE Dialect RESTART IDENTITY;
-- TRUNCATE Party RESTART IDENTITY;

INSERT INTO Dialect(id, name)
values (1, 'UBL2'),
       (2, 'EDI');

INSERT INTO Party(id, name, clientSidePartyId, url, apiKey)
values (1, 'GoGo Travel', 1, 'http://localhost:8091', 'TestKey1'),
       (2, 'Cruise Line', 1, 'http://localhost:8092', 'TestKey2');
--
-- INSERT INTO Party_Dialect(party_id, dialect_id)
-- values (1, 1),
--        (2, 1);
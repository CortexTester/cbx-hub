this is a POC project
-- learn module projects 

*** no data class for entity
https://github.com/spring-guides/tut-spring-boot-kotlin/blob/main/build.gradle.kts

***multi modules config properties
https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config

***spring.jpa.hibernate.ddl-auto -- create, create-drop, validate, and update
https://stackoverflow.com/questions/42135114/how-does-spring-jpa-hibernate-ddl-auto-property-exactly-work-in-spring
The update operation for example will attempt to add new columns, constraints, etc but will never remove a column or constraint that may have existed previously but no longer does as part of the object model from a prior run.
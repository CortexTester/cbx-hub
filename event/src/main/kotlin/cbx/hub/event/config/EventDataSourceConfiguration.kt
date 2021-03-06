package cbx.hub.event.config

import com.zaxxer.hikari.HikariDataSource
import org.hibernate.boot.SchemaAutoTooling
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@PropertySource(value = ["event.properties"])
@EnableJpaRepositories(
    basePackages = ["cbx.hub.event"],
    entityManagerFactoryRef = "eventEntityManagerFactory",
    transactionManagerRef = "eventTransactionManager"
)
class EventDataSourceConfiguration {
    @Bean(name = ["eventDataSourceProperties"])
    @ConfigurationProperties("event.datasource")
    fun dataSourceProperties(): DataSourceProperties? {
        return DataSourceProperties()
    }

    @Bean(name = ["eventDataSource"])
    @ConfigurationProperties("event.datasource.configuration")
    open fun dataSource(@Qualifier("eventDataSourceProperties") dataSourceProperties: DataSourceProperties): DataSource? {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java)
            .build()
    }

    @Bean(name = ["eventEntityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder, @Qualifier("eventDataSource") dataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean? {
        val properties: MutableMap<String, Any> = HashMap()
        properties[AvailableSettings.HBM2DDL_AUTO] = SchemaAutoTooling.CREATE.name.toLowerCase()
        properties[AvailableSettings.DIALECT] = "org.hibernate.dialect.PostgreSQLDialect"
        return builder
            .dataSource(dataSource)
            .properties(properties)
            .packages("cbx.hub.event")
            .build()
    }

    @Bean(name = ["eventTransactionManager"])
    fun transactionManager(
        @Qualifier("eventEntityManagerFactory") entityManagerFactory: EntityManagerFactory?
    ): PlatformTransactionManager? {
        return JpaTransactionManager(entityManagerFactory!!)
    }
}
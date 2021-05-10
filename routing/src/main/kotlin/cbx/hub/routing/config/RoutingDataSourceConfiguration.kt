package cbx.hub.routing.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
@PropertySource(value = ["routing.properties"])
@EnableJpaRepositories(
    basePackages = ["cbx.hub.routing"],
    entityManagerFactoryRef = "routingEntityManagerFactory",
    transactionManagerRef = "routingTransactionManager"
)
class RoutingDataSourceConfiguration (){
    @Primary
    @Bean(name = ["routingDataSourceProperties"])
    @ConfigurationProperties("routing.datasource")
    fun dataSourceProperties(): DataSourceProperties? {
        return DataSourceProperties()
    }

    @Primary
    @Bean(name = ["routingDataSource"])
    @ConfigurationProperties("routing.datasource.configuration")
    open fun dataSource(@Qualifier("routingDataSourceProperties") dataSourceProperties: DataSourceProperties): DataSource? {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java)
            .build()
    }

    @Primary
    @Bean(name = ["routingEntityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder, @Qualifier("routingDataSource") dataSource: DataSource?
    ): LocalContainerEntityManagerFactoryBean? {
        return builder
            .dataSource(dataSource)
            .packages("cbx.hub.routing")
            .build()
    }

    @Bean(name = ["routingTransactionManager"])
    fun transactionManager(
        @Qualifier("routingEntityManagerFactory") entityManagerFactory: EntityManagerFactory?
    ): PlatformTransactionManager? {
        return JpaTransactionManager(entityManagerFactory!!)
    }

//    @Bean
//    fun routingEntityManager(): LocalContainerEntityManagerFactoryBean =
//        (LocalContainerEntityManagerFactoryBean()).apply {
//            dataSource = routingDataSource()
//            setPackagesToScan("cbx.hub.routing")
//            jpaVendorAdapter = HibernateJpaVendorAdapter()
//            val properties = HashMap<String, Any?>()
////            properties["hibernate.hbm2ddl.auto"] = env.getProperty("hibernate.hbm2ddl.auto")
//
//            properties["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect" //env.getProperty("hibernate.dialect")
////            properties["driver-class-name"] = "org.postgresql.Driver"
////            properties["hibernate.hbm2ddl.auto"] = "create"
////            setJpaPropertyMap(properties)
//        }
//
//    @Bean
//    fun routingTransactionManager() = JpaTransactionManager(routingEntityManager().`object`!!)
}
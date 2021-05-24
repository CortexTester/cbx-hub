package cbx.hub.common.kafka

import cbx.hub.common.model.EventMessage
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
//@Profile("common")
@PropertySource(value = ["common.properties"])
@ConfigurationProperties(prefix = "common")
class EventConfig {
//    @Value("\${kafka.bootstrap-servers}")
    var kafkaServer: String? = "localhost:9091"

//    @Value("\${kafka.port:9091}")
//    private val port: Int = 0

    //consumer config
    @Bean
    fun eventKafkaListenerContainerFactory(eventConsumerFactory: ConsumerFactory<String, EventMessage>) =
        ConcurrentKafkaListenerContainerFactory<String, EventMessage>().also {
            it.consumerFactory = eventConsumerFactory
        }

    @Bean
    fun eventConsumerFactory() = DefaultKafkaConsumerFactory(consumerProps, StringDeserializer(), JsonDeserializer(EventMessage::class.java))

    val consumerProps = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        ConsumerConfig.GROUP_ID_CONFIG to "event",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        JsonDeserializer.TRUSTED_PACKAGES to "*"
    )

    //producer condig
    @Bean
    fun eventProducerFactory() = DefaultKafkaProducerFactory<String, EventMessage>(senderProps)

    val senderProps = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        ProducerConfig.LINGER_MS_CONFIG to 10,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
    )

    @Bean
    fun eventKafkaTemplate(eventProducerFactory: ProducerFactory<String, EventMessage>) =
        KafkaTemplate(eventProducerFactory)

    //topic config
    @Bean
    open fun eventTopic(): NewTopic? {
        return TopicBuilder.name(EVENT_TOPIC)
            .partitions(1)
            .replicas(1)
            .build()
    }

    //event sender
    @Bean
    fun eventSender(): EventSender = EventSender();

    @Bean
    fun  kafkaAdmin() :KafkaAdmin
    {
        val adminConfig = mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaServer,
        )
        return  KafkaAdmin(adminConfig);
    }


    //const
    companion object {
        const val EVENT_TOPIC = "event"
    }
}
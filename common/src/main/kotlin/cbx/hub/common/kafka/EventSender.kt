package cbx.hub.common.kafka

import cbx.hub.common.model.EventMessage
import cbx.hub.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback


class EventSender {
    @Autowired
    lateinit var eventKafkaTemplate: KafkaTemplate<String, EventMessage>

    fun send(message: EventMessage){
        logger().info("sending $message")
        val future : ListenableFuture<SendResult<String?, EventMessage?>> = eventKafkaTemplate.send(EventConfig.EVENT_TOPIC, message)

        future.addCallback(object : ListenableFutureCallback<SendResult<String?, EventMessage?>?> {
            override fun onSuccess(result: SendResult<String?, EventMessage?>?) {
                logger().info(  "Sent message: $message with offset: " + result?.recordMetadata?.offset() )
            }
            override fun onFailure(ex: Throwable) {
                logger().error("Unable to send message : $message", ex)
            }
        })
    }
}
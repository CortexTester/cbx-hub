package cbx.hub.event

import cbx.hub.common.model.EventMessage
import cbx.hub.event.model.Event
import cbx.hub.event.repository.EventRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepository: EventRepository) {
    fun test() = "from event service"
    fun addEvent(senderId: Long, receiverId: Long, dialect: String, eventTypeId: Int, contentLocation: String) : Long?{
        return eventRepository.save(Event(senderId = senderId, receiverId = receiverId, dialect = dialect, eventTypeId = eventTypeId, contentLocation = contentLocation)).id
    }

    @KafkaListener(topics = ["event"], groupId = "event-id",  containerFactory = "eventKafkaListenerContainerFactory")
    fun eventHandler(event:EventMessage):Unit{
        println("from event service $event")
    }

}
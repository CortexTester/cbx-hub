package cbx.hub.event

import cbx.hub.event.model.Event
import cbx.hub.event.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepository: EventRepository) {
    fun test() = "from event service"
    fun addEvent(senderId: Long, receiverId: Long, dialect: String, eventTypeId: Int, contentLocation: String) : Long?{
        return eventRepository.save(Event(senderId = senderId, receiverId = receiverId, dialect = dialect, eventTypeId = eventTypeId, contentLocation = contentLocation)).id
    }
}
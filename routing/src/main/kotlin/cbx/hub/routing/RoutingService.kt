package cbx.hub.routing

import cbx.hub.common.model.EventMessage
import cbx.hub.routing.model.Party
import cbx.hub.routing.repository.PartyRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["party"])
class RoutingService(val partyRepository: PartyRepository) {
    fun test() = "test routing"
    fun addPartyToRegistry(name: String, partyId: Long, dialect: String, url: String): Long? {
        return partyRepository.save(Party(name = name, partyId = partyId, dialect = dialect, url = url, apiKey = "testApiKey")).id
    }

    @KafkaListener(topics = ["event"], groupId = "routing-id",  containerFactory = "eventKafkaListenerContainerFactory")
    fun eventHandler(event: EventMessage):Unit{
        println("from routing service $event")
    }

    @Cacheable(value = ["party"], key="#key", unless = "#result!=null")
    fun getPartyByApiKey(key:String)  = partyRepository.findByApiKey(key)
}
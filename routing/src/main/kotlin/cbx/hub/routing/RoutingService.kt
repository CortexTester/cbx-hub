package cbx.hub.routing

import cbx.hub.common.model.EventMessage
import cbx.hub.routing.model.Dialect
import cbx.hub.routing.model.Party
import cbx.hub.routing.repository.DialectRepository
import cbx.hub.routing.repository.PartyRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["party"])
class RoutingService(val partyRepository: PartyRepository, val dialectRepository: DialectRepository) {
    fun test() = "test routing"

    //    @Transactional(value = "routingTransactionManager")
    fun addPartyToRegistry(name: String, partyId: Long, dialectId: Long, url: String, apiKey: String): Long? {
        try {
            val dialect: Dialect = dialectRepository.getOne(dialectId)
//        val dialect1 = Dialect(dialect.id, dialect.name)
            val party = Party(id = 3L, name = name, clientSidePartyId = partyId, url = url, apiKey = apiKey)
//        party.dialects?.add(dialect)
            val newPartyId = partyRepository.save(party).id
            party.dialects.add(dialect)
            partyRepository.save(party)

            return newPartyId

        }catch (e: Exception){
            println(e)
        }
        return null

    }

    @KafkaListener(topics = ["event"], groupId = "routing-id", containerFactory = "eventKafkaListenerContainerFactory")
    fun eventHandler(event: EventMessage): Unit {
        println("from routing service $event")
    }

    @Cacheable(value = ["party"], key = "{#key, #clientSidePartyId}", unless = "#result!=null")
    fun getPartyByApiKey(key: String, clientSidePartyId: Long = 1) =
        partyRepository.findByApiKeyAndClientSidePartyId(key, clientSidePartyId)
}
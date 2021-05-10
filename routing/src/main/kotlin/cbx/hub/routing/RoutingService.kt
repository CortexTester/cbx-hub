package cbx.hub.routing

import cbx.hub.routing.model.Registry
import cbx.hub.routing.repository.RegistryRepository
import org.springframework.stereotype.Service

@Service
class RoutingService(val registryRepository: RegistryRepository) {
    fun test() = "test routing"
    fun addPartyToRegistry(name: String, partyId: Long, dialect: String, url: String): Long? {
        return registryRepository.save(Registry(name = name, partyId = partyId, dialect = dialect, url = url)).id
    }
}
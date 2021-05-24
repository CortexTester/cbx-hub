package cbx.hub.routing.repository

import cbx.hub.routing.model.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository: JpaRepository<Party, Long> {
    fun findByApiKey(apiKey: String) : Party?
}
package cbx.hub.routing.repository

import cbx.hub.routing.model.Registry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistryRepository: JpaRepository<Registry, Long> {
}
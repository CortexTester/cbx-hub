package cbx.hub.routing.model

import javax.persistence.*

@Entity
class Registry(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)  var id: Long? = null,
    var name: String,
    var partyId: Long,
    var dialect: String,
    var url: String
)
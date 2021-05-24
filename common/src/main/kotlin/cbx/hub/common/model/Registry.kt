package cbx.hub.common.model

data class Registry(
    var id: Long? = null,
    var name: String,
    var partyId: Long,
    var dialect: String,
    var url: String
)

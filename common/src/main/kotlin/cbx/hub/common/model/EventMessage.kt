package cbx.hub.common.model

import java.util.*

data class EventMessage(
    val id: UUID, //tracking id
    val senderId: Long, // one sender
    val receiverIds: List<Long>, //multi receivers
    var dialect: String, //how document format, such as UBL, EDI
    var action: String,  //difference dialect has difference action, such as Approved, Rejected..
    var contentLocations: List<String> //this message only has document storage points
)
package cbx.hub.controller

import cbx.hub.common.TestService
import cbx.hub.event.EventService
import cbx.hub.routing.RoutingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    lateinit var service: TestService
    @Autowired
    lateinit var routingService: RoutingService
    @Autowired
    lateinit var eventService: EventService

    @GetMapping("/test")
    fun test() : String {
        val registryId = routingService.addPartyToRegistry("test party", 1001, "UBL2.0", "http://localhost:8081" )
        val eventId = eventService.addEvent(1, 2, "UBL2.0", 1, "http://aws/storage/1" )
//        return service.test() + " party registry id" + registryId
//        return service.test() + " event  " + eventService.test() + " id:" + eventId
        return service.test() + " party registry id" + registryId + " " + eventService.test() + " id:" + eventId
    }
}
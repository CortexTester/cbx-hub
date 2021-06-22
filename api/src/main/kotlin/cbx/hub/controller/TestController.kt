package cbx.hub.controller

import cbx.hub.common.TestService
import cbx.hub.common.kafka.EventSender
//import cbx.hub.common.kafka.EventConfig
//import cbx.hub.common.kafka.EventSender
import cbx.hub.common.model.EventMessage
import cbx.hub.common.model.Registry
import cbx.hub.event.EventService
import cbx.hub.event.model.Event
import cbx.hub.routing.RoutingService
import cbx.hub.security.PartyAuthenticationToken
import cbx.hub.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class TestController() {

    @Autowired
    lateinit var service: TestService
    @Autowired
    lateinit var routingService: RoutingService
    @Autowired
    lateinit var eventService: EventService


    @Autowired
    lateinit var eventSender: EventSender

    @GetMapping("/test")
    fun test(principal: Principal) : String {
        val registryId = routingService.addPartyToRegistry("test party", 1001, 1, "http://localhost:8081", "testKey8" )
        val eventId = eventService.addEvent(1, 2, "UBL2.0", 1, "http://aws/storage/1" )
        val party = (principal as PartyAuthenticationToken)?.party
        logger().info(party.toString())
        return service.test() + " party registry id" + registryId + " " + eventService.test() + " id:" + eventId
    }

    @PostMapping("/event")
    fun event(@RequestBody event : EventMessage): ResponseEntity<String> {
        eventSender.send(event)
        return ResponseEntity.ok("ok")
    }

//    @PostMapping("/registry")
//    fun event(@RequestBody party : Registry): ResponseEntity<String> {
//        var lf: ListenableFuture<SendResult<String, Registry>> = eventKafkaTemplate?.send("event", Registry)!!
//        var sendResult: SendResult<String, Registry> = lf.get()
//        return ResponseEntity.ok(sendResult.producerRecord.value().toString()  +  " sent to topic")
//    }

}
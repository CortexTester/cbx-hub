package cbx.hub.event.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EventTestController {
    @GetMapping("/event/api/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("from Event test controller")
    }
}
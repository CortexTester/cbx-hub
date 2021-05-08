package cbx.hub.controller

import cbx.hub.common.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    lateinit var service: TestService
    @GetMapping("/test")
    fun test() = service.test()
}
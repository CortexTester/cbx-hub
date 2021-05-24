package cbx.hub.common.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@EnableCaching
@Configuration
class CaffeineConfig {
    @Bean
    fun cacheManager(): CacheManager = CaffeineCacheManager("party")
        .apply {
            //todo: should skip the null value
//            isAllowNullValues = false
            setCaffeine(Caffeine.newBuilder().maximumSize(400).expireAfterWrite(60, TimeUnit.MINUTES))
        }
}
package config

import grails.plugin.cache.ehcache.GrailsEhcacheCacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.MemoryUnit
import org.ehcache.expiry.Duration
import org.ehcache.expiry.Expirations
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.util.concurrent.TimeUnit

class CacheConfig {

  @Autowired
  def GrailsEhcacheCacheManager grailsCacheManager

  CacheConfig() {
  }

  @PostConstruct
  void cacheConfig() {
    def resourcePoolBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
        .heap(3)
        .offheap(10, MemoryUnit.MB)
//        .disk(500, MemoryUnit.MB, false)

    def defaultConfig = CacheConfigurationBuilder
        .newCacheConfigurationBuilder(String.class, Serializable.class, resourcePoolBuilder)
        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(120L, TimeUnit.SECONDS)))
        .withExpiry(Expirations.timeToIdleExpiration(Duration.of(120L, TimeUnit.SECONDS)))

    println "using cache manager ${grailsCacheManager} "


    println "resourcePoolBuilder ${resourcePoolBuilder}"

    println "defaultConfig ${defaultConfig}"

    // we are only adding to the cache configuring
    // first time use will create the cache
    ['cache1', 'cache2']
        .each { cacheName ->
      grailsCacheManager.configuration.getCacheConfigurations().put(cacheName, defaultConfig)

      // retrieving the cache will create the cache
      // we can delay cache creation by commenting it here
      grailsCacheManager.getCache(cacheName)
    }

    println grailsCacheManager.getCacheNames().dump()
  }
}

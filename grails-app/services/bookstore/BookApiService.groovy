package bookstore

import grails.gorm.transactions.Transactional
import grails.plugin.cache.Cacheable
import groovy.util.logging.Slf4j

/**
 ctx.bookApiService.findAuthor("book1");
 ctx.bookApiService.findAuthor("book2");
 ctx.bookApiService.findAuthor("book3");
 ctx.bookApiService.findAuthor("book4");
 ctx.bookApiService.findAuthor("book5");

 ctx.bookApiService.findAuthor("book6");


 println ctx.grailsCacheManager.getCacheNames()

 ctx.grailsCacheManager.getCache("author_cache").getAllKeys().each {
 println it.simpleKey
 }

 println "Done"
 */
@Slf4j
class BookApiService {

    @Cacheable(value="author_cache", key={bookName})
    def String findAuthor(String bookName) {
        System.out.println "In findAuthor"
        return "HelloBook";
    }

    @Cacheable(value="cache1", key={bookName})
    def String findAuthorUsingCache1(String bookName) {
        System.out.println "In findAuthor"
        return "HelloBook";
    }
}

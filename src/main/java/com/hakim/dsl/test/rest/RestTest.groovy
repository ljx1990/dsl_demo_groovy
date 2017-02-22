package com.hakim.dsl.test.rest
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET
import static groovyx.net.http.ContentType.TEXT

// initialze a new builder and give a default URL
def http = new HTTPBuilder( 'http://www.google.com/search' )
when:
def status
http.request(GET,TEXT) { req ->
    uri.path = '/mail/help/tasks/' // overrides any path in the default URL
    headers.'User-Agent' = 'Mozilla/5.0'

    response.success = { resp, reader ->
        assert resp.status == 200
        status = resp.status
        println "My response handler got response: ${resp.statusLine}"
        println "Response length: ${resp.headers.'Content-Length'}"
        //System.out << reader // print response reader
    }

    // called only for a 404 (not found) status code:
    response.'404' = { resp ->
        println 'Not found'
    }
}

then:  200==status

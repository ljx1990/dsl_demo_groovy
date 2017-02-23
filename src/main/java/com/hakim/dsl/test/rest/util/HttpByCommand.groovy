package com.hakim.dsl.test.rest.util

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST


/**
 * Created by hd on 2017/1/7.
 */

class RestInfo {
    def uri
    Method method
    def type

    def RestInfo(method, qty) {
        this.method = method
        uri = qty.uri
        if(qty.type){
            this.type = qty.type
        }
    }

    def useMethod(Method method){
        this.method = method;
    }

}

class RestWrap{
    def order

    RestWrap(orderObject){
        order = orderObject
    }

    def customer(closure){
        closure = closure.clone()
        closure.delegate = order
        closure()
        order
    }
}


@Category(Object)
class RestInfoCatagory{
    static void callrest(Script self, RestInfo order){
        println  "Buy: $order"
        when:
        def status
        def http = new HTTPBuilder(order.uri)
        http.request(order.method,TEXT) { req ->
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
    }
    static void callrest(Script self, Map o){
        use(RestWrapCategory) {
            callrest(self, o.DEFAULT().customer{})
        }
    }
}


@Category(Map)
class RestWrapCategory {

    static RestWrap POST(Map self) {
        new RestWrap(new RestInfo(Method.POST, self))
    }
    static RestWrap GET(Map self) {
        new RestWrap(new RestInfo(Method.GET, self))
    }
    static RestWrap DEFAULT(Map self) {
        new RestWrap(new RestInfo(Method.valueOf(self.type.toUpperCase()), self))
    }

    static RestInfo customer(Map self,closure){
        use(RestWrapCategory){
            self.DEFAULT().customer(closure)
        }
    }
}




use(RestInfoCatagory,RestWrapCategory) {
    def dycPurchase = ["defaultURI":"http://activity.test123.com","uri":"/oper-activity/activity_dync/operate","type":"get"]
    /*callrest dycPurchase.customer{
        useMethod POST
    }*/
    callrest dycPurchase

}


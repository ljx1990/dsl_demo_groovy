package com.hakim.dsl.test.rest.util


/**
 * Created by hd on 2017/1/7.
 */

class RestInfo {
    def uri
    def method
    def type

    def RestInfo(method, qty) {
        this.method = method
        uri = qty.uri
        if(qty.type){
            this.type = qty.type
        }
    }

}

class RestWrap{
    def order

    RestWrap(orderObject){
        order = orderObject
    }

    def exec(closure){
        closure = closure.clone()
        closure.delegate = order
        closure()
        order
    }
}


@Category(Integer)
class RestInfoCatagory{
    static void callrest(Script self, RestWrap o){
        println  "Buy: $o"
    }
    static void callrest(Script self, Map o){
        def ok
        use(RestWrapCategory) {
          ok =   o.DEFAULT()
        }
        println  "Buy: $ok"
    }
}


@Category(Map)
class RestWrapCategory {

    static RestWrap POST(Map self) {
        new RestWrap(new RestInfo("POST", self))
    }

    static RestWrap GET(Map self) {
        new RestWrap(new RestInfo("GET", self))
    }
    static RestWrap DEFAULT(Map self) {
        new RestWrap(new RestInfo(self.type, self))
    }
}


use(RestInfoCatagory,RestWrapCategory) {
    Map dycPurchase = ["uri":"www.baidu.com","type":"post"]
    callrest dycPurchase

}


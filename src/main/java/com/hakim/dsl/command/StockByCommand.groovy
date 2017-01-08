package com.hakim.dsl.command


/**
 * Created by hd on 2017/1/7.
 */

class Order {
    def name
    def quantity
    def allOrNone = false
    def limitPrice
    def valueClosure

    def Order(stockName, qty) {
        name = stockName
        quantity = qty
    }

    def limitPrice(price) {limitPrice = price}

    def allOrNone() {allOrNone = true}

    def valueAs(closure) {
        valueClosure = closure.clone()
        valueClosure.delegate =
                [qty: quantity, unitPrice: limitPrice]
    }

    String toString() {
        "stock: $name, number of shares: $quantity, allOrNone: $allOrNone, limitPrice: $limitPrice,valueAs: ${valueClosure()}"
    }
}



class Stock{
    def order

    Stock(orderObject){
        order = orderObject
    }

    def shares(closure){
        closure = closure.clone()
        closure.delegate = order
        closure()
        order
    }
}

@Category(Integer)
class StockCategory {

    static Stock getGOOG(Integer self) {
        new Stock(new Order("GOOG", self))
    }

    static Stock IBM(Integer self) {
        new Stock(new Order("IBM", self))
    }

    static Stock getMSOFT(Integer self) {
        new Stock(new Order("MSOFT", self))
    }
}


@Category(Integer)
class OrderCategory{
    static void buy(Script self,Order o){
        println  "Buy: $o"
    }
    static void sell(Script self,Order o){
        println  "Sell: $o"
    }
}



use(OrderCategory, StockCategory) {
    buy 200.IBM().shares {
        limitPrice 300
        allOrNone()
        valueAs {qty * unitPrice - 500}
    }
}
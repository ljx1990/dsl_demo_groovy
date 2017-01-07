// Listing 4.7 Trade abstraction in Groovy
// For dynamic builders and the DSL script of listign 4.8 have a look at the script embedded in the pom

package com.hakim.dsl.metaprogram.trade

class Trade {
    String refNo
    Account account
    Instrument instrument
    List<Taxfee> taxfees = []
}

class Account {
    String no
    String name
    String type
}

class Instrument {
    String isin
    String type
    String name
}

class Taxfee {
    String taxId
    BigDecimal value
}


def builder = new ObjectGraphBuilder()
builder.classNameResolver = "com.hakim.dsl.metaprogram.trade"
builder.classLoader = getClass().classLoader
def trd = builder.trade(refNo:"TRD-123"){
    account(no:"ACC-123",name:"Joe Doe",type: "TRADING")
    instrument(isin:"INS=123",type:"EQUITY",name:"IBM Stock")
    3.times {
        taxfee(taxId:"Task ${it}",value :BigDecimal.valueOf(100))
    }
}
assert  trd !=null
assert trd.account.name =="Joe Doe"
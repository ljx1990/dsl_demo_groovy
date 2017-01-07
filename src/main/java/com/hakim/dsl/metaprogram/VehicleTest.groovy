package com.hakim.dsl.metaprogram

/**
 * Created by lvjxt on 2017/1/7.
 * 灵巧API
 */
interface Vehicle {
    int getPrincipal()
    int getValue();
}

@Category(Vehicle) class TaxFee {
    def taxFee() {
        value+principal*0.2
        this
    }
}

@Category(Vehicle) class Comisson {
    def comisson() {
       value - principal*0.2
        this
    }
}

class JamesBondVehicle implements Vehicle {
    JamesBondVehicle(int principal) {
        this.principal = principal
        value = principal;
    }
    int value;
    int principal;
    int getPrincipal() {  principal }
    int getValue() { value }
}

use ([TaxFee, Comisson]) {
    println( JamesBondVehicle.newInstance(200).with {
        taxFee() comisson() getValue()
    });
}


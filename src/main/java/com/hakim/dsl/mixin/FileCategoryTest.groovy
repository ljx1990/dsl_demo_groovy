package com.hakim.dsl.mixin

/**
 * Created by hd on 2017/1/7.
 */
//  创建一个Category，以重用该类中的静态方法的实现
class  FileCategory {
    //  实现操作符(<<)重载（ << 对应的方法为leftShift ）
    static  leftShift(self, other) {
        //  将"Hello, "以及other中的内容写入self表示的文件中
        self.write( " Hello, $other " )
        println  " done! "
    }
}

//  利用关键字use，使用之前创建的Category
use (FileCategory) {
    //  创建File的一个实例， 并将"Hello, 山风小子"写入该文件中，注意不带双引号
    new  File( " hello.txt " )  <<   " 山风小子 "
}
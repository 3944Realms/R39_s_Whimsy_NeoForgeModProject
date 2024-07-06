package com.r3944realms.whimsy.api.SpringBoot

import com.r3944realms.whimsy.WhimsyMod

class Actor {
     int age
     String name
     boolean gender
     Actor(age, name, gender){
          this.age = age
          this.name = name
          this.gender = gender
     }
}
static def sum(price1, price2) {
     "$price1 + $price2 = ${price1 + price2}"
}
String str = "2"
def str2 = "str$str"
Actor actor
actor = new Actor(2,"2",true)
println actor.getClass().toString()

println str2.getClass().toString()
/*println(WhimsyMod.MOD_ID+'''
2324\
2324
32
''' +
(sum 10,2))
*/
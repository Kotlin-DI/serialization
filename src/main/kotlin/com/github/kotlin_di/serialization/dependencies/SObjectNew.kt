package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.IDependency
import com.github.kotlin_di.common.`object`.UObject
import com.github.kotlin_di.ioc.Dependency
import com.github.kotlin_di.ioc.cast
import com.github.kotlin_di.serialization.SObject

@IDependency("SObject.new")
class SObjectNew : Dependency {

    override fun invoke(args: Array<out Any>): UObject {
        val map: MutableMap<String, Any> = if (args.size == 1) {
            cast(args[0])
        } else {
            HashMap()
        }
        return SObject(map)
    }
}

package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.IDependency
import com.github.kotlin_di.common.`object`.UObject
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.serialization.SObject

@IDependency("SObject")
fun newSObject(args: Option<MutableMap<String, Any>>): UObject {
    val map = when (args) {
        is None -> HashMap()
        is Some -> args.value
    }
    return SObject(map)
}

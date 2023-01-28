package com.github.kotlin_di.serialization

import com.github.kotlin_di.common.interfaces.UObject

class SObject(internal val map: MutableMap<String, Any>) : UObject {

    override fun get(key: String, orElse: () -> Any): Any {
        return map.getOrElse(key, orElse)
    }

    override fun set(key: String, value: Any) {
        map[key] = value
    }

    override fun toString(): String {
        return map.toString()
    }
}

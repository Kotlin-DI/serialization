package com.github.kotlin_di.serialization

import com.github.kotlin_di.common.interfaces.UObject
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.resolve

fun deserializeSample() {
    val json = """{"prop1": "value","prop2": {"nested": "value"},"prop3":[1,2] }"""
    val obj = resolve(Serialization.DESERIALIZE, json) as UObject
}

fun serializeSample() {
    val data = mutableMapOf<String, Any>(
        "key1" to "value1",
        "key2" to "value2"
    )
    val obj = resolve(Serialization.SOBJECT, Some(data))
    val json = resolve(Serialization.SERIALIZE, obj)
    // json: { "key1": "value1", "key2": "value2"}
}

fun sObjectSample() {
    val data = mutableMapOf<String, Any>(
        "key1" to "value1",
        "key2" to "value2"
    )
    val obj = resolve(Serialization.SOBJECT, Some(data))
    obj["key1"] == "value1"
}

package com.github.kotlinDI.test

import com.github.kotlinDI.common.interfaces.UObject
import com.github.kotlinDI.resolve
import com.github.kotlinDI.serialization.Serialization
import com.github.kotlinDI.serialization.SerializationPlugin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeserializationTest {

    @BeforeEach
    fun init() {
        SerializationPlugin().load()
    }

    @Test
    fun deserializeObject() {
        val obj = resolve(
            Serialization.DESERIALIZE,
            """{"prop1": "value","prop2": {"nested": "value"},"prop3":[1,2] }"""
        ) as UObject
        assertEquals("value", obj["prop1"])
        assertEquals("value", (obj["prop2"] as UObject)["nested"])
        assertEquals(1, (obj["prop3"] as List<*>)[0])
        assertEquals(2, (obj["prop3"] as List<*>)[1])
    }

    @Test
    fun deserializeArray() {
        val list = resolve(Serialization.DESERIALIZE, """[1,2,3]""") as List<Int>
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
    }
}
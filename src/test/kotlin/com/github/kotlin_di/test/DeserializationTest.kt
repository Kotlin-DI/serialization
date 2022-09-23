package com.github.kotlin_di.test

import com.github.kotlin_di.common.`object`.UObject
import com.github.kotlin_di.generated.serialization
import com.github.kotlin_di.generated.serializationPlugin
import com.github.kotlin_di.resolve
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeserializationTest {

    @BeforeEach
    fun init() {
        serializationPlugin().load()
    }

    @Test
    fun deserializeObject() {
        val obj = resolve(serialization.DESERIALIZE, """{"prop1": "value","prop2": {"nested": "value"},"prop3":[1,2] }""") as UObject
        assertEquals("value", obj["prop1"])
        assertEquals("value", (obj["prop2"] as UObject)["nested"])
        assertEquals(1, (obj["prop3"] as List<*>)[0])
        assertEquals(2, (obj["prop3"] as List<*>)[1])
    }

    @Test
    fun deserializeArray() {
        val list = resolve(serialization.DESERIALIZE, """[1,2,3]""") as List<Int>
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
    }
}

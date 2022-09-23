package com.github.kotlin_di.test

import com.github.kotlin_di.generated.serialization
import com.github.kotlin_di.generated.serializationPlugin
import com.github.kotlin_di.resolve
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SerializationTest {
    @BeforeEach
    fun init() {
        serializationPlugin().load()
    }

    @Test
    fun serializeObject() {
        val obj = resolve(serialization.SOBJECT_NEW)
        val nested = resolve(serialization.SOBJECT_NEW)
        nested["key"] = "value"
        obj["prop1"] = arrayOf(1, 2, 3)
        obj["prop2"] = nested
        obj["prop3"] = 2.5f

        val json = resolve(serialization.SERIALIZE, obj)
        Assertions.assertEquals("""{"prop2":{"key":"value"},"prop1":[1,2,3],"prop3":2.5}""", json)
    }

    @Test
    fun serializeArray() {
        val json = resolve(serialization.SERIALIZE, 1, 2, 3)
        Assertions.assertEquals("""[1,2,3]""", json)
    }
}

package com.github.kotlinDI.test

import com.github.kotlinDI.resolve
import com.github.kotlinDI.serialization.Serialization
import com.github.kotlinDI.serialization.SerializationPlugin
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SerializationTest {
    @BeforeEach
    fun init() {
        SerializationPlugin().load()
    }

    @Test
    fun serializeObject() {
        val obj = resolve(Serialization.SOBJECT)
        val nested = resolve(Serialization.SOBJECT)
        nested["key"] = "value"
        obj["prop1"] = arrayOf(1, 2, 3)
        obj["prop2"] = nested
        obj["prop3"] = 2.5f

        val json = resolve(Serialization.SERIALIZE, obj)
        Assertions.assertEquals("""{"prop2":{"key":"value"},"prop1":[1,2,3],"prop3":2.5}""", json)
    }

    @Test
    fun serializeArray() {
        val json = resolve(Serialization.SERIALIZE, arrayOf(1, 2, 3))
        Assertions.assertEquals("""[1,2,3]""", json)
    }
}
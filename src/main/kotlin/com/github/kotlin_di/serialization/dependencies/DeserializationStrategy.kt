package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.IDependency
import com.github.kotlin_di.common.`object`.UObject
import com.github.kotlin_di.generated.serialization
import com.github.kotlin_di.ioc.Dependency
import com.github.kotlin_di.ioc.cast
import com.github.kotlin_di.resolve
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*

@IDependency("Deserialize", Any::class)
class DeserializationStrategy : Dependency {

    private fun objectStrategy(obj: JsonObject): UObject {
        val result = HashMap<String, Any>()
        obj.entries.forEach {
            result[it.key] = anyStrategy(it.value)
        }
        return resolve(serialization.SOBJECT_NEW, result)
    }

    private fun arrayStrategy(element: JsonArray): Iterable<Any> {
        return element.map {
            anyStrategy(it)
        }.toMutableList()
    }

    private fun primitiveStrategy(element: JsonPrimitive): Any {
        if (element.intOrNull !== null) {
            return Json.decodeFromJsonElement(Int.serializer(), element)
        }
        if (element.longOrNull !== null) {
            return Json.decodeFromJsonElement(Long.serializer(), element)
        }
        if (element.booleanOrNull !== null) {
            return Json.decodeFromJsonElement(Boolean.serializer(), element)
        }
        if (element.floatOrNull !== null) {
            return Json.decodeFromJsonElement(Float.serializer(), element)
        }
        if (element.doubleOrNull !== null) {
            return Json.decodeFromJsonElement(Double.serializer(), element)
        }
        return Json.decodeFromJsonElement(String.serializer(), element)
    }

    private fun anyStrategy(element: JsonElement): Any {
        return when (element) {
            is JsonObject -> objectStrategy(element)
            is JsonArray -> arrayStrategy(element)
            is JsonPrimitive -> primitiveStrategy(element)
            is JsonNull -> "null"
        }
    }

    override fun invoke(args: Array<out Any>): Any {
        val jsonString: String = cast(args[0])
        val element = Json.parseToJsonElement(jsonString)
        return anyStrategy(element)
    }
}

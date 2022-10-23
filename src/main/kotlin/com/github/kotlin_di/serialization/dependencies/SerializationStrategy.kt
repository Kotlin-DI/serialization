package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.IDependency
import com.github.kotlin_di.common.types.Dependency
import com.github.kotlin_di.serialization.SObject
import kotlinx.serialization.json.*

@IDependency("Serialize")
class SerializationStrategy : Dependency<Any, String> {

    private fun objectStrategy(obj: SObject): JsonObject {
        val map = mutableMapOf<String, JsonElement>()
        obj.map.entries.forEach {
            map[it.key] = anyStrategy(it.value)
        }
        return JsonObject(map)
    }

    private fun arrayStrategy(iterable: Array<*>): JsonArray {
        val list = mutableListOf<JsonElement>()
        iterable.forEach {
            list.add(anyStrategy(it))
        }
        return JsonArray(list)
    }

    private fun listStrategy(iterable: Iterable<*>): JsonArray {
        val list = mutableListOf<JsonElement>()
        iterable.forEach {
            list.add(anyStrategy(it))
        }
        return JsonArray(list)
    }

    private fun primitiveStrategy(primitive: Any?): JsonPrimitive {
        return when (primitive) {
            is Number -> JsonPrimitive(primitive)
            is Boolean -> JsonPrimitive(primitive)
            is String -> JsonPrimitive(primitive)
            else -> {
                JsonNull
            }
        }
    }

    private fun anyStrategy(element: Any?): JsonElement {
        return when (element) {
            is SObject -> objectStrategy(element)
            is Array<*> -> arrayStrategy(element)
            is List<*> -> listStrategy(element)
            else -> {
                primitiveStrategy(element)
            }
        }
    }

    override fun invoke(args: Any): String {
        return anyStrategy(args).toString()
    }
}

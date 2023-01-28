package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.IDependency
import com.github.kotlin_di.common.interfaces.UObject
import com.github.kotlin_di.common.types.None
import com.github.kotlin_di.common.types.Option
import com.github.kotlin_di.common.types.Some
import com.github.kotlin_di.serialization.SObject

/** create new instance of Serializable UObject
 * @param args optional map of initial data
 * @sample com.github.kotlin_di.serialization.sObjectSample
 */
@IDependency("SObject")
fun newSObject(args: Option<MutableMap<String, Any>>): UObject {
    val map = when (args) {
        is None -> HashMap()
        is Some -> args.value
    }
    return SObject(map)
}

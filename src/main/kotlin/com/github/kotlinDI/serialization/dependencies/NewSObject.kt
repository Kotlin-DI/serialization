package com.github.kotlinDI.serialization.dependencies

import com.github.kotlinDI.common.annotations.IDependency
import com.github.kotlinDI.common.interfaces.Dependency
import com.github.kotlinDI.common.interfaces.UObject
import com.github.kotlinDI.common.types.None
import com.github.kotlinDI.common.types.Option
import com.github.kotlinDI.common.types.Some
import com.github.kotlinDI.serialization.SObject

/** create new instance of Serializable UObject
 * @param args optional map of initial data
 * @sample com.github.kotlinDI.serialization.sObjectSample
 */
@IDependency("SObject")
class NewSObject : Dependency<Option<MutableMap<String, Any>>, UObject> {
    override fun invoke(args: Option<MutableMap<String, Any>>): UObject {
        return SObject(
            when (args) {
                is None -> HashMap()
                is Some -> args.value
            }
        )
    }
}
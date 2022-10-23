package com.github.kotlin_di.serialization.dependencies

import com.github.kotlin_di.common.annotations.ICommand

@ICommand
fun CMD(a: Int) {
    println(a)
}

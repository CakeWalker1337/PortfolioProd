package com.retroblade.portfolioprod.utils.ui

/**
 * @author m.a.kovalev
 */

fun Triple<List<Any>, List<Any>, List<Any>>.isNotEmpty(): Boolean {
    return this.first.isNotEmpty() && this.second.isNotEmpty() && this.third.isNotEmpty()
}

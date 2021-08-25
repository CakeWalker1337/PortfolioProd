package com.retroblade.hirasawaprod.content.data.entity.db

/**
 * @author m.a.kovalev
 */
enum class PhotoType(val value: String) {
    PAGER("PAGER"),
    CONTENT("CONTENT"),
    UNKNOWN("UNKNOWN");

    companion object {

        fun from(value: String) = values().find { it.value == value } ?: UNKNOWN
    }
}
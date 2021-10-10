package com.retroblade.hirasawaprod.content.data.entity.db

/**
 * The enum class represents type of photo based on where it was received from
 */
enum class PhotoType(val value: String) {
    PAGER("PAGER"),
    CONTENT("CONTENT"),
    UNKNOWN("UNKNOWN");

    companion object {

        /**
         * Seeks the item in enum class by its [value]
         */
        fun from(value: String) = values().find { it.value == value } ?: UNKNOWN
    }
}
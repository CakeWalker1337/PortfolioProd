package com.retroblade.portfolioprod.utils

import org.joda.time.DateTime

/**
 * Util class contains methods for working with dates
 */
object DateUtils {

    /**
     * Calculates the integer number of years since [fromTime] timestamp
     * @return number of years
     */
    fun getYearsTillNow(fromTime: Long): Int {
        return DateTime.now().year - DateTime(fromTime).year
    }
}

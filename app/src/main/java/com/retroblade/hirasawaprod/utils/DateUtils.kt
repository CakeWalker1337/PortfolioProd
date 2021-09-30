package com.retroblade.hirasawaprod.utils

import com.retroblade.hirasawaprod.BuildConfig
import org.joda.time.DateTime

/**
 * @author m.a.kovalev
 */
fun getYearsOfExperienceTillNow(): Int {
    return DateTime.now().year - DateTime(BuildConfig.EXPERIENCE_START_DATE).year
}
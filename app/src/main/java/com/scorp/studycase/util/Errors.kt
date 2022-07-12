package com.scorp.studycase.util

import com.scorp.studycase.R
import javax.inject.Inject

class Errors @Inject constructor() {

    val INTERNAL_SERVER_ERROR: Int = 1001
    val PARAMETER_ERROR = 1002
    val NO_DATA_ERROR = 1003
    val NO_NEW_DATA_ERROR = 1004
    val UNKNOWN_ERROR = 1005

    private val errors: Map<Int, Int> = mapOf(
        INTERNAL_SERVER_ERROR to R.string.internal_server_error,
        PARAMETER_ERROR to R.string.parameter_error,
        NO_DATA_ERROR to R.string.no_data,
        NO_NEW_DATA_ERROR to R.string.no_new_data
    )

    fun getErrorMessage(code: Int): Int {
        var retValue = UNKNOWN_ERROR
        if (errors.containsKey(code)) {
            retValue = errors[code]!!
        }
        return retValue
    }
}
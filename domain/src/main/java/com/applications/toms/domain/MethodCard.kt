package com.applications.toms.domain

import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction

data class MethodCard (
    val method: Method,
    val name: String,
    val icon: Int,
    val icon_description: String,
    val action: UserAction
)
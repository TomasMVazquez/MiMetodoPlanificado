package com.applications.toms.domain

data class MethodCard (
    val name: String,
    val icon: Int,
    val icon_description: String,
    val action: UserAction
)
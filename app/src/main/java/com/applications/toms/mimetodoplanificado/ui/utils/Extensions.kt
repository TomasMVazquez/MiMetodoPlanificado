package com.applications.toms.mimetodoplanificado.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toFormattedString(): String = format(DateTimeFormatter.ofPattern("dd/MM/yy"))
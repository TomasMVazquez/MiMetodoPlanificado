package com.applications.toms.mimetodoplanificado.ui.utils.methods

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.R

const val TOTAL_CYCLE_DAYS = 28L
const val CYCLE_21_DAYS = 21L
const val CYCLE_7_DAYS = 7L
const val CYCLE_30_DAYS = 30L
const val CYCLE_90_DAYS = 90L
const val CYCLE_DAY_21 = 21L
const val CYCLE_DAY_25 = 25L
const val CYCLE_DAY_29 = 29L

val methods: List<MethodCard>
    @Composable get() = listOf(
        MethodCard(
            method = Method.PILLS,
            name = stringResource(R.string.pills),
            icon = R.drawable.ic_pastillas,
            icon_description = stringResource(R.string.content_description_pills),
            action = UserAction.PILLS
        ),
        MethodCard(
            method = Method.RING,
            name = stringResource(R.string.ring),
            icon = R.drawable.ic_circulo,
            icon_description = stringResource(R.string.content_description_ring),
            action = UserAction.RING
        ),
        MethodCard(
            method = Method.PATCH,
            name = stringResource(R.string.patch),
            icon = R.drawable.ic_parche,
            icon_description = stringResource(R.string.content_description_patch),
            action = UserAction.PATCH
        ),
        MethodCard(
            method = Method.SHOOT,
            name = stringResource(R.string.injection),
            icon = R.drawable.ic_jeringuilla,
            icon_description = stringResource(R.string.content_description_injection),
            action = UserAction.SHOOT
        ),
        MethodCard(
            method = Method.CYCLE,
            name = stringResource(R.string.cycle),
            icon = R.drawable.ic_cycle,
            icon_description = stringResource(R.string.content_description_cycle),
            action = UserAction.CYCLE
        )
    )
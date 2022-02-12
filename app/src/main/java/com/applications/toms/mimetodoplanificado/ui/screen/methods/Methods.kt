package com.applications.toms.mimetodoplanificado.ui.screen.methods

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.R

const val TOTAL_CYCLE_DAYS = 28L
const val RING_CYCLE = 21L

val methods: List<MethodCard>
    @Composable get() = listOf(
        MethodCard(
            name = stringResource(R.string.pills),
            icon = R.drawable.ic_pastillas,
            icon_description = stringResource(R.string.content_description_pills),
            action = UserAction.PILLS
        ),
        MethodCard(
            name = stringResource(R.string.ring),
            icon = R.drawable.ic_circulo,
            icon_description = stringResource(R.string.content_description_ring),
            action = UserAction.RING
        ),
        MethodCard(
            name = stringResource(R.string.patch),
            icon = R.drawable.ic_parche,
            icon_description = stringResource(R.string.content_description_patch),
            action = UserAction.PATCH
        ),
        MethodCard(
            name = stringResource(R.string.injection),
            icon = R.drawable.ic_jeringuilla,
            icon_description = stringResource(R.string.content_description_injection),
            action = UserAction.SHOOT
        ),
    )
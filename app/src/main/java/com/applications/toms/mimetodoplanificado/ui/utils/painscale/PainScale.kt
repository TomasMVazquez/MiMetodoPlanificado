package com.applications.toms.mimetodoplanificado.ui.utils.painscale

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.mimetodoplanificado.R

val painScaleCards: List<PainScaleCard>
    @Composable get() = listOf(
        PainScaleCard(
            painScale = 0,
            name = stringResource(R.string.pain_scale_title_0),
            icon = R.drawable.ic_pain_scale_1,
            icon_description = stringResource(R.string.pain_scale_cont_desc_0)
        ),
        PainScaleCard(
            painScale = 1,
            name = stringResource(R.string.pain_scale_title_1),
            icon = R.drawable.ic_pain_scale_1,
            icon_description = stringResource(R.string.pain_scale_cont_desc_1)
        ),
        PainScaleCard(
            painScale = 2,
            name = stringResource(R.string.pain_scale_title_2),
            icon = R.drawable.ic_pain_scale_2,
            icon_description = stringResource(R.string.pain_scale_cont_desc_2)
        ),
        PainScaleCard(
            painScale = 3,
            name = stringResource(R.string.pain_scale_title_3),
            icon = R.drawable.ic_pain_scale_2,
            icon_description = stringResource(R.string.pain_scale_cont_desc_3)
        ),
        PainScaleCard(
            painScale = 4,
            name = stringResource(R.string.pain_scale_title_4),
            icon = R.drawable.ic_pain_scale_3,
            icon_description = stringResource(R.string.pain_scale_cont_desc_4)
        ),
        PainScaleCard(
            painScale = 5,
            name = stringResource(R.string.pain_scale_title_5),
            icon = R.drawable.ic_pain_scale_4,
            icon_description = stringResource(R.string.pain_scale_cont_desc_5)
        ),
        PainScaleCard(
            painScale = 6,
            name = stringResource(R.string.pain_scale_title_6),
            icon = R.drawable.ic_pain_scale_4,
            icon_description = stringResource(R.string.pain_scale_cont_desc_6)
        ),
        PainScaleCard(
            painScale = 7,
            name = stringResource(R.string.pain_scale_title_7),
            icon = R.drawable.ic_pain_scale_5,
            icon_description = stringResource(R.string.pain_scale_cont_desc_7)
        ),
        PainScaleCard(
            painScale = 8,
            name = stringResource(R.string.pain_scale_title_8),
            icon = R.drawable.ic_pain_scale_5,
            icon_description = stringResource(R.string.pain_scale_cont_desc_8)
        )
    )

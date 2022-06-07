package com.applications.toms.mimetodoplanificado.ui.utils.moods

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.MoodCard
import com.applications.toms.mimetodoplanificado.R

val moodCards: List<MoodCard>
    @Composable get() = listOf(
        MoodCard(
            painScale = 0,
            name = stringResource(R.string.pain_scale_title_0),
            icon = R.drawable.ic_pain_scale_1,
            icon_description = stringResource(R.string.pain_scale_cont_desc_0)
        ),
        MoodCard(
            painScale = 1,
            name = stringResource(R.string.pain_scale_title_1),
            icon = R.drawable.ic_pain_scale_1,
            icon_description = stringResource(R.string.pain_scale_cont_desc_1)
        ),
        MoodCard(
            painScale = 2,
            name = stringResource(R.string.pain_scale_title_2),
            icon = R.drawable.ic_pain_scale_2,
            icon_description = stringResource(R.string.pain_scale_cont_desc_2)
        ),
        MoodCard(
            painScale = 3,
            name = stringResource(R.string.pain_scale_title_3),
            icon = R.drawable.ic_pain_scale_2,
            icon_description = stringResource(R.string.pain_scale_cont_desc_3)
        ),
        MoodCard(
            painScale = 4,
            name = stringResource(R.string.pain_scale_title_4),
            icon = R.drawable.ic_pain_scale_3,
            icon_description = stringResource(R.string.pain_scale_cont_desc_4)
        ),
        MoodCard(
            painScale = 5,
            name = stringResource(R.string.pain_scale_title_5),
            icon = R.drawable.ic_pain_scale_4,
            icon_description = stringResource(R.string.pain_scale_cont_desc_5)
        ),
        MoodCard(
            painScale = 6,
            name = stringResource(R.string.pain_scale_title_6),
            icon = R.drawable.ic_pain_scale_4,
            icon_description = stringResource(R.string.pain_scale_cont_desc_6)
        ),
        MoodCard(
            painScale = 7,
            name = stringResource(R.string.pain_scale_title_7),
            icon = R.drawable.ic_pain_scale_5,
            icon_description = stringResource(R.string.pain_scale_cont_desc_7)
        ),
        MoodCard(
            painScale = 8,
            name = stringResource(R.string.pain_scale_title_8),
            icon = R.drawable.ic_pain_scale_5,
            icon_description = stringResource(R.string.pain_scale_cont_desc_8)
        )
    )

package com.applications.toms.mimetodoplanificado.ui.components.painscale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.PainScaleImage
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString

@ExperimentalMaterialApi
@Composable
fun CardPainHistory(
    modifier: Modifier = Modifier,
    painScaleModel: PainScaleModel,
    painScaleCard: PainScaleCard,
    elevation: Dp = 1.dp,
    onClick: (() -> Unit)? = null
) {
    Card(
        onClick = { if (onClick != null) onClick() },
        enabled = onClick != null,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = elevation
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.pain_history_card_day, painScaleModel.dayOfCycle.toString()),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = painScaleModel.date.toFormattedString(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }


            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier.weight(0.5f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.border_xxsmall)),
                        text = "${painScaleCard.painScale}. ",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondaryVariant
                    )

                    Text(
                        text = painScaleCard.name,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }

                Box(
                    modifier = Modifier.weight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    PainScaleImage(
                        painScaleIcon = painScaleCard.icon,
                        painScaleContDesc = painScaleCard.icon_description,
                        filterGray = false
                    )
                }
            }
        }
    }
}
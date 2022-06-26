package com.applications.toms.mimetodoplanificado.ui.components.painscale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.PainScaleImage
import com.applications.toms.mimetodoplanificado.ui.theme.CarnationPink

@ExperimentalMaterialApi
@Composable
fun CardPain(
    modifier: Modifier = Modifier,
    painScaleCard: PainScaleCard,
    showPainScaleNumber: Boolean = false,
    elevation: Dp = 1.dp,
    selectedPainScaleCard: Int?,
    onClickCard: ((Int) -> Unit)? = null
) {
    Card(
        onClick = { if (onClickCard != null) onClickCard(painScaleCard.painScale) },
        enabled = onClickCard != null,
        modifier = modifier,
        backgroundColor = if (selectedPainScaleCard == painScaleCard.painScale) CarnationPink
        else MaterialTheme.colors.primary,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_tiny)),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PainScaleImage(
                painScaleIcon = painScaleCard.icon,
                painScaleContDesc = painScaleCard.icon_description,
                filterGray = selectedPainScaleCard == painScaleCard.painScale
            )

            Row(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showPainScaleNumber) {
                    Text(
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.border_xxsmall)),
                        text = "${painScaleCard.painScale}. ",
                        style = MaterialTheme.typography.overline,
                        color = if (selectedPainScaleCard == painScaleCard.painScale) MaterialTheme.colors.secondary
                        else MaterialTheme.colors.secondaryVariant
                    )
                }

                Text(
                    text = painScaleCard.name,
                    style = MaterialTheme.typography.overline,
                    color = if (selectedPainScaleCard == painScaleCard.painScale) MaterialTheme.colors.secondary
                    else MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}
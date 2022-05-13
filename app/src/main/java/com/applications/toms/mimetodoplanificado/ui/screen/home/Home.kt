package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CardButton
import com.applications.toms.mimetodoplanificado.ui.utils.methods.methods

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Home(
    onUserAction: (Method?, UserAction) -> Unit
) {

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    top = dimensionResource(id = R.dimen.no_padding),
                    end = dimensionResource(id = R.dimen.no_padding),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(
                onClick = {
                    onUserAction(null, UserAction.ABOUT_US)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(id = R.string.about_us)
                )
            }
        }

        HomeContent(methods, onUserAction)
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeContent(
    methods: List<MethodCard>,
    onUserAction: (Method, UserAction) -> Unit
) {

    Column(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.no_padding),
            horizontal = dimensionResource(id = R.dimen.padding_large)
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.h3
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(cells = GridCells.Fixed(2)){
            items(methods) {
                CardButton(method = it, onClick = onUserAction)
            }
        }
    }

}
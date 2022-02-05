package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CardButton
import com.applications.toms.mimetodoplanificado.ui.screen.methods.methods

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Home(onUserAction: (UserAction) -> Unit){

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    top = dimensionResource(id = R.dimen.no_padding),
                    end = dimensionResource(id = R.dimen.no_padding),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(
                onClick = {
                    onUserAction(UserAction.ABOUT_US_CLICK)
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
fun HomeContent(methods: List<MethodCard>, onUserAction: (UserAction) -> Unit) {

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
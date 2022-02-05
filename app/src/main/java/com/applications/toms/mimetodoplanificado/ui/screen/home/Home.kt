package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.ui.navigation.UserAction
import com.applications.toms.mimetodoplanificado.R

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

        HomeContent(onUserAction)
    }
}


@Composable
fun HomeContent(onUserAction: (UserAction) -> Unit) {
    Column(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_large))) {

    }
}
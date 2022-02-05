package com.applications.toms.mimetodoplanificado.ui.screen.aboutus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.LinkText
import com.applications.toms.mimetodoplanificado.ui.components.LinkTextData
import com.applications.toms.mimetodoplanificado.ui.components.SpacerType

@Composable
fun AboutUs(goBack: () -> Unit) {

    val uriHandler = LocalUriHandler.current

    Column() {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_medium))
        ) {

            item {
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_small)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(id = R.string.content_description_icon),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.icon_height)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                )
            }

            item {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Text(
                    text = stringResource(R.string.description_about_me),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Text(
                    text = stringResource(R.string.description_about_app),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }

            item {
                Text(
                    text = stringResource(R.string.title_attribution),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }

            item {
                Text(
                    text = stringResource(R.string.desc_attribution),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_xsmall)
                )
            }

            item {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData(text = "${stringResource(id = R.string.flaticon_attribution_start)} "),
                        LinkTextData(
                            text = "${stringResource(id = R.string.flaticon_attribution_freepik)} ",
                            tag = stringResource(id = R.string.flaticon_attribution_freepik),
                            annotation = stringResource(id = R.string.flaticon_attribution_freepik_link),
                            onClick = {
                                uriHandler.openUri(it.item)
                            }
                        ),
                        LinkTextData(text = "${stringResource(id = R.string.flaticon_attribution_from)} "),
                        LinkTextData(
                            text = stringResource(id = R.string.flaticon_attribution_title),
                            tag = stringResource(id = R.string.flaticon_attribution_title),
                            annotation = stringResource(id = R.string.flaticon_attribution_link),
                            onClick = {
                                uriHandler.openUri(it.item)
                            }
                        )
                    )
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_small)
                )
            }

            item {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData(text = "${stringResource(id = R.string.imgs_blob_start)} "),
                        LinkTextData(
                            text = "${stringResource(id = R.string.imgs_blob_title)} ",
                            tag = stringResource(id = R.string.imgs_blob_title),
                            annotation = stringResource(id = R.string.imgs_blob_link),
                            onClick = {
                                uriHandler.openUri(it.item)
                            }
                        )
                    )
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_medium)
                )
            }

            item {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData(text = "${stringResource(id = R.string.imgs_itg_start)} "),
                        LinkTextData(
                            text = "${stringResource(id = R.string.imgs_itg_title)} ",
                            tag = stringResource(id = R.string.imgs_itg_title),
                            annotation = stringResource(id = R.string.imgs_itg_link),
                            onClick = {
                                uriHandler.openUri(it.item)
                            }
                        )
                    )
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_medium)
                )
            }

            item {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData(text = "${stringResource(id = R.string.play_store_apps_start)} "),
                        LinkTextData(
                            text = "${stringResource(id = R.string.play_store_apps_title)} ",
                            tag = stringResource(id = R.string.play_store_apps_title),
                            annotation = stringResource(id = R.string.play_store_apps_link),
                            onClick = {
                                uriHandler.openUri(it.item)
                            }
                        )
                    )
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_medium)
                )
            }
        }
    }

}
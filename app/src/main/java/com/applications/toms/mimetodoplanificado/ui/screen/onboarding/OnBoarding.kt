package com.applications.toms.mimetodoplanificado.ui.screen.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.applications.toms.domain.OnBoardingPage
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import com.applications.toms.mimetodoplanificado.ui.utils.onBoardingHasFinished
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoarding(
    onGettingStartedClick: () -> Unit,
    onSkipClicked: () -> Unit
) {

    val onboardPages = listOf(
        OnBoardingPage(
            title = stringResource(R.string.obg_title_choose),
            description = stringResource(R.string.obg_desc_choose),
            image = R.drawable.obg_choose,
            img_desc = stringResource(R.string.obg_img_desc_choose)
        ),
        OnBoardingPage(
            title = stringResource(R.string.obg_title_set),
            description = stringResource(R.string.obg_desc_set),
            image = R.drawable.obg_set,
            img_desc = stringResource(R.string.obg_img_desc_set)
        ),
        OnBoardingPage(
            title = stringResource(R.string.obg_title_relax),
            description = stringResource(R.string.obg_desc_relax),
            image = R.drawable.obg_relax,
            img_desc = stringResource(R.string.obg_img_desc_relax)
        )
    )

    if (hasOnBoardingAlreadyShown(LocalContext.current)) onGettingStartedClick()

    OnBoardingContent(onboardPages, onSkipClicked, onGettingStartedClick)
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
private fun OnBoardingContent(
    onboardPages: List<OnBoardingPage>,
    onSkipClicked: () -> Unit,
    onGettingStartedClick: () -> Unit
) {

    val context = LocalContext.current
    val pagerState = rememberPagerState(0)

    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            GenericButton(
                buttonType = ButtonType.LOW_EMPHASIS,
                text = stringResource(R.string.ong_skip)
            ) {
                onBoardingHasFinished(context)
                onSkipClicked()
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            count = 3
        ) { page ->

            PageUI(page = onboardPages[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.CenterHorizontally),
            activeColor = VividRaspberry,
            inactiveColor = Purple.copy(ContentAlpha.disabled)
        )

        Column(modifier = Modifier.height(dimensionResource(id = R.dimen.button_space_height))) {
            AnimatedVisibility(visible = pagerState.currentPage == 2) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    GenericButton(
                        modifier = Modifier,
                        buttonType = ButtonType.LOW_EMPHASIS,
                        text = stringResource(R.string.obg_btn_start)
                    ) {
                        onBoardingHasFinished(context)
                        onGettingStartedClick()
                    }
                }
            }
        }
    }
}

@Composable
fun PageUI(page: OnBoardingPage) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = page.title,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_xxxlarge)))
        }

        item {
            Image(
                painter = painterResource(page.image),
                contentDescription = page.img_desc,
                modifier = Modifier.size(size = dimensionResource(id = R.dimen.onboarding_image_size))
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))
        }

        item {
            Text(
                text = page.description,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_large)))
        }
    }
}
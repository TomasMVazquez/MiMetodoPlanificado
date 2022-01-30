package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.applications.toms.domain.OnBoardingPage
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.GenericButton
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.onBoardingHasFinished
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoarding(onGettingStartedClick:()->Unit, onSkipClicked:()->Unit) {

    val context = LocalContext.current
    val pagerState = rememberPagerState(0)

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

    Column() {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            GenericButton(
//                modifier = Modifier.padding(16.dp),
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
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = VividRaspberry,
            inactiveColor = Purple.copy(ContentAlpha.disabled)
        )

        AnimatedVisibility(visible = pagerState.currentPage == 2 ) {
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

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Image(
                painter = painterResource(page.image),
                contentDescription = page.img_desc,
                modifier = Modifier.size(size = 200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(
                text = page.description,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
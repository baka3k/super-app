package hi.baka.superapp.feature.movie.person

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baka3k.architecture.core.ui.component.AsyncImageView
import com.baka3k.architecture.core.ui.component.HiTopAppBar
import com.baka3k.architecture.core.ui.component.ShimmerAnimation
import com.baka3k.core.data.movie.model.PhotoSize.Profile.h632
import com.baka3k.core.data.movie.model.asPhotoUrl
import com.baka3k.core.model.Person
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@Composable
fun PersonScreenRouter1(
    viewModel: PersonViewModel = hiltViewModel(), onBackPress: () -> Unit
) {
    val personScreenUIState by viewModel.personUiIState.collectAsStateWithLifecycle()
    PersonScreen(
        personScreenUiIState = personScreenUIState, onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen1(
    personScreenUiIState: PersonScreenUiIState, onBackPress: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (personScreenUiIState) {
            is PersonScreenUiIState.Loading -> {
                ShimmerAnimation()
            }
            is PersonScreenUiIState.Success -> {
                uiPersonView(personScreenUiIState.person)
            }
            is PersonScreenUiIState.Error -> {
                Text(text = "PersonScreenUiIState.Error")
            }
        }
        HiTopAppBar(
            title = "",
            onBackPress = onBackPress,
            modifier = Modifier.wrapContentHeight(),
            scrollBehavior = scrollBehavior
        )
    }

}

@Composable
fun uiPersonView1(person: Person) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (photo, box, name, story) = createRefs()
        personPhoto(person, Modifier.constrainAs(photo) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        boxTransparency1(Modifier.constrainAs(box) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })
        personName(person = person, modifier = Modifier.constrainAs(name) {
            top.linkTo(parent.top, margin = 20.dp)
            start.linkTo(parent.start, margin = 20.dp)
            end.linkTo(parent.end, margin = 20.dp)
            bottom.linkTo(parent.bottom)
        })
        personStory(person = person, modifier = Modifier.constrainAs(story) {
            top.linkTo(name.bottom, margin = 10.dp)
            start.linkTo(parent.start, margin = 10.dp)
            end.linkTo(parent.end, margin = 10.dp)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
    }
}

@Composable
fun boxTransparency1(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val startGradient = (screenWidth * 0.5).toFloat()
    val endGradient = LocalConfiguration.current.screenHeightDp.toFloat()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    startY = startGradient, endY = endGradient, colors = listOf(
                        Color.Transparent,
                        Color(0, 0, 0, 190),

                        )
                )
            )
    ) {

    }
}

@Composable
private fun personStory(
    person: Person, modifier: Modifier = Modifier
) {
    val modifierReused = Modifier.padding(start = 20.dp, end = 20.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 7.dp)
            .verticalScroll(rememberScrollState())
    ) {
        title(title = "Also Known As")
        if (person.alsoKnownAs.isNotEmpty()) {
            person.alsoKnownAs.forEach {
                content(content = it, modifierReused = modifierReused)
            }
        } else {
            notDefine(modifierReused)
        }
        title(title = person.knownForDepartment)
        content(content = person.placeOfBirth, modifierReused = modifierReused)

        title(title = "Date Of Birth")
        content(content = formatDate(person), modifierReused = modifierReused)

        title(title = "Biography")
        content(content = person.biography, modifierReused = modifierReused)

        title(title = "Home Page")
        content(content = person.homepage, modifierReused = modifierReused)
    }
}

@Composable
private fun title(title: String) {
    Text(text = title, color = Color.White)
}

@Composable
private fun content(content: String, modifierReused: Modifier) {
    if (content.isNotEmpty()) {
        Text(
            text = content,
            color = Color(139, 142, 145),
            style = MaterialTheme.typography.titleSmall,
            modifier = modifierReused
        )
    } else {
        notDefine(modifierReused = modifierReused)
    }
}

@Composable
private fun notDefine(modifierReused: Modifier) {
    Text(
        text = "N/A",
        color = Color(139, 142, 145),
        style = MaterialTheme.typography.titleSmall,
        modifier = modifierReused
    )
}

@Composable
private fun formatDate(person: Person): String {
    return if (person.birthday.isNotEmpty()) {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val date = LocalDate.parse(person.birthday)
            formatter.format(date)
        } catch (e: DateTimeException) {
            ""
        } catch (e: DateTimeParseException) {
            ""
        } catch (e: IllegalArgumentException) {
            ""
        }
    } else {
        ""
    }
}

@Composable
private fun personName(
    person: Person, modifier: Modifier = Modifier
) {
    Text(
        text = person.name,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge.copy(
            color = Color(203, 206, 212), shadow = Shadow(
                color = Color(36, 42, 56), offset = Offset(3.0f, 3.0f), blurRadius = 3f
            )
        )
    )
}

@Composable
private fun personPhoto(
    person: Person, modifier: Modifier = Modifier
) {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0F)
    AsyncImageView(
        data = person.profilePath.asPhotoUrl(h632),
        contentScale = ContentScale.Fit,
        modifier = modifier.fillMaxWidth(),
        colorFilter = ColorFilter.colorMatrix(matrix)
    )
}
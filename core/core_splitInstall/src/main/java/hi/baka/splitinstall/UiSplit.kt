package hi.baka.splitinstall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CancelButton(
    modifier: Modifier = Modifier, label: String = "", onClicked: () -> Unit
) {
    DialogButton(
        modifier = modifier,
        label = {
            Text(
                label,
                fontWeight = FontWeight.Bold,
                color = PurpleGray90,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
        },
        onClicked = onClicked,
    )
}
@Composable
fun DialogButton(
    modifier: Modifier = Modifier, label: @Composable RowScope.() -> Unit, onClicked: () -> Unit
) {
    TextButton(
        onClick = onClicked, modifier = modifier, content = label
    )
}

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier, label: String = "", onClicked: () -> Unit
) {
    DialogButton(
        modifier = modifier,
        label = {
            Text(
                label,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
        },
        onClicked = onClicked,
    )
}
@Composable
fun OptionButton(
    confirmButton: @Composable () -> Unit?,
    cancelButton: @Composable () -> Unit?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Purple80),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        confirmButton()
        cancelButton()
    }
}
@Composable
fun CustomDialogUI(
    title: String,
    mess: String,
    modifier: Modifier = Modifier,
    actionUI: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(modifier.background(Color.White), content = {
            ContentUI(title, mess)
            actionUI()
        })
    }
}

@Composable
private fun ContentUI(title: String, mess: String) {
    Icon(
        imageVector = Icons.Outlined.Notifications,
        contentDescription = "",
        tint = Purple40,
        modifier = Modifier
            .padding(top = 35.dp)
            .height(70.dp)
            .fillMaxWidth(),
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = mess,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun CustomDialog(
    title: String = "",
    mess: String = "",
    openDialogCustom: MutableState<Boolean> = mutableStateOf(true),
    action: @Composable () -> Unit?
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(
            title = title,
            mess = mess,
            actionUI = {
                action()
            })
    }
}

val Purple40 = Color(0xFF8C4190)
val PurpleGray90 = Color(0xFFEDDEE8)
val Purple80 = Color(0xFFFFA8FF)
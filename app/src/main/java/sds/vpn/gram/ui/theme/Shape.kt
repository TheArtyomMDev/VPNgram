package sds.vpn.gram.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import me.nikhilchaudhari.library.shapes.Pressed
import me.nikhilchaudhari.library.shapes.Punched


val roundedLargeShape = RoundedCornerShape(48.dp)
val roundedMediumShape = RoundedCornerShape(24.dp)
val roundedSmallShape = RoundedCornerShape(16.dp)
val roundedSmallShapeTopCorners = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp
)

val punchedLargeShape = Punched.Rounded(48.dp)
val punchedSmallShape = Punched.Rounded(16.dp)

val pressedLargeShape = Pressed.Rounded(48.dp)
val pressedSmallShape = Pressed.Rounded(16.dp)

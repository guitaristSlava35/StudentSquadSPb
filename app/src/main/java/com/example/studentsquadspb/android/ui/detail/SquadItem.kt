package com.example.studentsquadspb.android.ui.detail

import android.R
import android.R.attr.text
import android.widget.Toolbar
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.studentsquadspb.android.data.model.MemberInfo
import com.example.studentsquadspb.android.data.model.MemberSquad
import com.example.studentsquadspb.android.data.model.SquadInfo
import com.example.studentsquadspb.android.data.model.SquadMember

private val AvatarSize = 48.dp

@Composable
fun InitialsAvatar(
    fullname: String,
    modifier: Modifier = Modifier
){
    val initials = fullname.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString(""){it.first().uppercase()}

    Box(
        modifier = Modifier
            .size(AvatarSize)
            .clip(CircleShape)
            .background(Color(0xFFE8EFE6)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = initials,
            color = Color(0xFF1B1B1B),
            fontWeight = FontWeight.Medium
            )

    }
}

@Composable
fun MemberAvatar (
    fullname: String,
    @DrawableRes photo: Int?,
    modifier: Modifier = Modifier
){
    if(photo != null)
    Image(
        painter = painterResource(photo),
        contentDescription = fullname,
        modifier = Modifier
            .size(AvatarSize)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
    else
        InitialsAvatar(fullname, modifier)
}


@Composable
fun InfoField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ){
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(value, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)        )
    }
}


@Composable
fun SquadItem(
    member: SquadMember,
    modifier: Modifier = Modifier,
){


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemberAvatar(fullname = member.fullName, photo = member.photo)
        Spacer(Modifier.width(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = member.fullName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if(member.positions.isNotEmpty()) {
                Text(
                    text = member.positions.joinToString(", ") {it.title},
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }

}
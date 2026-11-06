package com.example.studentsquadspb.android.ui.member

import android.R.attr.onClick
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studentsquadspb.android.data.model.info.SquadInfo

private val EmblemSquadSize = 64.dp

@Composable
fun SquadName(
    name: String,
    modifier: Modifier = Modifier
){
    val initials = name.split(" ")
        .filter { it.isNotBlank() }
        .joinToString { it.first().uppercase() }

    Box(
        modifier = Modifier
            .size(EmblemSquadSize)
            .clip(CircleShape)
            .background(Color(0xFFE8EFE6)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Medium
            )
    }
}

@Composable
fun SquadEmblem(
    name: String,
    @DrawableRes emblem: Int?,
    modifier: Modifier = Modifier
){
    if (emblem != null){
        Image(
            painter = painterResource(emblem),
            contentDescription = name,
            modifier = Modifier
                .size(EmblemSquadSize)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
    else {
        SquadName(name, modifier)
    }
}

@Composable
fun SquadListProfileItem(
    squad: SquadInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(2.dp))
        SquadEmblem(name = squad.name, emblem = squad.emblem)
        Text(
            text = squad.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}
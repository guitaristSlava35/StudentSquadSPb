package com.example.studentsquadspb.android.ui.squadList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.studentsquadspb.android.data.directionLogo
import com.example.studentsquadspb.android.data.model.SquadInfo
import com.example.studentsquadspb.android.ui.theme.directionColor


@Composable
fun SquadListItem(squad: SquadInfo, onClick: () -> Unit, modifier: Modifier = Modifier) {

    val cardColor = directionColor(squad.directionWork)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(squad.emblem),
                contentDescription = squad.name,
                modifier = Modifier
                    .padding(6.dp)
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column{
                Row {
                    Text(
                        text = squad.directionWork,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(2.dp)
                    )

                    Text(
                        text = squad.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(2.dp)
                    )}

                Image(
                    painter = painterResource(directionLogo(squad.directionWork)),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(96.dp),
                    contentScale = ContentScale.Fit
                )

            }

        }

    }
}
package com.example.studentsquadspb.android.ui.headquartersList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.studentsquadspb.android.data.model.info.HeadquartersInfo
import androidx.compose.ui.unit.dp

@Composable
fun HeadquartersListItem (
    headquarters: HeadquartersInfo,
    //onCkick: () -> Unit,
    modifier: Modifier = Modifier
    ) {

    val cardColor = Color(0xFFE7E0EC)

    Card(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = Color.Black,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painter = painterResource(headquarters.emblem),
                contentDescription = headquarters.name,
                modifier = Modifier
                    .padding(6.dp)
                    .width(124.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = modifier.padding(12.dp))
            Text(
                text = "Штаб СО ${headquarters.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(2.dp)
            )
        }
    }

}
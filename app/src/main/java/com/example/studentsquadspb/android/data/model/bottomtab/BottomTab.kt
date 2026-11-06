package com.example.studentsquadspb.android.data.model.bottomtab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.studentsquadspb.android.navigation.Routes

enum class BottomTab(val route: String, val title: String, val icon: ImageVector){
    SQUAD(Routes.LIST, "Отряды", Icons.Default.Groups),
    HEADQUARTERS(Routes.HEADQUARTERS, "Штабы", Icons.Default.AccountBalance),
    RSH(Routes.RSH, "РШ", Icons.Default.Apartment),
    PROFILE(Routes.MY_PROFILE, "Мой профиль", Icons.Default.Person)

}
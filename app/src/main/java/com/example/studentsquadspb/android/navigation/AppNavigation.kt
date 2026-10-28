package com.example.studentsquadspb.android.navigation

import android.adservices.adid.AdId
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studentsquadspb.android.data.FakeSquadRepository
import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.ui.detail.SquadDetailScreen
import com.example.studentsquadspb.android.ui.squadList.SquadListScreen

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail"
    const val ARG_SQUAD_ID = "squadId"
    const val DETAIL_PATTERN = "$DETAIL/{$ARG_SQUAD_ID}"

    fun detail(squadId: Int) = "$DETAIL/$squadId"
}

@Composable
fun AppNavigation(
    repository: SquadRepository = FakeSquadRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {

        // Экран 1: список отрядов
        composable(Routes.LIST) {
            SquadListScreen(
                squads = repository.getSquads(),
                onAppClick = { squad ->
                    navController.navigate(Routes.detail(squad.id))
                }
            )
        }

        // Экран 2: профиль отряда
        composable(
            route = Routes.DETAIL_PATTERN,
            arguments = listOf(
                navArgument(Routes.ARG_SQUAD_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Routes.ARG_SQUAD_ID)
            val squad = id?.let { repository.getSquad(it) }

            if (squad != null) {
                SquadDetailScreen(
                    squad = squad,
                    members = repository.getMembers(squad.id),
                    onBack = { navController.popBackStack() }
                )
            } else {
                // Отряд не найден: возвращаемся к списку
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}
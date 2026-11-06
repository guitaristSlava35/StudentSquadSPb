    package com.example.studentsquadspb.android.navigation

    import android.adservices.adid.AdId
    import androidx.compose.foundation.layout.WindowInsets
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.example.studentsquadspb.android.data.FakeSquadRepository
    import com.example.studentsquadspb.android.data.SquadRepository
    import com.example.studentsquadspb.android.data.model.bottomtab.AppBottomBar
    import com.example.studentsquadspb.android.data.model.bottomtab.BottomTab
    import com.example.studentsquadspb.android.ui.StubScreen
    import com.example.studentsquadspb.android.ui.detail.SquadDetailScreen
    import com.example.studentsquadspb.android.ui.member.MemberScreen
    import com.example.studentsquadspb.android.ui.squadList.SquadListScreen
    import com.example.studentsquadspb.android.ui.member.MemberScreen

    object Routes {
        const val LIST = "list"
        const val DETAIL = "detail"
        const val MEMBER = "member"
        const val ARG_SQUAD_ID = "squadId"
        const val ARG_MEMBER_ID = "memberId"
        const val DETAIL_PATTERN = "$DETAIL/{$ARG_SQUAD_ID}"
        const val MEMBER_PATTERN = "$MEMBER/{$ARG_SQUAD_ID}/{$ARG_MEMBER_ID}"

        const val HEADQUARTERS = "headquarters"
        const val RSH = "rsh"
        const val MY_PROFILE = "my_profile"

        fun detail(squadId: Int) = "$DETAIL/$squadId"
        fun member(squadId: Int, memberId: Int) = "$MEMBER/$squadId/$memberId"
    }

    @Composable
    fun AppNavigation(
        repository: SquadRepository = FakeSquadRepository
    ) {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        val showBottomBar = BottomTab.entries.any { it.route == currentRoute }

        Scaffold(
            bottomBar = { if (showBottomBar) AppBottomBar(navController) },
            contentWindowInsets = WindowInsets(0)
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Routes.LIST,
                modifier = Modifier.padding(innerPadding)

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
                            onBack = { navController.popBackStack() },
                            onMemberClick = { member ->
                                navController.navigate(Routes.member(squad.id, member.memberId))
                            }
                        )
                    } else {
                        // Отряд не найден: возвращаемся к списку
                        LaunchedEffect(Unit) { navController.popBackStack() }
                    }
                }

                //Экран 3: Профиль бойца
                composable(
                    route = Routes.MEMBER_PATTERN,
                    arguments = listOf(
                        navArgument(Routes.ARG_SQUAD_ID) { type = NavType.IntType },
                        navArgument(Routes.ARG_MEMBER_ID) { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val squadId = backStackEntry.arguments?.getInt(Routes.ARG_SQUAD_ID)
                    val memberId = backStackEntry.arguments?.getInt(Routes.ARG_MEMBER_ID)

                    val squad = squadId?.let { repository.getSquad(it) }
                    val member = memberId?.let { repository.getMember(it) }

                    if (squad != null && member != null) {
                        MemberScreen(
                            squad = squad,
                            member = member,
                            membersquad = repository.getMemberSquads(member.id),
                            onBack = { navController.popBackStack() },
                            onSquadClick = { target ->
                                if (target.id == squad.id) navController.popBackStack()
                                else navController.navigate(Routes.detail(target.id))
                            }
                        )
                    } else {
                        LaunchedEffect(Unit) { navController.popBackStack() }
                    }
                }

                composable(Routes.HEADQUARTERS) { StubScreen("Штабы") }
                composable(Routes.RSH) { StubScreen("РШ") }
                composable(Routes.MY_PROFILE) { StubScreen("Мой профиль") }

            }
        }
    }
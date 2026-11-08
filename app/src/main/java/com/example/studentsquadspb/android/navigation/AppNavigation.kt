    package com.example.studentsquadspb.android.navigation

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.WindowInsets
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Button
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.currentBackStackEntryAsState
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.example.studentsquadspb.android.data.SquadRepository
    import com.example.studentsquadspb.android.data.model.MemberLoadState
    import com.example.studentsquadspb.android.data.model.MemberViewModel
    import com.example.studentsquadspb.android.data.model.bottomtab.AppBottomBar
    import com.example.studentsquadspb.android.data.model.bottomtab.BottomTab
    import com.example.studentsquadspb.android.ui.StubScreen
    import com.example.studentsquadspb.android.ui.detail.SquadDetailScreen
    import com.example.studentsquadspb.android.ui.detail.SquadDetailState
    import com.example.studentsquadspb.android.ui.detail.SquadDetailViewModel
    import com.example.studentsquadspb.android.ui.member.MemberScreen
    import com.example.studentsquadspb.android.ui.squadList.SquadListScreen
    import com.example.studentsquadspb.android.ui.squadList.SquadListUiState
    import com.example.studentsquadspb.android.ui.squadList.SquadListViewModel


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
                    val viewModel: SquadListViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()

                    when (val s = state) {
                        is SquadListUiState.Loading -> Box(
                            Modifier.fillMaxSize(),
                            Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                        is SquadListUiState.Error -> Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(s.message)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = viewModel::load) { Text("Повторить") }
                        }

                        is SquadListUiState.Success -> SquadListScreen(
                            squads = s.squads,
                            onAppClick = { squad -> navController.navigate(Routes.detail(squad.id)) }
                        )
                    }
                }

                // Экран 2: профиль отряда
                composable(
                    route = Routes.DETAIL_PATTERN,
                    arguments = listOf(navArgument(Routes.ARG_SQUAD_ID) { type = NavType.IntType })
                ) {
                    val viewModel: SquadDetailViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()

                    when (val s = state) {
                        is SquadDetailState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        is SquadDetailState.Error -> Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(s.message)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = viewModel::load) { Text("Повторить") }
                            TextButton(onClick = { navController.popBackStack() }) { Text("Назад") }
                        }
                        is SquadDetailState.Success -> SquadDetailScreen(
                            squad = s.detail.squad,
                            members = s.detail.members,
                            onBack = { navController.popBackStack() },
                            onMemberClick = { member ->
                                navController.navigate(Routes.member(s.detail.squad.id, member.memberId))
                            }
                        )
                    }
                }

                //Экран 3: Профиль бойца
                composable(
                    route = Routes.MEMBER_PATTERN,
                    arguments = listOf(
                        navArgument(Routes.ARG_SQUAD_ID) { type = NavType.IntType },
                        navArgument(Routes.ARG_MEMBER_ID) { type = NavType.IntType }
                    )
                ) {
                    val viewModel: MemberViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()

                    when (val s = state) {
                        is MemberLoadState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        is MemberLoadState.Error -> Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(s.message)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = viewModel::load) { Text("Повторить") }
                            TextButton(onClick = { navController.popBackStack() }) { Text("Назад") }
                        }
                        is MemberLoadState.Success -> MemberScreen(
                            state = s.ui,
                            onBack = { navController.popBackStack() },
                            onSquadClick = { target ->
                                if (target.id == s.ui.squad.id) navController.popBackStack()
                                else navController.navigate(Routes.detail(target.id)) { launchSingleTop = true }
                            }
                        )
                    }
                }

                composable(Routes.HEADQUARTERS) { StubScreen("Штабы") }
                composable(Routes.RSH) { StubScreen("РШ") }
                composable(Routes.MY_PROFILE) { StubScreen("Мой профиль") }

            }
        }
    }
package ml.bubblebath.kmpcrud.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ml.bubblebath.kmpcrud.data.Data
import ml.bubblebath.kmpcrud.viewmodels.MainScreenState
import ml.bubblebath.kmpcrud.viewmodels.MainScreenViewModel

class MainScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MainScreenViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text("KMPCRUD") })
            },
            floatingActionButton = {
                if (uiState is MainScreenState.Success) {
                    FloatingActionButton(onClick = { viewModel.addRandomData() }) {
                        Icon(Icons.Filled.Place, contentDescription = null)
                    }
                }
            }
        ) {
            MainView(
                modifier = Modifier.fillMaxSize().padding(it),
                state = uiState,
                onDataItemClick = { data ->
                    navigator.push(
                        DataDetailsScreen(
                            data,
                            onNavigateBack = {
                                navigator.pop()
                            })
                    )
                },
                onEditClick = { data ->
                    navigator.push(
                        DataEditScreen(
                            data,
                            onNavigateBack = {
                                navigator.pop()
                            },
                            onDeleteDataClicked = {
                                viewModel.deleteData(data)
                                navigator.pop()
                            },
                            onUpdateDataClicked = { latitude, longitude ->
                                viewModel.updateData(data, latitude, longitude)
                                navigator.pop()
                            }
                        )
                    )
                }
            )
        }
    }

    @Composable
    private fun MainView(
        modifier: Modifier = Modifier,
        state: MainScreenState,
        onDataItemClick: (Data) -> Unit,
        onEditClick: (Data) -> Unit
    ) {

        when (state) {
            is MainScreenState.Error -> {
                ErrorView(modifier = modifier)
            }

            is MainScreenState.Loading -> {
                LoadingView(modifier = modifier)
            }

            is MainScreenState.Success -> {
                SuccessView(
                    modifier = modifier,
                    state.dataList,
                    onDataItemClick = onDataItemClick,
                    onEditClick = onEditClick
                )
            }
        }
    }

    @Composable
    private fun ErrorView(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                tint = MaterialTheme.colorScheme.error,
                imageVector = Icons.Filled.Warning,
                contentDescription = null
            )
            Text("Error. Retrying in 5 seconds...")
        }
    }

    @Composable
    private fun LoadingView(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text("Loading")
        }
    }

    @Composable
    private fun SuccessView(
        modifier: Modifier = Modifier,
        data: List<Data>,
        onDataItemClick: (Data) -> Unit,
        onEditClick: (Data) -> Unit
    ) {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = data, key = { it.uuid }) {
                DataColumnItem(
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 8.dp),
                    data = it,
                    onClick = { onDataItemClick(it) },
                    onEditClick = { onEditClick(it) }
                )
            }
        }
    }
}
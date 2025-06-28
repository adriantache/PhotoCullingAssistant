package com.adriantache.photoculling.presentation.shootsCollection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriantache.photoculling.domain.state.ShootsCollectionState
import com.adriantache.photoculling.presentation.util.Spacer

@Composable
fun ShootsCollectionView(
    modifier: Modifier,
    state: ShootsCollectionState.Content
) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Shoots")

        Spacer(16.dp)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Surface(modifier = Modifier.fillMaxWidth().clickable { state.onAddShoot() }) {
                    Text("Add new")
                }
            }

            items(items = state.shoots.shoots) {
                Text(it.name)
            }
        }
    }
}

package ml.bubblebath.kmpcrud.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ml.bubblebath.kmpcrud.data.Data

@Composable
fun DataColumnItem(modifier: Modifier = Modifier, data: Data, onClick: () -> Unit, onEditClick: () -> Unit) {
    Card(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Filled.Place,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f, fill = true)) {
                Text("Latitude")
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = data.geolocation.latitude.toString(),
                    fontWeight = FontWeight.Bold
                )
                Text("Longitude")
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = data.geolocation.longitude.toString(),
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
        }
    }
}

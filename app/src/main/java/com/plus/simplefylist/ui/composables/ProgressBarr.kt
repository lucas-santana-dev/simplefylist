package com.plus.simplefylist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plus.simplefylist.data.entities.ProductEntity
import kotlin.math.absoluteValue

@Composable
fun ProgressBarr(
    progress: Float,
    productsChecked: State<List<ProductEntity>>,
    listProduct: State<List<ProductEntity>>,

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            progress = progress.absoluteValue,
            modifier = Modifier
                .weight(1f)
                .height(10.dp),
            color = Color(0xFFFFEB3B)
            )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "${productsChecked.value.size}/ ${listProduct.value.size} Itens",
            color = Color.White
        )

    }
}
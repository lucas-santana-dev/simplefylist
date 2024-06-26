package com.plus.simplefylist.ui.view.screens.productListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductCardComposable(
    productName: String,
    productQuant: String,
    productCategory: String,
    productPrice: String,
    onClick: () -> Unit,
    onClickMenu:() -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(10.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { newValue ->
                        onCheckedChange(newValue)

                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                    ),


                )
            }
            Column(
                Modifier
                    .fillMaxHeight(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Row {
                    Text(
                        text = "Qtd.: $productQuant",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.DarkGray

                    )
                }
                Row {
                    Text(
                        text = "Vlr. Unit.: R$ $productPrice",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.DarkGray

                    )
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { onClickMenu() }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Deletar Item",
                        tint = MaterialTheme.colorScheme.primary

                    )

                }
            }

        }
    }
    Spacer(modifier = Modifier.padding(5.dp))
}

@Preview
@Composable
fun ProductCardPreview() {
    // Parâmetros fictícios para demonstração
    val productName = "Feijão Fradinho"
    val productQuant = "10"
    val productCategory = "Grãos"
    val productPrice = "5,99"
    var isChecked by remember { mutableStateOf(false) }
    ProductCardComposable(
        productName = productName,
        productQuant = productQuant,
        productCategory = productCategory,
        productPrice = productPrice,
        onClick = {},
        isChecked = isChecked,

        onCheckedChange = {},
        onClickMenu = {}
    )
}
package dev.project.serinochallenge.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.project.serinochallenge.data.item.entities.ProductEntities

@Composable
fun ProductItem(
    item: ProductEntities,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        modifier = Modifier
            .padding(10.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(modifier = modifier, verticalAlignment = Alignment.Top) {
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                Modifier
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                AsyncImage(
                    model = item.thumbnail,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.widthIn(50.dp),
                    contentDescription = "image_item"
                )
            }
            Column(
                Modifier
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                OutlinedCard(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = item.brand.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: PHP " + item.price.toString(),
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Row(
            Modifier
                .fillMaxSize()
                .padding(25.dp, 0.dp, 25.dp, 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Stocks: " + item.stock,
                textAlign = TextAlign.Start
            )
            OutlinedCard(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Text(
                    text = item.rating.toString(),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(5.dp),
                    color = if (item.rating > 4.50) Color.Cyan else Color.Magenta
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        item = ProductEntities(
            id = 1,
            stock = 99,
            title = "Samsung",
            price = 99,
            description = "I'm creating a layout with Jetpack Compose and there is a column. I would like center items inside this column:"
        ), onItemClick = {})
}
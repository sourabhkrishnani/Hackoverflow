package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drbharti.ui.theme.DrBhartiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    onNavigate: (String) -> Unit = {},
    onArticleSelected: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Articles") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                onNavigate = onNavigate,
                currentRoute = "articles"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Categories
            Text(
                text = "Categories",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                CategoryChip(
                    name = "All",
                    isSelected = true,
                    onClick = { /* Filter articles */ }
                )
                
                CategoryChip(
                    name = "Nutrition",
                    isSelected = false,
                    onClick = { /* Filter articles */ }
                )
                
                CategoryChip(
                    name = "Fitness",
                    isSelected = false,
                    onClick = { /* Filter articles */ }
                )
                
                CategoryChip(
                    name = "Mental Health",
                    isSelected = false,
                    onClick = { /* Filter articles */ }
                )
            }
            
            // Featured Article
            Text(
                text = "Featured",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
            
            FeaturedArticleCard(
                article = getDummyArticles().first(),
                onClick = { onArticleSelected(it) }
            )
            
            // Recent Articles
            Text(
                text = "Recent Articles",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
            
            LazyColumn {
                items(getDummyArticles().drop(1)) { article ->
                    ArticleListItem(
                        article = article,
                        onClick = { onArticleSelected(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f)
    val textColor = if (isSelected) Color.White else Color.Gray
    
    Surface(
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = backgroundColor,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FeaturedArticleCard(
    article: Article,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick(article.id) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ){
                Image(painter = painterResource(R.drawable.dark_theme_screen_image), contentDescription = "Food plate", modifier = Modifier.size(500.dp) )

            }
            
            // Gradient overlay would go here
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Text(
                    text = article.author,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(article.id) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail placeholder
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )
        
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = article.author,
                fontSize = 14.sp,
                color = Color.Gray
            )
            
            Text(
                text = article.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

// Dummy data
data class Article(
    val id: String,
    val title: String,
    val author: String,
    val date: String,
    val category: String,
    val content: String
)

fun getDummyArticles(): List<Article> {
    return listOf(
        Article(
            id = "1",
            title = "How to Maintain a Healthy Diet During Pregnancy",
            author = "Dr. Sarah Johnson",
            date = "May 15, 2023",
            category = "Nutrition",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
        ),
        Article(
            id = "2",
            title = "The Benefits of Regular Exercise for Mental Health",
            author = "Dr. Michael Brown",
            date = "May 10, 2023",
            category = "Fitness",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
        ),
        Article(
            id = "3",
            title = "Understanding Postpartum Depression",
            author = "Dr. Emily Davis",
            date = "May 5, 2023",
            category = "Mental Health",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
        ),
        Article(
            id = "4",
            title = "Essential Nutrients for Infant Development",
            author = "Dr. Robert Williams",
            date = "April 28, 2023",
            category = "Nutrition",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    DrBhartiTheme {
        ArticlesScreen()
    }
}

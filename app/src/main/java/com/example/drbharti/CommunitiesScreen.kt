package com.example.drbharti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.PrimaryGreen
import com.example.drbharti.viewmodel.AppViewModel

data class Community(
    val id: String,
    val name: String,
    val description: String,
    val memberCount: Int,
    val imageUrl: String? = null,
    val isJoined: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitiesScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AppViewModel = viewModel()
) {
    var showJoinDialog by remember { mutableStateOf(false) }
    var selectedCommunity by remember { mutableStateOf<Community?>(null) }
    
    // Sample communities data
    val communities = remember {
        listOf(
            Community(
                id = "1",
                name = "Healthy Eating",
                description = "Tips and discussions about maintaining a healthy diet and nutrition.",
                memberCount = 1250,
                isJoined = true
            ),
            Community(
                id = "2",
                name = "Fitness Enthusiasts",
                description = "For people who love to stay fit and share workout routines.",
                memberCount = 980
            ),
            Community(
                id = "3",
                name = "Mental Wellness",
                description = "Support group for mental health and wellness discussions.",
                memberCount = 756
            ),
            Community(
                id = "4",
                name = "Yoga & Meditation",
                description = "Sharing experiences and tips about yoga and meditation practices.",
                memberCount = 623
            ),
            Community(
                id = "5",
                name = "Pregnancy & Parenting",
                description = "Support for expecting and new parents.",
                memberCount = 1432
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Communities",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryGreen,
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
                    
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                onNavigate = onNavigate,
                currentRoute = "communities"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create new community */ },
                containerColor = PrimaryGreen,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Community"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Your Communities",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Joined communities
            val joinedCommunities = communities.filter { it.isJoined }
            items(joinedCommunities) { community ->
                CommunityCard(
                    community = community,
                    onCommunityClick = { /* Navigate to community detail */ },
                    onJoinClick = { 
                        selectedCommunity = community
                        showJoinDialog = true
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Discover Communities",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Communities to discover
            val discoverCommunities = communities.filter { !it.isJoined }
            items(discoverCommunities) { community ->
                CommunityCard(
                    community = community,
                    onCommunityClick = { /* Navigate to community detail */ },
                    onJoinClick = { 
                        selectedCommunity = community
                        showJoinDialog = true
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for FAB
            }
        }
    }
    
    // Join Community Dialog
    if (showJoinDialog && selectedCommunity != null) {
        JoinCommunityDialog(
            community = selectedCommunity!!,
            onDismiss = { showJoinDialog = false },
            onJoin = { 
                // Handle join logic
                showJoinDialog = false
            }
        )
    }
}

@Composable
fun CommunityCard(
    community: Community,
    onCommunityClick: () -> Unit,
    onJoinClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCommunityClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Community image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                if (community.imageUrl != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_community_placeholder),
                        contentDescription = "Community ${community.name}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Community",
                        tint = PrimaryGreen,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = community.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = community.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${community.memberCount} members",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            if (community.isJoined) {
                OutlinedButton(
                    onClick = onJoinClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryGreen
                    ),
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text("Joined")
                }
            } else {
                Button(
                    onClick = onJoinClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryGreen
                    )
                ) {
                    Text("Join")
                }
            }
        }
    }
}

@Composable
fun JoinCommunityDialog(
    community: Community,
    onDismiss: () -> Unit,
    onJoin: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = "Community",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = if (community.isJoined) "Leave Community?" else "Join Community?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = if (community.isJoined) 
                        "Are you sure you want to leave ${community.name}? You will no longer have access to discussions and resources." 
                    else 
                        "Join ${community.name} to connect with ${community.memberCount} members and access exclusive content and discussions.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = onJoin,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen
                        )
                    ) {
                        Text(if (community.isJoined) "Leave" else "Join")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunitiesScreenPreview() {
    DrBhartiTheme {
        CommunitiesScreen()
    }
}

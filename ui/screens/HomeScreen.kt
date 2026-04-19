package com.unsw.digitalid.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsw.digitalid.ui.components.StudentCard
import com.unsw.digitalid.ui.components.StudentInfo
import com.unsw.digitalid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
) {
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { contentVisible = true }

    Scaffold(
        containerColor = UnswOffWhite,
        topBar = {
            HomeTopBar(onLogout = onLogout)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ── Welcome header ────────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { -30 },
                    animationSpec  = tween(500),
                ),
            ) {
                WelcomeHeader()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Student card ──────────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(600, delayMillis = 150)) + slideInVertically(
                    initialOffsetY = { 60 },
                    animationSpec  = tween(600, delayMillis = 150),
                ),
            ) {
                StudentCard(
                    student  = StudentInfo(
                        name      = "Ahmed Hassan",
                        studentId = "z1234567",
                        program   = "Software Engineering",
                        faculty   = "Engineering",
                        cardExpiry = "2027",
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Status chip ───────────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(700, delayMillis = 300)),
            ) {
                ActiveStatusChip()
            }

            Spacer(modifier = Modifier.height(36.dp))

            // ── Future NFC section ────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(700, delayMillis = 400)) + slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec  = tween(700, delayMillis = 400),
                ),
            ) {
                NfcSection()
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ─── Top app bar ─────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(onLogout: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Yellow circle badge
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(UnswYellow),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text       = "U",
                        style      = MaterialTheme.typography.titleSmall,
                        color      = UnswNavy,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                Text(
                    text       = "UNSW Digital ID",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = UnswWhite,
                )
            }
        },
        actions = {
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector        = Icons.Outlined.Logout,
                    contentDescription = "Logout",
                    tint               = UnswYellow,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = UnswNavy,
            titleContentColor = UnswWhite,
        ),
    )
}

// ─── Welcome header ───────────────────────────────────────────────────────────

@Composable
private fun WelcomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(UnswNavy, UnswNavy.copy(alpha = 0.85f)),
                ),
            )
            .padding(horizontal = 24.dp, vertical = 22.dp),
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text  = "Good day,",
                    style = MaterialTheme.typography.bodyMedium,
                    color = UnswYellow.copy(alpha = 0.80f),
                )
                Text(
                    text       = "Ahmed Hassan",
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color      = UnswWhite,
                )
                Text(
                    text  = "Software Engineering · z1234567",
                    style = MaterialTheme.typography.bodySmall,
                    color = UnswWhite.copy(alpha = 0.60f),
                )
            }

            Icon(
                imageVector        = Icons.Outlined.AccountCircle,
                contentDescription = "Profile",
                tint               = UnswYellow,
                modifier           = Modifier.size(48.dp),
            )
        }
    }
}

// ─── Active status chip ───────────────────────────────────────────────────────

@Composable
private fun ActiveStatusChip() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1B5E20).copy(alpha = 0.10f))
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF4CAF50)),
            )
            Text(
                text      = "ID Active · Valid until Dec 2027",
                style     = MaterialTheme.typography.labelMedium,
                color     = Color(0xFF2E7D32),
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

// ─── NFC / Future feature section ────────────────────────────────────────────

@Composable
private fun NfcSection() {
    Column(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement   = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text       = "Access Features",
            style      = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color      = UnswNavy,
            modifier   = Modifier.padding(start = 4.dp),
        )

        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FeatureTile(
                modifier    = Modifier.weight(1f),
                icon        = Icons.Outlined.Nfc,
                title       = "NFC Access",
                description = "Tap to unlock rooms",
                tint        = UnswNavy,
                comingSoon  = true,
            )
            FeatureTile(
                modifier    = Modifier.weight(1f),
                icon        = Icons.Outlined.QrCode2,
                title       = "QR Code",
                description = "Show at reception",
                tint        = UnswNavy,
                comingSoon  = true,
            )
        }

        // Reserved NFC hint banner
        NfcHintBanner()
    }
}

@Composable
private fun FeatureTile(
    icon       : ImageVector,
    title      : String,
    description: String,
    tint       : Color,
    comingSoon : Boolean,
    modifier   : Modifier = Modifier,
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = UnswWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(tint.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = title,
                    tint               = tint,
                    modifier           = Modifier.size(26.dp),
                )
            }

            Text(
                text       = title,
                style      = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color      = UnswNavy,
                textAlign  = TextAlign.Center,
            )

            Text(
                text      = description,
                style     = MaterialTheme.typography.labelSmall,
                color     = UnswMidGrey,
                textAlign = TextAlign.Center,
            )

            if (comingSoon) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(UnswYellow.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                ) {
                    Text(
                        text          = "COMING SOON",
                        style         = MaterialTheme.typography.labelSmall,
                        color         = UnswYellowDark,
                        fontWeight    = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        fontSize      = 9.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun NfcHintBanner() {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(
            containerColor = UnswNavy.copy(alpha = 0.05f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border    = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = UnswNavy.copy(alpha = 0.12f),
        ),
    ) {
        Row(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment   = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector        = Icons.Outlined.Nfc,
                contentDescription = null,
                tint               = UnswNavy.copy(alpha = 0.45f),
                modifier           = Modifier.size(22.dp),
            )
            Text(
                text  = "NFC room access will be enabled here.\nHold your phone near a UNSW door reader.",
                style = MaterialTheme.typography.bodySmall,
                color = UnswNavy.copy(alpha = 0.55f),
                lineHeight = 18.sp,
            )
        }
    }
}

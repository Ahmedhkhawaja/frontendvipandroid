package com.unsw.digitalid.ui.screens

import android.media.RingtoneManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.res.painterResource
import com.unsw.digitalid.R
import androidx.compose.animation.AnimatedVisibility
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.unsw.digitalid.ui.components.StudentCard
import com.unsw.digitalid.ui.components.StudentInfo
import com.unsw.digitalid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
) {
    var contentVisible       by remember { mutableStateOf(false) }
    var showAccessGranted    by remember { mutableStateOf(false) }
    var accessGrantedSource  by remember { mutableStateOf("NFC") }

    // QR scanner launcher – any successful scan triggers the Access Granted dialog
    val qrScanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            accessGrantedSource = "QR"
            showAccessGranted   = true
        }
    }

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
                        name       = "Salil Kanhere",
                        studentId  = "z1234567",
                        program    = "Software Engineering",
                        faculty    = "Engineering",
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
                NfcSection(
                onNfcTap = {
                    accessGrantedSource = "NFC"
                    showAccessGranted   = true
                },
                onQrTap  = {
                    qrScanLauncher.launch(
                        ScanOptions().apply {
                            setPrompt("Scan any QR code to verify access")
                            setBeepEnabled(false)
                            setOrientationLocked(true)
                            setCameraId(0)
                        }
                    )
                },
            )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // ── Access Granted dialog (NFC or QR) ────────────────────────────────
    if (showAccessGranted) {
        AccessGrantedDialog(
            source    = accessGrantedSource,
            onDismiss = { showAccessGranted = false },
        )
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
                // UNSW crest logo in top bar
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(UnswWhite),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.foundation.Image(
                        painter            = painterResource(R.drawable.unsw_logo),
                        contentDescription = "UNSW Logo",
                        modifier           = Modifier.size(30.dp),
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
                    imageVector        = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = "Logout",
                    tint               = UnswYellow,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor    = UnswNavy,
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
                    text       = "Salil Kanhere",
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
                text       = "ID Active · Valid until Dec 2027",
                style      = MaterialTheme.typography.labelMedium,
                color      = Color(0xFF2E7D32),
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

// ─── NFC / Future feature section ────────────────────────────────────────────

@Composable
private fun NfcSection(onNfcTap: () -> Unit, onQrTap: () -> Unit) {
    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                comingSoon  = false,
                onClick     = onNfcTap,
            )
            FeatureTile(
                modifier    = Modifier.weight(1f),
                icon        = Icons.Outlined.QrCode2,
                title       = "QR Code",
                description = "Scan to verify access",
                tint        = UnswNavy,
                comingSoon  = false,
                onClick     = onQrTap,
            )
        }

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
    onClick    : () -> Unit,
    modifier   : Modifier = Modifier,
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = UnswWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick   = onClick,
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
            modifier              = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector        = Icons.Outlined.Nfc,
                contentDescription = null,
                tint               = UnswNavy.copy(alpha = 0.45f),
                modifier           = Modifier.size(22.dp),
            )
            Text(
                text       = "NFC room access will be enabled here.\nHold your phone near a UNSW door reader.",
                style      = MaterialTheme.typography.bodySmall,
                color      = UnswNavy.copy(alpha = 0.55f),
                lineHeight = 18.sp,
            )
        }
    }
}

// ─── Access Granted dialog (shared for NFC and QR) ───────────────────────────

@Composable
private fun AccessGrantedDialog(source: String, onDismiss: () -> Unit) {
    val context = LocalContext.current

    // Play notification sound once when dialog appears
    LaunchedEffect(Unit) {
        try {
            val uri  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val tone = RingtoneManager.getRingtone(context, uri)
            tone?.play()
        } catch (_: Exception) {}
    }

    // Animated scale for the icon
    var iconVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { iconVisible = true }
    val iconScale by animateFloatAsState(
        targetValue   = if (iconVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness    = Spring.StiffnessMedium,
        ),
        label = "iconScale",
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties       = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        AnimatedVisibility(
            visible = true,
            enter   = fadeIn(tween(200)) + scaleIn(
                initialScale  = 0.85f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness    = Spring.StiffnessMediumLow,
                ),
            ),
            exit    = fadeOut(tween(150)) + scaleOut(targetScale = 0.9f),
        ) {
            Card(
                modifier  = Modifier
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth(),
                shape     = RoundedCornerShape(28.dp),
                colors    = CardDefaults.cardColors(containerColor = UnswWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            ) {
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Animated green check circle
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .scale(iconScale)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50).copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.CheckCircle,
                            contentDescription = "Access Granted",
                            tint               = Color(0xFF2E7D32),
                            modifier           = Modifier.size(56.dp),
                        )
                    }

                    Text(
                        text       = "Access Granted",
                        style      = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color      = Color(0xFF2E7D32),
                        textAlign  = TextAlign.Center,
                    )

                    Text(
                        text      = "$source verified successfully.\nWelcome, Salil Kanhere.",
                        style     = MaterialTheme.typography.bodyMedium,
                        color     = UnswMidGrey,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick  = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape    = RoundedCornerShape(14.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = UnswNavy,
                            contentColor   = UnswWhite,
                        ),
                    ) {
                        Text(
                            text       = "Done",
                            fontWeight = FontWeight.Bold,
                            style      = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

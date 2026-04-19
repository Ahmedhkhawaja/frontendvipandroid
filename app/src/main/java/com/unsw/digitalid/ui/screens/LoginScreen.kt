package com.unsw.digitalid.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.res.painterResource
import com.unsw.digitalid.R
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsw.digitalid.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
) {
    var zid             by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }
    var isLoading       by remember { mutableStateOf(false) }
    var contentVisible  by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    // Trigger entrance animation on first composition
    LaunchedEffect(Unit) { contentVisible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UnswWhite),
    ) {
        // ── Top accent stripe ─────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(UnswNavy, UnswNavyDark),
                    ),
                ),
        )

        // ── Yellow accent bar ─────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .offset(y = 220.dp)
                .background(UnswYellow),
        )

        // ── Main content ──────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // ── Logo / branding block ─────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(600)) + slideInVertically(
                    initialOffsetY = { -40 },
                    animationSpec  = tween(600),
                ),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Yellow circle badge with UNSW logo inside
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(RoundedCornerShape(percent = 50))
                            .background(UnswYellow),
                        contentAlignment = Alignment.Center,
                    ) {
                        androidx.compose.foundation.Image(
                            painter            = painterResource(R.drawable.unsw_logo),
                            contentDescription = "UNSW Logo",
                            modifier           = Modifier.size(68.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text          = "UNSW Digital ID",
                        style         = MaterialTheme.typography.headlineLarge,
                        color         = UnswWhite,
                        fontWeight    = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text  = "University of New South Wales",
                        style = MaterialTheme.typography.bodyMedium,
                        color = UnswYellow.copy(alpha = 0.85f),
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Login card ────────────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(700, delayMillis = 200)) +
                        slideInVertically(
                            initialOffsetY = { 60 },
                            animationSpec  = tween(700, delayMillis = 200),
                        ),
            ) {
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(24.dp),
                    colors    = CardDefaults.cardColors(containerColor = UnswWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                ) {
                    Column(
                        modifier            = Modifier.padding(28.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        // Card header
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text       = "Sign In",
                                style      = MaterialTheme.typography.headlineMedium,
                                color      = UnswNavy,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text  = "Access your student identity card",
                                style = MaterialTheme.typography.bodySmall,
                                color = UnswMidGrey,
                            )
                        }

                        HorizontalDivider(
                            color     = UnswYellow,
                            thickness = 2.dp,
                            modifier  = Modifier.width(48.dp),
                        )

                        // ── zID / Email field ────────────────────────────
                        OutlinedTextField(
                            value         = zid,
                            onValueChange = { zid = it },
                            label         = { Text("zID or Email") },
                            placeholder   = { Text("e.g. z1234567", color = UnswMidGrey) },
                            leadingIcon   = {
                                Icon(
                                    imageVector        = Icons.Outlined.Email,
                                    contentDescription = "Email",
                                    tint               = if (zid.isNotEmpty()) UnswNavy else UnswMidGrey,
                                )
                            },
                            singleLine      = true,
                            modifier        = Modifier.fillMaxWidth(),
                            shape           = RoundedCornerShape(14.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction    = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            ),
                            colors = loginFieldColors(),
                        )

                        // ── Password field ───────────────────────────────
                        OutlinedTextField(
                            value         = password,
                            onValueChange = { password = it },
                            label         = { Text("Password") },
                            placeholder   = { Text("Enter your password", color = UnswMidGrey) },
                            leadingIcon   = {
                                Icon(
                                    imageVector        = Icons.Outlined.Lock,
                                    contentDescription = "Password",
                                    tint               = if (password.isNotEmpty()) UnswNavy else UnswMidGrey,
                                )
                            },
                            trailingIcon  = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector        = if (passwordVisible)
                                            Icons.Outlined.Visibility
                                        else
                                            Icons.Outlined.VisibilityOff,
                                        contentDescription = if (passwordVisible) "Hide" else "Show",
                                        tint               = UnswMidGrey,
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            singleLine      = true,
                            modifier        = Modifier.fillMaxWidth(),
                            shape           = RoundedCornerShape(14.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction    = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (zid.isNotBlank() && password.isNotBlank()) {
                                        isLoading = true
                                        onLoginSuccess()
                                    }
                                },
                            ),
                            colors = loginFieldColors(),
                        )

                        // ── Login button ─────────────────────────────────
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                isLoading = true
                                onLoginSuccess()
                            },
                            modifier  = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            shape     = RoundedCornerShape(14.dp),
                            colors    = ButtonDefaults.buttonColors(
                                containerColor = UnswNavy,
                                contentColor   = UnswWhite,
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                pressedElevation = 4.dp,
                            ),
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier    = Modifier.size(22.dp),
                                    color       = UnswYellow,
                                    strokeWidth = 2.5.dp,
                                )
                            } else {
                                Text(
                                    text          = "Sign In",
                                    style         = MaterialTheme.typography.labelLarge,
                                    fontWeight    = FontWeight.Bold,
                                    fontSize      = 16.sp,
                                    letterSpacing = 1.sp,
                                )
                            }
                        }

                        // ── Forgot password link ─────────────────────────
                        TextButton(
                            onClick  = { /* TODO: forgot password flow */ },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) {
                            Text(
                                text  = "Forgot password?",
                                color = UnswNavy,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Footer note ───────────────────────────────────────────────
            AnimatedVisibility(
                visible = contentVisible,
                enter   = fadeIn(tween(900, delayMillis = 400)),
            ) {
                Text(
                    text       = "Use your UNSW student credentials\nto access your Digital ID",
                    style      = MaterialTheme.typography.bodySmall,
                    color      = UnswMidGrey,
                    textAlign  = TextAlign.Center,
                    lineHeight = 18.sp,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ─── Shared text field colours matching UNSW theme ──────────────────────────
@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor          = UnswDarkGrey,
    unfocusedTextColor        = UnswDarkGrey,
    focusedBorderColor        = UnswNavy,
    unfocusedBorderColor      = UnswLightGrey,
    focusedLabelColor         = UnswNavy,
    unfocusedLabelColor       = UnswMidGrey,
    cursorColor               = UnswNavy,
    focusedLeadingIconColor   = UnswNavy,
    unfocusedLeadingIconColor = UnswMidGrey,
)

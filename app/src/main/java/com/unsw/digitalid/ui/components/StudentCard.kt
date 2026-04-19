package com.unsw.digitalid.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unsw.digitalid.R
import com.unsw.digitalid.ui.theme.*

// ─── Data model ─────────────────────────────────────────────────────────────

/**
 * Holds all student information displayed on the card.
 * Expand this data class as future fields (NFC token, QR, etc.) are added.
 */
data class StudentInfo(
    val name      : String = "Salil Kanhere",
    val studentId : String = "z1234567",
    val program   : String = "Software Engineering",
    val faculty   : String = "Engineering",
    val cardExpiry: String = "2027",
    // Future fields – leave null until backend integration
    val photoUrl  : String? = null,
    val nfcToken  : String? = null,
    val qrPayload : String? = null,
)

// ─── Reusable StudentCard composable ────────────────────────────────────────

/**
 * Digital UNSW student ID card.
 *
 * Drop-in composable – wrap in a parent with any width constraint.
 * Future NFC / QR additions slot into [nfcContent] and [qrContent] slots.
 */
@Composable
fun StudentCard(
    student   : StudentInfo = StudentInfo(),
    modifier  : Modifier    = Modifier,
    // Extension slots for future features
    nfcContent: (@Composable () -> Unit)? = null,
    qrContent : (@Composable () -> Unit)? = null,
) {
    // Subtle continuous shimmer on the card surface
    val shimmerAnim = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by shimmerAnim.animateFloat(
        initialValue   = -800f,
        targetValue    = 1200f,
        animationSpec  = infiniteRepeatable(
            animation  = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerX",
    )

    Card(
        modifier  = modifier
            .fillMaxWidth()
            .shadow(
                elevation       = 24.dp,
                shape           = RoundedCornerShape(28.dp),
                ambientColor    = UnswYellow.copy(alpha = 0.25f),
                spotColor       = UnswNavy.copy(alpha = 0.30f),
            ),
        shape     = RoundedCornerShape(28.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.00f to UnswYellow,
                            0.45f to UnswYellowLight,
                            0.75f to UnswYellow,
                            1.00f to UnswYellowDark,
                        ),
                        start = Offset(0f, 0f),
                        end   = Offset(800f, 600f),
                    ),
                ),
        ) {
            // ── Geometric background decorations ──────────────────────────
            CardDecorations(shimmerX = shimmerX)

            // ── Card content ─────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 22.dp),
            ) {

                // ── TOP SECTION: University branding ──────────────────────
                CardTopSection()

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(
                    color     = UnswNavy.copy(alpha = 0.15f),
                    thickness = 1.dp,
                )
                Spacer(modifier = Modifier.height(20.dp))

                // ── CENTER SECTION: Photo placeholder ─────────────────────
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    PhotoPlaceholder(size = 90.dp)

                    // Student name + ID beside the photo
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier            = Modifier.weight(1f),
                    ) {
                        Text(
                            text       = student.name,
                            style      = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color      = UnswNavy,
                            maxLines   = 2,
                            overflow   = TextOverflow.Ellipsis,
                        )
                        StudentBadge(label = student.studentId)
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))
                HorizontalDivider(
                    color     = UnswNavy.copy(alpha = 0.15f),
                    thickness = 1.dp,
                )
                Spacer(modifier = Modifier.height(18.dp))

                // ── BOTTOM SECTION: Academic details ──────────────────────
                CardBottomSection(student = student)

                // ── FUTURE SLOT: NFC / QR content ────────────────────────
                if (nfcContent != null || qrContent != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        color     = UnswNavy.copy(alpha = 0.15f),
                        thickness = 1.dp,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        nfcContent?.invoke()
                        qrContent?.invoke()
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

// ─── Sub-components ──────────────────────────────────────────────────────────

@Composable
private fun CardTopSection() {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text          = "UNSW SYDNEY",
                style         = MaterialTheme.typography.titleMedium,
                fontWeight    = FontWeight.ExtraBold,
                color         = UnswNavy,
                letterSpacing = 2.sp,
            )
            Text(
                text          = "University of New South Wales",
                style         = MaterialTheme.typography.labelSmall,
                color         = UnswNavy.copy(alpha = 0.65f),
                letterSpacing = 0.3.sp,
            )
        }

        // "STUDENT" pill badge
        Box(
            modifier         = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(UnswNavy),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text          = "STUDENT",
                modifier      = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                style         = MaterialTheme.typography.labelSmall,
                fontWeight    = FontWeight.Bold,
                color         = UnswYellow,
                letterSpacing = 1.5.sp,
            )
        }
    }
}

@Composable
private fun PhotoPlaceholder(size: Dp) {
    Image(
        painter            = painterResource(R.drawable.student_photo),
        contentDescription = "Student Photo",
        contentScale       = androidx.compose.ui.layout.ContentScale.Crop,
        modifier           = Modifier
            .size(size)
            .clip(RoundedCornerShape(16.dp)),
    )
}

@Composable
private fun StudentBadge(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(UnswNavy.copy(alpha = 0.10f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text          = label,
            style         = MaterialTheme.typography.labelMedium,
            fontWeight    = FontWeight.SemiBold,
            color         = UnswNavy,
            letterSpacing = 1.sp,
        )
    }
}

@Composable
private fun CardBottomSection(student: StudentInfo) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.Bottom,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoRow(label = "Program",    value = student.program)
            InfoRow(label = "Faculty",    value = student.faculty)
            InfoRow(label = "Valid Until", value = student.cardExpiry)
        }

        // Decorative dot-grid watermark
        DotGrid(
            columns = 4,
            rows    = 3,
            color   = UnswNavy.copy(alpha = 0.18f),
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text          = "$label:",
            style         = MaterialTheme.typography.labelSmall,
            color         = UnswNavy.copy(alpha = 0.55f),
            letterSpacing = 0.3.sp,
        )
        Text(
            text       = value,
            style      = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color      = UnswNavy,
        )
    }
}

// ─── Canvas decorations ──────────────────────────────────────────────────────

@Composable
private fun CardDecorations(shimmerX: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
    ) {
        drawDecorations(shimmerX)
    }
}

private fun DrawScope.drawDecorations(shimmerX: Float) {
    val w = size.width
    val h = size.height

    // Large background circle (top-right)
    drawCircle(
        color  = Color.White.copy(alpha = 0.10f),
        radius = w * 0.65f,
        center = Offset(w * 1.05f, -h * 0.15f),
    )

    // Medium circle (bottom-left)
    drawCircle(
        color  = Color.White.copy(alpha = 0.08f),
        radius = w * 0.40f,
        center = Offset(-w * 0.05f, h * 1.10f),
    )

    // Small accent arc (bottom-right)
    drawArc(
        color      = Color.White.copy(alpha = 0.12f),
        startAngle = 180f,
        sweepAngle = 90f,
        useCenter  = false,
        topLeft    = Offset(w * 0.70f, h * 0.65f),
        size       = Size(w * 0.50f, w * 0.50f),
    )

    // Shimmer sweep
    drawRect(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.Transparent,
                Color.White.copy(alpha = 0.06f),
                Color.Transparent,
            ),
            start = Offset(shimmerX - 200f, 0f),
            end   = Offset(shimmerX + 200f, h),
        ),
        size = size,
    )
}

@Composable
private fun DotGrid(
    columns : Int,
    rows    : Int,
    color   : Color,
    dotSize : Dp = 3.dp,
    spacing : Dp = 10.dp,
) {
    val total  = (dotSize + spacing)
    val totalW = total * columns
    val totalH = total * rows

    Canvas(modifier = Modifier.size(totalW, totalH)) {
        val step = (dotSize + spacing).toPx()
        val r    = dotSize.toPx() / 2
        repeat(rows) { row ->
            repeat(columns) { col ->
                drawCircle(
                    color  = color,
                    radius = r,
                    center = Offset(
                        x = col * step + r,
                        y = row * step + r,
                    ),
                )
            }
        }
    }
}

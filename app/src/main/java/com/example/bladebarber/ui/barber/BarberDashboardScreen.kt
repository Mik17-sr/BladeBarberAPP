package com.example.bladebarber.ui.barber

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*

private data class BarberAppointment(
    val time: String,
    val client: String,
    val service: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberDashboardScreen(
    barberName: String = "Santiago Barber",
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(BarberTab.Today) }
    var appointments by remember {
        mutableStateOf(
            listOf(
                BarberAppointment("10:00 AM", "Carlos Méndez", "Corte clásico + barba", "Confirmada"),
                BarberAppointment("11:30 AM", "Andrés Gómez", "Corte fade", "Pendiente"),
                BarberAppointment("01:00 PM", "Felipe Restrepo", "Afeitado tradicional", "Confirmada"),
                BarberAppointment("03:30 PM", "David Ospina", "Diseño moderno", "Pendiente")
            )
        )
    }

    Scaffold(
        containerColor = BladeDarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("BladeBarber", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Panel de barbero", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = BladeGold)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión", tint = BladeTextSecondary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BladeCardBg)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = BladeCardBg, contentColor = BladeGold) {
                NavigationBarItem(
                    selected = selectedTab == BarberTab.Today,
                    onClick = { selectedTab = BarberTab.Today },
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                    label = { Text("Mi agenda") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
                NavigationBarItem(
                    selected = selectedTab == BarberTab.Profile,
                    onClick = { selectedTab = BarberTab.Profile },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Mi perfil") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            BarberTab.Today -> Column(
                modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(18.dp))
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("TU JORNADA", color = BladeGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text("Hola, $barberName", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("Aquí tienes el resumen de tu día.", color = BladeTextSecondary, fontSize = 13.sp)
                        Spacer(Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            SummaryChip(Icons.Default.CalendarMonth, "${appointments.size} citas")
                            SummaryChip(Icons.Default.Schedule, "08:00 AM – 06:00 PM")
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    BarberMetric(Modifier.weight(1f), "Citas de hoy", appointments.size.toString(), "Programadas")
                    BarberMetric(Modifier.weight(1f), "Por confirmar", appointments.count { it.status == "Pendiente" }.toString(), "Pendientes")
                }

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Agenda de hoy", color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Tus próximos clientes", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                    Surface(color = BladeGold.copy(alpha = 0.14f), shape = RoundedCornerShape(8.dp)) {
                        Text("HOY", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                    }
                }

                appointments.forEachIndexed { index, appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onConfirm = {
                            appointments = appointments.toMutableList().also {
                                it[index] = appointment.copy(status = "Confirmada")
                            }
                        }
                    )
                }
            }

            BarberTab.Profile -> Column(
                modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Mi perfil", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(56.dp).background(BladeGold.copy(alpha = 0.16f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = BladeGold, modifier = Modifier.size(30.dp))
                        }
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(barberName, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            Text("Barbero · Especialista en cortes", color = BladeTextSecondary, fontSize = 12.sp)
                        }
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
                ) {
                    Column {
                        ProfileRow("Horario asignado", "Lunes a sábado · 08:00 AM – 06:00 PM")
                        HorizontalDivider(color = BladeBorder)
                        ProfileRow("Citas atendidas hoy", "${appointments.count { it.status == "Confirmada" }} confirmadas")
                    }
                }
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, BladeBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión")
                }
            }
        }
    }
}

private enum class BarberTab { Today, Profile }

@Composable
private fun SummaryChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Surface(color = BladeDarkBg, shape = RoundedCornerShape(8.dp)) {
        Row(Modifier.padding(horizontal = 9.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = BladeGold, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(5.dp))
            Text(text, color = BladeTextPrimary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun BarberMetric(modifier: Modifier, title: String, value: String, note: String) {
    Card(
        modifier = modifier.border(1.dp, BladeBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, color = BladeTextSecondary, fontSize = 11.sp)
            Text(value, color = BladeGold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(note, color = BladeTextSecondary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun AppointmentCard(appointment: BarberAppointment, onConfirm: () -> Unit) {
    val statusColor = if (appointment.status == "Confirmada") BladeSuccess else BladeGold
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))
    ) {
        Column(Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(appointment.time, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.weight(1f))
                Surface(color = statusColor.copy(alpha = 0.14f), shape = RoundedCornerShape(6.dp)) {
                    Text(appointment.status, color = statusColor, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp))
                }
            }
            Text(appointment.client, color = BladeTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(appointment.service, color = BladeTextSecondary, fontSize = 12.sp)
            if (appointment.status == "Pendiente") {
                TextButton(onClick = onConfirm, contentPadding = PaddingValues(0.dp)) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = BladeGold, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(5.dp))
                    Text("Confirmar cita", color = BladeGold, fontSize = 12.sp)
                    Spacer(Modifier.width(4.dp))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = BladeGold, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ProfileRow(title: String, value: String) {
    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = BladeTextSecondary, fontSize = 11.sp)
        Text(value, color = BladeTextPrimary, fontSize = 13.sp)
    }
}

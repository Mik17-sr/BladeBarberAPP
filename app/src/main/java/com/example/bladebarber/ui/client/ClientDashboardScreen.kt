package com.example.bladebarber.ui.client

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*

private data class ClientService(val name: String, val description: String, val price: String)
private data class ClientNotification(val title: String, val message: String, val time: String, val type: String, val unread: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDashboardScreen(
    clientName: String,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var bookedService by remember { mutableStateOf<String?>(null) }
    var notifications by remember {
        mutableStateOf(
            listOf(
                ClientNotification("Tu agenda", "Aquí recibirás la confirmación y los detalles de tus citas.", "Hoy", "appointment", true),
                ClientNotification("Promoción de la semana", "Disfruta 15% de descuento en corte + barba hasta el sábado.", "Ayer", "promotion", true),
                ClientNotification("Bienvenido a BladeBarber", "Explora nuestros servicios y reserva tu próximo corte.", "Hace 2 días", "welcome", false)
            )
        )
    }
    val services = listOf(
        ClientService("Corte clásico", "Estilo limpio y atemporal · 30 min", "$25.000"),
        ClientService("Corte + barba", "Corte personalizado y perfilado · 45 min", "$35.000"),
        ClientService("Afeitado tradicional", "Toalla caliente y acabado clásico · 25 min", "$20.000"),
        ClientService("Diseño & fade", "Degradado y diseño a tu gusto · 40 min", "$30.000")
    )

    Scaffold(
        containerColor = BladeDarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("BladeBarber", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Tu espacio", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión", tint = BladeTextSecondary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BladeCardBg)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = BladeCardBg) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.ContentCut, contentDescription = null) },
                    label = { Text("Servicios") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                    label = { Text("Mi cita") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                    label = { Text("Avisos") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = BladeGold, selectedTextColor = BladeGold, indicatorColor = BladeGold.copy(alpha = 0.14f), unselectedIconColor = BladeTextSecondary, unselectedTextColor = BladeTextSecondary)
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> Column(
                Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(18.dp))
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("BIENVENIDO A BLADEBARBER", color = BladeGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text("Hola, $clientName", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("Elige un servicio y reserva tu próximo corte.", color = BladeTextSecondary, fontSize = 13.sp)
                    }
                }
                if (bookedService != null) {
                    Surface(color = BladeSuccess.copy(alpha = 0.14f), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Text("Solicitud de cita para $bookedService lista. Te confirmaremos el horario.", color = BladeSuccess, fontSize = 13.sp, modifier = Modifier.padding(14.dp))
                    }
                }
                Text("Nuestros servicios", color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                services.forEach { service ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(color = BladeGold.copy(alpha = 0.14f), shape = CircleShape, modifier = Modifier.size(38.dp)) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.ContentCut, contentDescription = null, tint = BladeGold, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(service.name, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(service.description, color = BladeTextSecondary, fontSize = 11.sp)
                                }
                                Text(service.price, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Button(
                                onClick = {
                                    bookedService = service.name
                                    notifications = listOf(
                                        ClientNotification("Solicitud de cita enviada", "Recibimos tu solicitud para ${service.name}. Te avisaremos cuando confirmen el horario.", "Ahora", "appointment", true)
                                    ) + notifications
                                },
                                modifier = Modifier.fillMaxWidth().height(44.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BladeGold)
                            ) {
                                Text("Reservar servicio", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            1 -> Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Mi cita", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
                ) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(bookedService ?: "Aún no tienes una cita", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(if (bookedService == null) "Explora los servicios para solicitar una reserva." else "Solicitud enviada · Horario por confirmar", color = BladeTextSecondary, fontSize = 13.sp)
                    }
                }
            }

            2 -> Column(
                Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Notificaciones", color = BladeTextPrimary, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                        Text("Novedades de tus citas y promociones", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                    TextButton(onClick = { notifications = notifications.map { it.copy(unread = false) } }) {
                        Text("Marcar leídas", color = BladeGold, fontSize = 12.sp)
                    }
                }
                notifications.forEachIndexed { index, notification ->
                    val icon = if (notification.type == "promotion") Icons.Default.LocalOffer else Icons.Default.EventAvailable
                    val tint = if (notification.type == "promotion") BladeGold else BladeSuccess
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth().border(
                            1.dp,
                            if (notification.unread) BladeGold.copy(alpha = 0.45f) else BladeBorder,
                            RoundedCornerShape(14.dp)
                        ),
                        onClick = { notifications = notifications.toMutableList().also { it[index] = notification.copy(unread = false) } }
                    ) {
                        Row(Modifier.padding(15.dp), verticalAlignment = Alignment.Top) {
                            Surface(color = tint.copy(alpha = 0.14f), shape = CircleShape, modifier = Modifier.size(40.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(19.dp))
                                }
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(notification.title, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                                    if (notification.unread) {
                                        Box(Modifier.size(8.dp).clip(CircleShape).background(BladeGold))
                                    }
                                }
                                Text(notification.message, color = BladeTextSecondary, fontSize = 12.sp)
                                Text(notification.time, color = BladeGold, fontSize = 10.sp)
                            }
                        }
                    }
                }
                if (notifications.isEmpty()) {
                    Text("No tienes notificaciones por ahora.", color = BladeTextSecondary, fontSize = 13.sp)
                }
            }

            else -> Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Mi perfil", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Card(
                    colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
                ) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = BladeGold.copy(alpha = 0.14f), shape = CircleShape, modifier = Modifier.size(48.dp)) {
                            Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, contentDescription = null, tint = BladeGold) }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(clientName, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Cliente BladeBarber", color = BladeTextSecondary, fontSize = 12.sp)
                        }
                    }
                }
                OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Text("Cerrar sesión", color = BladeTextPrimary)
                }
            }
        }
    }
}

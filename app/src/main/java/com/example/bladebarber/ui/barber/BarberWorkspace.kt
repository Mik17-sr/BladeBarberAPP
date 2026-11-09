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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*
import kotlinx.coroutines.launch

private enum class BarberSection(val title: String, val icon: ImageVector) {
    Today("Inicio", Icons.Default.Dashboard),
    Appointments("Atender citas", Icons.Default.CalendarMonth),
    Team("Equipo", Icons.Default.Groups),
    Schedule("Mi horario", Icons.Default.Schedule),
    Shop("Estado local", Icons.Default.Store),
    Queue("Fila de espera", Icons.Default.Queue),
    ServiceNew("Registrar servicio", Icons.Default.Add),
    Services("Servicios", Icons.Default.ContentCut),
    Lunch("Bloque de almuerzo", Icons.Default.Restaurant),
    News("Novedades", Icons.Default.EventNote),
    History("Mi historial", Icons.Default.History),
    Notifications("Notificaciones", Icons.Default.Notifications),
    EditProfile("Editar perfil", Icons.Default.Person),
    Password("Cambiar contraseña", Icons.Default.Lock)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberDashboardScreen(barberName: String = "Santiago Barber", onLogout: () -> Unit) {
    var section by remember { mutableStateOf(BarberSection.Today) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = BladeCardBg, modifier = Modifier.width(300.dp)) {
                Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(14.dp)) {
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(42.dp).background(BladeGold, CircleShape), contentAlignment = Alignment.Center) {
                            Text(barberName.firstOrNull()?.uppercase() ?: "B", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(barberName, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Panel de barbero", color = BladeGold, fontSize = 12.sp)
                        }
                    }
                    HorizontalDivider(color = BladeBorder, modifier = Modifier.padding(bottom = 10.dp))
                    BarberSection.entries.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.title, fontSize = 13.sp) },
                            selected = section == item,
                            onClick = {
                                section = item
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = BladeGold.copy(alpha = 0.15f),
                                unselectedContainerColor = Color.Transparent,
                                selectedTextColor = BladeGold,
                                unselectedTextColor = BladeTextPrimary,
                                selectedIconColor = BladeGold,
                                unselectedIconColor = BladeTextSecondary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = BladeBorder)
                    NavigationDrawerItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
                        label = { Text("Cerrar sesión") },
                        selected = false,
                        onClick = onLogout,
                        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent, unselectedTextColor = BladeError, unselectedIconColor = BladeError)
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = BladeDarkBg,
            topBar = {
                TopAppBar(
                    title = { Text(section.title.uppercase(), color = BladeTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú", tint = BladeTextPrimary)
                        }
                    },
                    actions = {
                        IconButton(onClick = { section = BarberSection.Notifications }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = BladeTextSecondary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = BladeCardBg)
                )
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                when (section) {
                    BarberSection.Today, BarberSection.Appointments -> BarberAppointmentsView(barberName, compact = section == BarberSection.Today)
                    BarberSection.Team -> BarberTeamView()
                    BarberSection.Schedule -> BarberScheduleView()
                    BarberSection.Shop -> BarberShopView()
                    BarberSection.Queue -> BarberQueueView()
                    BarberSection.ServiceNew -> BarberServiceForm()
                    BarberSection.Services -> BarberServicesView()
                    BarberSection.Lunch -> BarberLunchView()
                    BarberSection.News -> BarberNewsView()
                    BarberSection.History -> BarberHistoryView()
                    BarberSection.Notifications -> BarberNotificationsView()
                    BarberSection.EditProfile -> BarberEditProfileView(barberName)
                    BarberSection.Password -> BarberPasswordView()
                }
            }
        }
    }
}

@Composable
private fun BarberAppointmentsView(name: String, compact: Boolean) {
    val appointments = emptyList<Triple<String, String, String>>()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (compact) {
            Column(
                Modifier.fillMaxWidth()
                    .background(Brush.linearGradient(listOf(Color(0xFF1B2B37), Color(0xFF202020))), RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFF314451), RoundedCornerShape(16.dp))
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("PANEL DEL BARBERO", color = Color(0xFF8ABBE3), fontSize = 10.sp, letterSpacing = 1.sp)
                Text("HOLA,\n${name.uppercase()}", color = BladeTextPrimary, fontSize = 20.sp, lineHeight = 23.sp, fontWeight = FontWeight.Bold)
                Row {
                    Text("Tienes ", color = BladeTextSecondary, fontSize = 11.sp)
                    Text("0 citas", color = BladeGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(" programadas para hoy.", color = BladeTextSecondary, fontSize = 11.sp)
                }
            }

            BarberCard {
                Row(verticalAlignment = Alignment.Top) {
                    Box(Modifier.size(30.dp).background(BladeGold.copy(alpha = 0.16f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = BladeGold, modifier = Modifier.size(16.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("0", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                        Text("Citas Hoy", color = BladeTextSecondary, fontSize = 10.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("Sin citas", color = BladeSuccess, fontSize = 9.sp)
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))
            ) {
                Column {
                    Text("MIS PRÓXIMAS CITAS", color = BladeTextSecondary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp))
                    HorizontalDivider(color = BladeBorder)
                    Text("No tienes citas para hoy.", color = BladeTextSecondary, fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 25.dp))
                }
            }
        } else {
            Text("Citas confirmadas del día", color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (appointments.isEmpty()) {
                BarberCard {
                    Text("No hay citas que atender por ahora.", color = BladeTextSecondary, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 20.dp))
                }
            }
        }
        appointments.forEach { (time, client, service) ->
            BarberCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(time, color = BladeGold, fontWeight = FontWeight.Bold, modifier = Modifier.width(76.dp))
                    Column(Modifier.weight(1f)) {
                        Text(client, color = BladeTextPrimary, fontWeight = FontWeight.SemiBold)
                        Text(service, color = BladeTextSecondary, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = BladeTextSecondary)
                }
            }
        }
    }
}

@Composable
private fun BarberTeamView() {
    val barbers = listOf(Triple("Pedro G.", "Fade & Barba", "28"), Triple("Luis M.", "Clásico", "15"), Triple("Andrés C.", "Diseño", "5"), Triple("Camilo R.", "Moderno", "19"))
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Equipo de barberos", "Conoce a tus compañeros")
        var query by remember { mutableStateOf("") }
        BarberInput(query, { query = it }, "Buscar compañero")
        barbers.filter { it.first.contains(query, true) || it.second.contains(query, true) }.forEach { (name, specialty, appointments) ->
            BarberCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(name.first().toString())
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                        Text(specialty, color = BladeTextSecondary, fontSize = 12.sp)
                        Text("Activo", color = BladeSuccess, fontSize = 10.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(appointments, color = BladeGold, fontWeight = FontWeight.Bold)
                        Text("Citas", color = BladeTextSecondary, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun BarberScheduleView() {
    val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Mi horario", "Consulta tu disponibilidad semanal")
        BarberCard {
            Text("Horario asignado por el administrador", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(12.dp))
            days.forEach { day ->
                Row(Modifier.fillMaxWidth().padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(day, color = BladeTextPrimary, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(if (day == "Domingo") "Descanso" else "08:00 – 18:00", color = if (day == "Domingo") BladeTextSecondary else BladeGold, fontSize = 12.sp)
                }
                if (day != "Domingo") HorizontalDivider(color = BladeBorder)
            }
        }
        InfoNotice("Para modificar tu horario, contacta al administrador.")
    }
}

@Composable
private fun BarberShopView() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Estado del local", "Información del estado actual de la barbería")
        BarberCard {
            Text("ESTADO ACTUAL", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(12.dp))
            Surface(color = BladeSuccess.copy(alpha = 0.12f), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text("●  BARBERÍA ABIERTA", color = BladeSuccess, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Desde las 8:00 AM · Cierra 8:00 PM", color = BladeTextSecondary, fontSize = 11.sp)
                }
            }
            listOf("Lunes – Viernes" to "08:00 AM – 08:00 PM", "Sábado" to "09:00 AM – 06:00 PM", "Domingo" to "Cerrado").forEach { (day, hours) ->
                Row(Modifier.fillMaxWidth().padding(top = 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(day, color = BladeTextPrimary, fontSize = 12.sp)
                        Text(hours, color = BladeTextSecondary, fontSize = 10.sp)
                    }
                    Text(if (hours == "Cerrado") "Cerrado" else "Abierto", color = if (hours == "Cerrado") BladeError else BladeSuccess, fontSize = 10.sp)
                }
            }
        }
        InfoNotice("Solo el administrador puede abrir o cerrar la barbería.")
    }
}

@Composable
private fun BarberQueueView() {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ScreenHeading("Fila de espera", "Gestiona los turnos de atención")
        BarberCard {
            Text("La fila de espera no está activa en este momento.", color = BladeTextSecondary, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(vertical = 22.dp))
        }
        Text("La fila está vacía en este momento.", color = BladeTextSecondary, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
private fun BarberServiceForm() {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Registrar servicio", "Crea un nuevo servicio para la barbería")
        BarberCard {
            BarberInput(name, { name = it; saved = false }, "Nombre del servicio")
            Spacer(Modifier.height(10.dp))
            BarberInput(price, { price = it; saved = false }, "Precio")
            Spacer(Modifier.height(10.dp))
            BarberInput(duration, { duration = it; saved = false }, "Duración en minutos")
            Spacer(Modifier.height(16.dp))
            GoldButton("Guardar servicio", enabled = name.isNotBlank() && price.isNotBlank()) { saved = true }
            if (saved) Text("Servicio guardado en esta sesión.", color = BladeSuccess, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun BarberServicesView() {
    val services = listOf(Triple("Corte + barba", "Perfilado y bajo ajuste de la barba", "$35.000"), Triple("Corte clásico", "Corte tradicional", "$25.000"), Triple("Afeitado", "Afeitado con toalla caliente", "$20.000"))
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Servicios de la barbería", "Lista de servicios disponibles")
        services.forEach { (name, description, price) ->
            BarberCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(name.first().toString())
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(description, color = BladeTextSecondary, fontSize = 10.sp)
                    }
                    Text(price, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun BarberLunchView() {
    var saved by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Bloque de almuerzo", "Reserva tu hora de comida (12:00 – 14:00)")
        BarberCard {
            Text("SELECCIONAR BLOQUE", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(12.dp))
            BarberInput("28/08/2026", {}, "Fecha", readOnly = true)
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LunchOption("12:00", "hasta 13:00", Modifier.weight(1f))
                LunchOption("13:00", "hasta 14:00", Modifier.weight(1f))
            }
            Spacer(Modifier.height(14.dp))
            GoldButton(if (saved) "Bloque guardado" else "Guardar bloque") { saved = true }
        }
    }
}

@Composable
private fun LunchOption(time: String, note: String, modifier: Modifier) {
    var selected by remember { mutableStateOf(time == "12:00") }
    Surface(
        modifier = modifier,
        color = if (selected) BladeGold.copy(alpha = 0.12f) else BladeDarkBg,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, if (selected) BladeGold else BladeBorder),
        onClick = { selected = !selected }
    ) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(time, color = BladeTextPrimary, fontWeight = FontWeight.Bold)
            Text(note, color = BladeTextSecondary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun BarberNewsView() {
    var registered by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Novedades de disponibilidad", "Registra inasistencias o cancelaciones anticipadas")
        BarberCard {
            Text("NUEVA NOVEDAD", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(12.dp))
            BarberInput("28/08/2026", {}, "Fecha afectada", readOnly = true)
            Spacer(Modifier.height(10.dp))
            BarberInput(reason, { reason = it; registered = false }, "Motivo")
            Spacer(Modifier.height(12.dp))
            GoldButton("Registrar novedad", enabled = reason.isNotBlank()) { registered = true }
            if (registered) Text("Novedad registrada para revisión.", color = BladeSuccess, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
        ScreenHeading("Mis novedades recientes", "")
        BarberCard {
            Text("Inasistencia · 28/08/2026", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text("Estado: Pendiente de revisión", color = BladeGold, fontSize = 11.sp)
        }
    }
}

@Composable
private fun BarberHistoryView() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Mi historial de atención", "Registro de los servicios que has prestado")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            HistoryMetric("0", "Citas completadas", Modifier.weight(1f))
            HistoryMetric("$0", "Ingresos generados", Modifier.weight(1f))
        }
        HistoryMetric("0", "Servicios ejecutados", Modifier.fillMaxWidth())
        BarberCard {
            Text("DETALLE DE ATENCIONES", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(20.dp))
            Text("No hay atenciones para el período seleccionado.", color = BladeTextSecondary, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 20.dp))
        }
    }
}

@Composable
private fun BarberNotificationsView() {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ScreenHeading("Notificaciones", "Alertas y avisos sobre tus citas")
        BarberCard {
            Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = BladeTextSecondary, modifier = Modifier.size(28.dp).align(Alignment.CenterHorizontally))
            Text("No tienes notificaciones aún", color = BladeTextSecondary, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp, bottom = 12.dp))
        }
    }
}

@Composable
private fun BarberEditProfileView(name: String) {
    var displayName by remember { mutableStateOf(name) }
    var email by remember { mutableStateOf("juan.barbero@barberos.com") }
    var phone by remember { mutableStateOf("+57 300 000 0000") }
    var specialty by remember { mutableStateOf("Fade & Barba") }
    var description by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        BarberCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Avatar(displayName.firstOrNull()?.toString() ?: "J", size = 54)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Editar perfil", color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Toca la foto para cambiarla", color = BladeTextSecondary, fontSize = 11.sp)
                }
            }
        }
        BarberCard {
            Text("DATOS PERSONALES", color = BladeTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.height(10.dp))
            BarberInput(displayName, { displayName = it; saved = false }, "Nombre")
            Spacer(Modifier.height(8.dp))
            BarberInput(email, { email = it; saved = false }, "Correo")
            Spacer(Modifier.height(8.dp))
            BarberInput(phone, { phone = it; saved = false }, "Teléfono")
            Spacer(Modifier.height(8.dp))
            BarberInput(specialty, { specialty = it; saved = false }, "Especialidad")
            Spacer(Modifier.height(8.dp))
            BarberInput(description, { description = it; saved = false }, "Descripción", minLines = 3)
            Spacer(Modifier.height(12.dp))
            GoldButton("Guardar perfil") { saved = true }
            if (saved) Text("Cambios guardados en esta sesión.", color = BladeSuccess, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun BarberPasswordView() {
    var current by remember { mutableStateOf("") }
    var new by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        BarberCard {
            Text("CAMBIAR CONTRASEÑA", color = BladeTextSecondary, fontSize = 11.sp)
            HorizontalDivider(color = BladeBorder, modifier = Modifier.padding(vertical = 12.dp))
            BarberInput(current, { current = it; message = "" }, "Contraseña actual", isPassword = true)
            Spacer(Modifier.height(12.dp))
            BarberInput(new, { new = it; message = "" }, "Nueva contraseña", isPassword = true)
            Spacer(Modifier.height(12.dp))
            BarberInput(confirm, { confirm = it; message = "" }, "Confirmar contraseña", isPassword = true)
            Spacer(Modifier.height(16.dp))
            GoldButton("Actualizar contraseña", enabled = current.isNotBlank() && new.isNotBlank() && confirm.isNotBlank()) {
                message = if (new == confirm) "Contraseña actualizada en esta sesión." else "Las contraseñas nuevas no coinciden."
            }
            if (message.isNotBlank()) Text(message, color = if (new == confirm) BladeSuccess else BladeError, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun ScreenHeading(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        if (subtitle.isNotBlank()) Text(subtitle, color = BladeTextSecondary, fontSize = 12.sp)
    }
}

@Composable
private fun BarberCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))
    ) { Column(Modifier.padding(16.dp), content = content) }
}

@Composable
private fun BarberInput(value: String, onValueChange: (String) -> Unit, label: String, readOnly: Boolean = false, minLines: Int = 1, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = Modifier.fillMaxWidth(),
        readOnly = readOnly,
        minLines = minLines,
        singleLine = minLines == 1 && !isPassword,
        visualTransformation = if (isPassword) androidx.compose.ui.text.input.PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BladeGold, unfocusedBorderColor = BladeBorder, focusedLabelColor = BladeGold, unfocusedLabelColor = BladeTextSecondary, cursorColor = BladeGold)
    )
}

@Composable
private fun GoldButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = BladeGold, contentColor = Color.Black)) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@Composable
private fun InfoNotice(text: String) {
    Surface(color = Color(0xFF16202A), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().border(1.dp, Color(0xFF283746), RoundedCornerShape(10.dp))) {
        Text(text, color = Color(0xFF9CCBFA), fontSize = 11.sp, modifier = Modifier.padding(12.dp))
    }
}

@Composable
private fun Avatar(initial: String, size: Int = 42) {
    Box(Modifier.size(size.dp).background(BladeGold.copy(alpha = 0.14f), CircleShape).border(1.dp, BladeGold, CircleShape), contentAlignment = Alignment.Center) {
        Text(initial, color = BladeGold, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun HistoryMetric(value: String, label: String, modifier: Modifier) {
    Column(modifier) {
        BarberCard {
            Text(value, color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = BladeTextSecondary, fontSize = 10.sp)
        }
    }
}

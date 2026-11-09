package com.example.bladebarber.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*
import kotlinx.coroutines.launch

data class NavItem(val id: String, val title: String, val icon: ImageVector, val badge: String? = null)
data class NavSection(val title: String, val items: List<NavItem>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDashboardScreen(
    clientName: String,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentPanel by remember { mutableStateOf("welcome") }

    val navSections = listOf(
        NavSection("Principal", listOf(
            NavItem("welcome", "Inicio", Icons.Default.Home)
        )),
        NavSection("Mi Cuenta", listOf(
            NavItem("profile-view", "Mi Perfil", Icons.Default.Person),
            NavItem("profile-edit", "Editar Perfil", Icons.Default.Edit),
            NavItem("change-pass", "Contraseña", Icons.Default.Lock)
        )),
        NavSection("Barbería", listOf(
            NavItem("agenda", "Agendar Cita", Icons.Default.DateRange),
            NavItem("mis-citas", "Mis Citas", Icons.AutoMirrored.Filled.List, badge = "2"),
            NavItem("fila", "Fila de Espera", Icons.Default.Person, badge = "3"),
            NavItem("barberos", "Nuestros Barberos", Icons.Default.Face)
        )),
        NavSection("Comunidad", listOf(
            NavItem("muro", "Muro", Icons.Default.Email),
            NavItem("publicar", "Publicar", Icons.Default.Add)
        )),
        NavSection("Ofertas & Beneficios", listOf(
            NavItem("promociones", "Promociones", Icons.Default.Star),
            NavItem("fidelidad", "Mi Tarjeta Fidelidad", Icons.Default.ThumbUp),
            NavItem("notificaciones", "Notificaciones", Icons.Default.Notifications, badge = "!")
        ))
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BladeCardBg,
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Header
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(BladeGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(clientName.take(1).uppercase(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(clientName, color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                            Text("Cliente", color = BladeTextSecondary, fontSize = 12.sp)
                        }
                    }

                    // Navigation Items
                    navSections.forEach { section ->
                        Text(
                            text = section.title.uppercase(),
                            color = BladeTextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        section.items.forEach { item ->
                            NavigationDrawerItem(
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                        Text(item.title, modifier = Modifier.weight(1f))
                                        if (item.badge != null) {
                                            Box(
                                                modifier = Modifier
                                                    .background(BladeGold, RoundedCornerShape(12.dp))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(item.badge, color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                },
                                icon = { Icon(item.icon, contentDescription = null) },
                                selected = currentPanel == item.id,
                                onClick = {
                                    currentPanel = item.id
                                    scope.launch { drawerState.close() }
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = BladeGold.copy(alpha = 0.15f),
                                    unselectedContainerColor = Color.Transparent,
                                    selectedIconColor = BladeGold,
                                    unselectedIconColor = BladeTextSecondary,
                                    selectedTextColor = BladeGold,
                                    unselectedTextColor = BladeTextSecondary
                                ),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = BladeDarkBg,
            topBar = {
                TopAppBar(
                    title = {
                        val currentTitle = navSections.flatMap { it.items }.find { it.id == currentPanel }?.title ?: "BladeBarber"
                        Text(currentTitle, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = BladeTextPrimary)
                        }
                    },
                    actions = {
                        IconButton(onClick = { currentPanel = "notificaciones" }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = BladeTextSecondary)
                        }
                        IconButton(onClick = { currentPanel = "agenda" }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Agendar", tint = BladeTextSecondary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = BladeCardBg)
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (currentPanel) {
                    "welcome" -> WelcomePanel(clientName) { currentPanel = it }
                    "profile-view" -> ProfileViewPanel(clientName) { currentPanel = it }
                    "profile-edit" -> ProfileEditPanel(clientName) { currentPanel = it }
                    "change-pass" -> ChangePassPanel()
                    "agenda" -> AgendaPanel()
                    "mis-citas" -> MisCitasPanel()
                    "fila" -> FilaPanel()
                    "barberos" -> BarberosPanel()
                    "muro" -> MuroPanel()
                    "publicar" -> PublicarPanel()
                    "promociones" -> PromocionesPanel()
                    "fidelidad" -> FidelidadPanel()
                    "notificaciones" -> NotificacionesPanel()
                }
            }
        }
    }
}

@Composable
fun WelcomePanel(clientName: String, onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Banner
        Column {
            Text("Tu Espacio en BladeBarber", color = BladeGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("Hola,\n$clientName", color = BladeTextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
            Text("Bienvenido de vuelta. ¿Listo para tu próximo corte?", color = BladeTextSecondary, fontSize = 14.sp)
        }

        // Stats Grid
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(modifier = Modifier.weight(1f), icon = Icons.Default.DateRange, value = "2", label = "Citas Próximas")
            StatCard(modifier = Modifier.weight(1f), icon = Icons.Default.Check, value = "14", label = "Citas Totales")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(modifier = Modifier.weight(1f), icon = Icons.Default.Person, value = "#3", label = "En la Fila Hoy")
            StatCard(modifier = Modifier.weight(1f), icon = Icons.Default.Home, value = "AB", label = "Barbería Abierta")
        }

        // Próxima Cita
        SectionCard(title = "Tu Próxima Cita") {
            ListItemMock(title = "Corte + Barba", subtitle = "10:00 AM · Barbero: Luis Mora", trailing = "07 May")
            ListItemMock(title = "Corte Clásico", subtitle = "2:30 PM · Barbero: Pedro Gómez", trailing = "15 May")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onNavigate("agenda") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BladeGold)
            ) {
                Text("Agendar Nueva Cita", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        // Estado y Fila
        SectionCard(title = "Estado & Fila") {
            Text("BARBERÍA ABIERTA", color = BladeSuccess, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("8:00 AM – 8:00 PM", color = BladeTextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BladeDarkBg, RoundedCornerShape(10.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tu posición en la fila", color = BladeTextSecondary, fontSize = 12.sp)
                    Text("#3", color = BladeGold, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                    Text("≈ 20 minutos de espera", color = BladeTextSecondary, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { onNavigate("fila") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)
            ) {
                Text("Ver Fila Completa")
            }
        }

        // Últimas del Muro
        SectionCard(title = "Últimas del Muro") {
            ListItemMock(title = "Admin BladeBarber", subtitle = "Hoy ya tenemos disponibilidad para citas express...", trailing = "Hace 2h")
            ListItemMock(title = "Luis Mora", subtitle = "Nuevas técnicas de fade disponibles esta semana", trailing = "Hace 5h")
            TextButton(onClick = { onNavigate("muro") }) {
                Text("Ver todo", color = BladeGold)
            }
        }
    }
}

@Composable
fun ProfileViewPanel(clientName: String, onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(
            modifier = Modifier.size(80.dp).clip(CircleShape).background(BladeGold),
            contentAlignment = Alignment.Center
        ) {
            Text(clientName.take(1).uppercase(), color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Text(clientName, color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        SectionCard("Información") {
            ListItemMock("Nombre", clientName, "")
            ListItemMock("Correo", "cliente@email.com", "")
            ListItemMock("Teléfono", "+57 300 123 4567", "")
        }

        SectionCard("Acciones") {
            Button(onClick = { onNavigate("profile-edit") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = BladeGold)) {
                Text("Editar Perfil", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { onNavigate("change-pass") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                Text("Cambiar Contraseña")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPanel(clientName: String, onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionCard("Editar Datos Personales") {
            OutlinedTextField(
                value = clientName,
                onValueChange = {},
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BladeTextPrimary,
                    unfocusedTextColor = BladeTextPrimary,
                    cursorColor = BladeGold,
                    focusedBorderColor = BladeGold
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "cliente@email.com",
                onValueChange = {},
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BladeTextPrimary,
                    unfocusedTextColor = BladeTextPrimary,
                    cursorColor = BladeGold,
                    focusedBorderColor = BladeGold
                )
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = { onNavigate("profile-view") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = BladeGold)) {
                Text("Guardar Cambios", color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePassPanel() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionCard("Cambiar Contraseña") {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña Actual") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BladeTextPrimary,
                    unfocusedTextColor = BladeTextPrimary,
                    cursorColor = BladeGold,
                    focusedBorderColor = BladeGold
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nueva Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BladeTextPrimary,
                    unfocusedTextColor = BladeTextPrimary,
                    cursorColor = BladeGold,
                    focusedBorderColor = BladeGold
                )
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = BladeGold)) {
                Text("Actualizar Contraseña", color = Color.Black)
            }
        }
    }
}

@Composable
fun GenericPanelMock(title: String, description: String) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(40.dp))
        Icon(Icons.Default.Info, contentDescription = null, tint = BladeGold, modifier = Modifier.size(64.dp))
        Text(title, color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(description, color = BladeTextSecondary, fontSize = 14.sp)
    }
}

@Composable
fun AgendaPanel() = GenericPanelMock("Agendar Cita", "Formulario para seleccionar fecha, barbero y servicio.")

@Composable
fun MisCitasPanel() = GenericPanelMock("Mis Citas", "Historial y citas pendientes.")

@Composable
fun FilaPanel() = GenericPanelMock("Fila de Espera", "Turnos en tiempo real y tiempo estimado.")

@Composable
fun BarberosPanel() = GenericPanelMock("Nuestros Barberos", "Conoce al equipo de la barbería.")

@Composable
fun MuroPanel() = GenericPanelMock("Muro de Comunidad", "Publicaciones, novedades y fotos.")

@Composable
fun PublicarPanel() = GenericPanelMock("Publicar en el Muro", "Formulario para subir tu estilo.")

@Composable
fun PromocionesPanel() = GenericPanelMock("Promociones", "Ofertas y descuentos disponibles.")

@Composable
fun FidelidadPanel() = GenericPanelMock("Tarjeta de Fidelidad", "Acumula cortes y gana recompensas.")

@Composable
fun NotificacionesPanel() = GenericPanelMock("Notificaciones", "Alertas y mensajes importantes.")

@Composable
fun StatCard(modifier: Modifier = Modifier, icon: ImageVector, value: String, label: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = BladeGold, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(label, color = BladeTextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 12.dp))
            content()
        }
    }
}

@Composable
fun ListItemMock(title: String, subtitle: String, trailing: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = BladeTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, color = BladeTextSecondary, fontSize = 12.sp)
        }
        Text(trailing, color = BladeGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

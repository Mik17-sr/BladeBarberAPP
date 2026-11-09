package com.example.bladebarber.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Send
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
                    "agenda" -> AgendaPanel { currentPanel = it }
                    "mis-citas" -> MisCitasPanel { currentPanel = it }
                    "fila" -> FilaPanel()
                    "barberos" -> BarberosPanel { currentPanel = it }
                    "muro" -> MuroPanel { currentPanel = it }
                    "publicar" -> PublicarPanel { currentPanel = it }
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
        SectionCard(title = "TU PRÓXIMA CITA") {
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.background(BladeDarkBg, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("07", color = VioletPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("MAY", color = BladeTextSecondary, fontSize = 10.sp)
                }
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("Corte + Barba", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("10:00 AM · Barbero: Luis Mora", color = BladeTextSecondary, fontSize = 12.sp)
                }
                Text("Próxima", color = VioletPrimary, fontSize = 10.sp, modifier = Modifier.background(VioletPrimary.copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(horizontal=8.dp, vertical=4.dp))
            }
            
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.background(BladeDarkBg, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("15", color = VioletPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("MAY", color = BladeTextSecondary, fontSize = 10.sp)
                }
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("Corte Clásico", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("2:30 PM · Barbero: Pedro Gómez", color = BladeTextSecondary, fontSize = 12.sp)
                }
                Text("Pendiente", color = Color(0xFFF59E0B), fontSize = 10.sp, modifier = Modifier.background(Color(0xFFF59E0B).copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(horizontal=8.dp, vertical=4.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNavigate("agenda") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)
            ) {
                Text("Agendar Nueva Cita", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // Estado y Fila
        SectionCard(title = "ESTADO & FILA") {
            Row(
                modifier = Modifier.fillMaxWidth().background(GreenAccent.copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.size(8.dp).clip(CircleShape).background(GreenAccent))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("BARBERÍA ABIERTA", color = GreenAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("8:00 AM – 8:00 PM", color = GreenAccent.copy(alpha=0.8f), fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BladeDarkBg, RoundedCornerShape(10.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TU POSICIÓN EN LA FILA", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Text("#3", color = VioletPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                    Text("≈ 20 minutos de espera", color = BladeTextSecondary, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { onNavigate("fila") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)
            ) {
                Icon(Icons.AutoMirrored.Filled.List, null, modifier=Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Ver Fila Completa")
            }
        }

        // Últimas del Muro
        SectionCard(title = "ÚLTIMAS DEL MURO") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(32.dp).clip(CircleShape).background(BladeDarkBg), contentAlignment = Alignment.Center) { Text("A", color = BladeTextPrimary) }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Admin BladeBarber", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Hoy ya tenemos disponibilidad para...", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                }
                Text("Hace 2h", color = VioletPrimary, fontSize = 10.sp, modifier = Modifier.background(VioletPrimary.copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(horizontal=8.dp, vertical=4.dp))
            }
        }
    }
}

@Composable
fun ProfileViewPanel(clientName: String, onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Hero Card
        Card(
            colors = CardDefaults.cardColors(containerColor = BladeCardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
        ) {
            Column(Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(BladeDarkBg).border(2.dp, VioletPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(clientName.take(1).uppercase(), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(16.dp))
                Text(clientName, color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("carlos@barberos.com", color = BladeTextSecondary, fontSize = 14.sp)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Cliente", color = BladeTextPrimary, fontSize = 10.sp, modifier = Modifier.background(BladeDarkBg, RoundedCornerShape(12.dp)).border(1.dp, VioletPrimary, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                    Text("Frecuente", color = BladeTextPrimary, fontSize = 10.sp, modifier = Modifier.background(BladeDarkBg, RoundedCornerShape(12.dp)).border(1.dp, VioletPrimary, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                    Text("Activo", color = BladeTextPrimary, fontSize = 10.sp, modifier = Modifier.background(BladeDarkBg, RoundedCornerShape(12.dp)).border(1.dp, VioletPrimary, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
        }

        SectionCard("INFORMACIÓN") {
            ListItemMock("Nombre", clientName, "")
            ListItemMock("Correo", "carlos@barberos.com", "")
            ListItemMock("Teléfono", "+57 300 123 4567", "")
            ListItemMock("Miembro desde", "26 Aug 2024", "")
        }
        
        SectionCard("ACCIONES") {
            Button(onClick = { onNavigate("profile-edit") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Editar Perfil", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { onNavigate("change-pass") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = BladeTextPrimary, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Cambiar Contraseña")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { onNavigate("mis-citas") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = BladeTextPrimary, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Ver Mis Citas")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPanel(clientName: String, onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BladeCardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
        ) {
            Row(Modifier.padding(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(BladeDarkBg).border(2.dp, VioletPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(clientName.take(1).uppercase(), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("EDITAR PERFIL", color = BladeTextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Haz clic en la foto para\ncambiarla", color = BladeTextSecondary, fontSize = 12.sp)
                }
            }
        }

        SectionCard("DATOS PERSONALES") {
            Text("NOMBRE COMPLETO", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(value = clientName, onValueChange = {}, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary))
            
            Spacer(Modifier.height(16.dp))
            Text("CORREO ELECTRÓNICO", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(value = "carlos@barberos.com", onValueChange = {}, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary))
            
            Spacer(Modifier.height(16.dp))
            Text("USUARIO", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(value = "carlosm", onValueChange = {}, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary))
            
            Spacer(Modifier.height(16.dp))
            Text("TELÉFONO", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(value = "+57 300 123 4567", onValueChange = {}, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary))
            
            Spacer(Modifier.height(16.dp))
            Text("FOTO DE PERFIL", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Seleccionar imagen")
            }

            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onNavigate("profile-view") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                    Text("Guardar Cambios", color = Color.White)
                }
                OutlinedButton(onClick = { onNavigate("profile-view") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePassPanel() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionCard("CAMBIAR CONTRASEÑA") {
            Text("CONTRASEÑA ACTUAL", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = "********", onValueChange = {}, modifier = Modifier.fillMaxWidth(), 
                leadingIcon = { Icon(Icons.Default.Lock, null, tint=BladeTextSecondary, modifier=Modifier.size(16.dp)) },
                trailingIcon = { Icon(Icons.Default.Visibility, null, tint=BladeTextSecondary, modifier=Modifier.size(16.dp)) },
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary)
            )
            
            Spacer(Modifier.height(16.dp))
            Text("NUEVA CONTRASEÑA", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = "********", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, null, tint=BladeTextSecondary, modifier=Modifier.size(16.dp)) },
                trailingIcon = { Icon(Icons.Default.Visibility, null, tint=BladeTextSecondary, modifier=Modifier.size(16.dp)) },
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary)
            )
            
            Spacer(Modifier.height(16.dp))
            Text("CONFIRMAR CONTRASEÑA", color = BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = "********", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, null, tint=BladeTextSecondary, modifier=Modifier.size(16.dp)) },
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary)
            )
            
            Spacer(Modifier.height(24.dp))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Actualizar Contraseña", color = Color.White)
            }
        }
    }
}

val VioletPrimary = Color(0xFF8B5CF6)
val GreenAccent = Color(0xFF10B981)

@Composable
fun AgendaPanel(onNavigate: (String) -> Unit) {
    var currentStep by remember { mutableStateOf(1) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("AGENDAR CITA", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Reserva tu turno en pocos pasos", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            StepItem("1", "Servicio", currentStep >= 1)
            HorizontalDivider(Modifier.weight(1f).padding(horizontal = 8.dp), color = if(currentStep > 1) VioletPrimary else BladeBorder)
            StepItem("2", "Barbero", currentStep >= 2)
            HorizontalDivider(Modifier.weight(1f).padding(horizontal = 8.dp), color = if(currentStep > 2) VioletPrimary else BladeBorder)
            StepItem("3", "Fecha & Hora", currentStep >= 3)
            HorizontalDivider(Modifier.weight(1f).padding(horizontal = 8.dp), color = if(currentStep > 3) VioletPrimary else BladeBorder)
            StepItem("4", "Confirmar", currentStep >= 4)
        }
        
        when(currentStep) {
            1 -> {
                SectionCard("ELIGE TU SERVICIO") {
                    Text("Puedes elegir más de uno", color = BladeTextSecondary, fontSize = 12.sp)
                    Spacer(Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, VioletPrimary, RoundedCornerShape(12.dp))
                    ) {
                        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("✂ Corte + Barba", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                                Text("$25.000", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("40 min · Servicio disponible", color = BladeTextSecondary, fontSize = 12.sp)
                            }
                            RadioButton(selected = true, onClick = {}, colors = RadioButtonDefaults.colors(selectedColor = VioletPrimary))
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(12.dp))
                    ) {
                        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Corte Clásico", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                                Text("$15.000", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("30 min · Servicio disponible", color = BladeTextSecondary, fontSize = 12.sp)
                            }
                            RadioButton(selected = false, onClick = {}, colors = RadioButtonDefaults.colors(selectedColor = VioletPrimary))
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = { currentStep = 2 }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                        Text("Siguiente — Elegir Barbero", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            2 -> {
                SectionCard("ELIGE UN BARBERO") {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, VioletPrimary, RoundedCornerShape(12.dp))
                    ) {
                        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(BladeCardBg).border(1.dp, VioletPrimary, CircleShape), contentAlignment = Alignment.Center) {
                                Text("L", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Luis Mora", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                                Text("Especialista en Fade", color = BladeTextSecondary, fontSize = 12.sp)
                            }
                            RadioButton(selected = true, onClick = {}, colors = RadioButtonDefaults.colors(selectedColor = VioletPrimary))
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(12.dp))
                    ) {
                        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(BladeCardBg).border(1.dp, BladeBorder, CircleShape), contentAlignment = Alignment.Center) {
                                Text("P", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Pedro Gómez", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                                Text("Barbero Clásico", color = BladeTextSecondary, fontSize = 12.sp)
                            }
                            RadioButton(selected = false, onClick = {}, colors = RadioButtonDefaults.colors(selectedColor = VioletPrimary))
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { currentStep = 1 }, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                            Text("Atrás")
                        }
                        Button(onClick = { currentStep = 3 }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                            Text("Siguiente", color = Color.White)
                        }
                    }
                }
            }
            3 -> {
                SectionCard("FECHA Y HORA") {
                    Text("Mayo 2024", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 17..21) {
                            val isSelected = i == 18
                            Column(
                                modifier = Modifier
                                    .background(if (isSelected) VioletPrimary else BladeDarkBg, RoundedCornerShape(8.dp))
                                    .border(1.dp, if (isSelected) VioletPrimary else BladeBorder, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(listOf("VIE", "SÁB", "DOM", "LUN", "MAR")[i-17], color = if(isSelected) Color.White else BladeTextSecondary, fontSize = 10.sp)
                                Text(i.toString(), color = if(isSelected) Color.White else BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Horarios Disponibles", color = BladeTextSecondary, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = false, onClick = {}, label = { Text("3:00 PM") })
                        FilterChip(selected = true, onClick = {}, label = { Text("4:00 PM") }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = VioletPrimary, selectedLabelColor = Color.White))
                        FilterChip(selected = false, onClick = {}, label = { Text("5:30 PM") })
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { currentStep = 2 }, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                            Text("Atrás")
                        }
                        Button(onClick = { currentStep = 4 }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                            Text("Siguiente", color = Color.White)
                        }
                    }
                }
            }
            4 -> {
                SectionCard("CONFIRMA TU RESERVA") {
                    Column(Modifier.background(BladeDarkBg, RoundedCornerShape(12.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Servicio", color = BladeTextSecondary, fontSize = 12.sp)
                            Text("Corte + Barba", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Barbero", color = BladeTextSecondary, fontSize = 12.sp)
                            Text("Luis Mora", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Fecha y Hora", color = BladeTextSecondary, fontSize = 12.sp)
                            Text("Sáb 18 May, 4:00 PM", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(color = BladeBorder)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Estimado", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                            Text("$25.000", color = VioletPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { currentStep = 3 }, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                            Text("Atrás")
                        }
                        Button(onClick = { onNavigate("mis-citas") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = GreenAccent)) {
                            Text("Confirmar Cita", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepItem(number: String, label: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(if(active) VioletPrimary else BladeDarkBg).border(1.dp, if(active) VioletPrimary else BladeBorder, CircleShape), contentAlignment = Alignment.Center) {
            Text(number, color = if(active) Color.White else BladeTextSecondary, fontWeight = FontWeight.Bold)
        }
        Text(label, color = if(active) VioletPrimary else BladeTextSecondary, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun MisCitasPanel(onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("MIS CITAS", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Historial y próximas reservas", color = BladeTextSecondary, fontSize = 14.sp)
            }
            Button(onClick = { onNavigate("agenda") }, colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                Text("+ Nueva", color = Color.White)
            }
        }
        
        SectionCard("PRÓXIMAS") {
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.background(BladeDarkBg, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("18", color = VioletPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("MAY", color = BladeTextSecondary, fontSize = 10.sp)
                }
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("Corte Fade + Barba", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("4:00 PM · Barbero: Juan Rodríguez", color = BladeTextSecondary, fontSize = 12.sp)
                }
                Text("Pendiente", color = Color(0xFFF59E0B), fontSize = 10.sp, modifier = Modifier.background(Color(0xFFF59E0B).copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(horizontal=8.dp, vertical=4.dp))
            }
        }

        SectionCard("HISTORIAL") {
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.background(BladeDarkBg, RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("02", color = BladeTextSecondary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("MAY", color = BladeTextSecondary, fontSize = 10.sp)
                }
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("Corte Clásico", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("11:30 AM · Barbero: Luis Mora", color = BladeTextSecondary, fontSize = 12.sp)
                }
                Text("Completada", color = GreenAccent, fontSize = 10.sp, modifier = Modifier.background(GreenAccent.copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(horizontal=8.dp, vertical=4.dp))
            }
        }
    }
}

@Composable
fun FilaPanel() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("FILA DE ESPERA", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Turno virtual en tiempo real", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        SectionCard("TU TURNO") {
            Column(Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Tu posición en la fila", color = BladeTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text("#3", color = VioletPrimary, fontSize = 56.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("≈ 20 minutos de espera estimados", color = BladeTextPrimary, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
                OutlinedButton(onClick = {}, colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)), modifier = Modifier.fillMaxWidth()) {
                    Text("Abandonar Fila")
                }
            }
        }
        
        SectionCard("FILA ACTUAL") {
            Row(Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("3 personas en espera", color = BladeTextSecondary, fontSize = 12.sp, modifier = Modifier.background(BladeDarkBg, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
            }
            
            // User 1
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(32.dp).clip(CircleShape).background(BladeDarkBg), contentAlignment = Alignment.Center) { Text("1", color = BladeTextSecondary, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Andrés F.", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("En silla - Luis Mora", color = GreenAccent, fontSize = 12.sp)
                }
            }
            // User 2
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(32.dp).clip(CircleShape).background(BladeDarkBg), contentAlignment = Alignment.Center) { Text("2", color = BladeTextSecondary, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Mateo Gómez", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Esperando", color = BladeTextSecondary, fontSize = 12.sp)
                }
            }
            // Current User
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp).background(VioletPrimary.copy(alpha=0.1f), RoundedCornerShape(8.dp)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(32.dp).clip(CircleShape).background(VioletPrimary), contentAlignment = Alignment.Center) { Text("3", color = Color.White, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("Carlos Méndez (Tú)", color = VioletPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Esperando", color = VioletPrimary, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BarberosPanel(onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("NUESTROS BARBEROS", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("El equipo que te pondrá increíble", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        BarberoCardMock("Juan Rodríguez", "Barbero Especialista", "J", "4.8", "120 reseñas", onNavigate)
        BarberoCardMock("Luis Mora", "Especialista en Fade", "L", "4.9", "85 reseñas", onNavigate)
        BarberoCardMock("Pedro Gómez", "Colorimetría y Diseños", "P", "4.5", "40 reseñas", onNavigate)
    }
}

@Composable
fun BarberoCardMock(name: String, role: String, initial: String, rating: String, reviews: String, onNavigate: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(BladeDarkBg).border(1.dp, VioletPrimary, CircleShape), contentAlignment = Alignment.Center) {
                    Text(initial, color = BladeTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                    Text(role, color = BladeTextSecondary, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = VioletPrimary, modifier = Modifier.size(12.dp))
                        repeat(4) { Icon(Icons.Default.Star, contentDescription = null, tint = BladeGold, modifier = Modifier.size(12.dp)) }
                        Spacer(Modifier.width(4.dp))
                        Text(rating, color = BladeTextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(" ($reviews)", color = BladeTextSecondary, fontSize = 10.sp)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onNavigate("agenda") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                    Text("Agendar", color = Color.White)
                }
                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                    Text("Ver reseñas")
                }
            }
        }
    }
}

@Composable
fun MuroPanel(onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("MURO", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Publicaciones de la comunidad BladeBarber", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        Button(onClick = { onNavigate("publicar") }, colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Publicar", color = Color.White)
        }
        
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = true, onClick = {}, label = { Text("Todos") }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = VioletPrimary, selectedLabelColor = Color.White))
            FilterChip(selected = false, onClick = {}, label = { Text("Administrador") }, colors = FilterChipDefaults.filterChipColors(labelColor = BladeTextSecondary))
        }
        
        PostCardMock("Admin BladeBarber", "hace 2 horas", "¡Nuevas promociones por el mes del padre! ✂️ Revisa la sección de ofertas para descubrir todo lo que hemos preparado para ti.", true)
        PostCardMock("Carlos Méndez", "hace 1 día", "Excelente atención de Luis Mora, el fade quedó impecable como siempre. 10/10 recomendado.", false)
        PostCardMock("Pedro Gómez", "hace 3 días", "Un clásico que nunca falla. Arrancando la semana con toda la actitud en la barbería.", false)
    }
}

@Composable
fun PostCardMock(name: String, time: String, content: String, isAdmin: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(BladeDarkBg), contentAlignment = Alignment.Center) {
                    Text(name.take(1), color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(time, color = BladeTextSecondary, fontSize = 12.sp)
                }
                if(isAdmin) {
                    Text("Admin", color = VioletPrimary, fontSize = 10.sp, modifier = Modifier.border(1.dp, VioletPrimary, RoundedCornerShape(8.dp)).padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(Modifier.fillMaxWidth().height(150.dp).background(BladeDarkBg, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Image, contentDescription = null, tint = BladeTextSecondary)
            }
            Spacer(Modifier.height(12.dp))
            Text(content, color = BladeTextPrimary, fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = BladeTextSecondary, modifier = Modifier.size(20.dp))
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = BladeTextSecondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicarPanel(onNavigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("NUEVA PUBLICACIÓN", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Comparte con la comunidad BladeBarber", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        SectionCard("") {
            Text("¿QUÉ QUIERES COMPARTIR?", color = BladeTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = "", 
                onValueChange = {},
                placeholder = { Text("Comparte tu experiencia, una foto de tu corte, una recomendación...") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = BladeBorder, focusedBorderColor = VioletPrimary)
            )
            Spacer(Modifier.height(16.dp))
            Text("IMAGEN (OPCIONAL)", color = BladeTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Seleccionar imagen")
            }
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = VioletPrimary)) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Publicar", color = Color.White)
                }
                OutlinedButton(onClick = { onNavigate("muro") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.outlinedButtonColors(contentColor = BladeTextPrimary)) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun PromocionesPanel() {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
            Column {
                Text("PROMOCIONES", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Ofertas y beneficios activos para ti", color = BladeTextSecondary, fontSize = 14.sp)
            }
            Text("Actualizado: 12:07 a.m.", color = BladeTextSecondary, fontSize = 10.sp)
        }
        
        SectionCard("OFERTAS ACTUALES") {
            PromoCardMock("20% OFF en Corte + Barba", "Presenta este cupón en tu próxima visita. Válido hasta el 30 de Mayo.", "20%")
            Spacer(Modifier.height(12.dp))
            PromoCardMock("Día del Estudiante", "Corte clásico a $15.000 mostrando tu carnet estudiantil. (Martes y Miércoles)", "PROMO")
        }
    }
}

@Composable
fun PromoCardMock(title: String, desc: String, tag: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().border(1.dp, VioletPrimary, RoundedCornerShape(12.dp))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(VioletPrimary.copy(alpha=0.2f)), contentAlignment = Alignment.Center) {
                Text(tag, color = VioletPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(desc, color = BladeTextSecondary, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun FidelidadPanel() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("MI TARJETA DE FIDELIDAD", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Acumula visitas y desbloquea beneficios exclusivos", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        Card(
            colors = CardDefaults.cardColors(containerColor = BladeDarkBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, VioletPrimary, RoundedCornerShape(16.dp))
        ) {
            Column(Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ContentCut, contentDescription = null, tint = BladeTextSecondary, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("BLADEBARBER", color = BladeTextSecondary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 12.sp)
                }
                Spacer(Modifier.height(24.dp))
                Text("7", color = VioletPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                Text("visitas de 10 para tu próximo beneficio", color = BladeTextSecondary, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
                
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    for(i in 1..8) {
                        val isActive = i <= 7
                        Box(Modifier.size(24.dp).clip(CircleShape).background(if(isActive) VioletPrimary else BladeCardBg), contentAlignment = Alignment.Center) {
                            Text(i.toString(), color = if(isActive) Color.White else BladeTextSecondary, fontSize = 10.sp)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(24.dp).clip(CircleShape).background(BladeCardBg), contentAlignment = Alignment.Center) {
                        Text("9", color = BladeTextSecondary, fontSize = 10.sp)
                    }
                    Spacer(Modifier.width(18.dp))
                    Box(Modifier.size(24.dp).clip(CircleShape).background(BladeCardBg), contentAlignment = Alignment.Center) {
                        Text("10", color = BladeTextSecondary, fontSize = 10.sp)
                    }
                }
                
                Spacer(Modifier.height(24.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Corte gratis", color = BladeTextPrimary, fontWeight = FontWeight.Bold)
                    Text("CLIENTE FRECUENTE", color = VioletPrimary, fontSize = 10.sp, modifier = Modifier.border(1.dp, VioletPrimary, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
        }
        
        SectionCard("HISTORIAL DE BENEFICIOS") {
            ListItemMock("Corte Gratis Canjeado", "Beneficio completado por 10 visitas.", "15 Feb 2024")
            ListItemMock("Descuento 50% en Barba", "Cupón promocional por el día del padre.", "19 Jun 2023")
        }
    }
}

@Composable
fun NotificacionesPanel() {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("NOTIFICACIONES", color = BladeTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Alertas y avisos sobre tus citas", color = BladeTextSecondary, fontSize = 14.sp)
        }
        
        TextButton(onClick = {}) {
            Icon(Icons.Default.Check, contentDescription = null, tint = VioletPrimary, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Marcar todas como leídas", color = VioletPrimary)
        }
        
        SectionCard("HOY") {
            ListItemMock("Cita Confirmada", "Tu cita de mañana a las 4:00 PM con Luis ha sido confirmada.", "Hace 2h")
            ListItemMock("Nueva Promo 20% OFF", "Revisa la sección de promociones para más detalles.", "Hace 5h")
        }
        
        SectionCard("ANTERIORES") {
            ListItemMock("¡Ganaste 1 punto!", "Gracias por tu última visita. Estás más cerca de tu corte gratis.", "Hace 1 sem")
            ListItemMock("Bienvenido a BladeBarber", "Completa tu perfil para recibir notificaciones personalizadas.", "Hace 1 mes")
        }
    }
}

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

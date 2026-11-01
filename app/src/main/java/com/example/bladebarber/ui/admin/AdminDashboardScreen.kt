package com.example.bladebarber.ui.admin

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.admin.panels.*
import com.example.bladebarber.ui.auth.BladeLogo
import com.example.bladebarber.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentSection by remember { mutableStateOf<AdminSection>(AdminSection.Welcome) }

    val sections = listOf(
        AdminSection.Welcome,
        AdminSection.ProfileView,
        AdminSection.ProfileEdit,
        AdminSection.ChangePass,
        AdminSection.PostsWall,
        AdminSection.PostCreate,
        AdminSection.BarbersList,
        AdminSection.NovedadesAdmin,
        AdminSection.BarberRegister,
        AdminSection.Agenda,
        AdminSection.Schedule,
        AdminSection.Barberia,
        AdminSection.ServiceRegister,
        AdminSection.ServicesList,
        AdminSection.FilaConfig,
        AdminSection.Promociones,
        AdminSection.HistorialBarberos,
        AdminSection.FidelidadConfig,
        AdminSection.Notificaciones,
        AdminSection.Comisiones,
        AdminSection.Reportes,
        AdminSection.Ingresos,
        AdminSection.InventarioEgresos
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerState = drawerState,
                drawerContainerColor = BladeCardBg,
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Sidebar Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BladeLogo(size = 40)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("BladeBarber", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Administrador", color = BladeGold, fontSize = 12.sp)
                        }
                    }

                    HorizontalDivider(color = BladeBorder, modifier = Modifier.padding(bottom = 12.dp))

                    var lastCategory = ""
                    sections.forEach { section ->
                        if (section.category != lastCategory) {
                            lastCategory = section.category
                            Text(
                                text = lastCategory.uppercase(),
                                color = BladeTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                            )
                        }

                        NavigationDrawerItem(
                            icon = { Icon(section.icon, contentDescription = null) },
                            label = { Text(section.title, fontSize = 13.sp) },
                            selected = currentSection == section,
                            onClick = {
                                currentSection = section
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

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = BladeBorder)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Footer User & Logout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(BladeGold, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("A", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Administrador", color = BladeTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text("admin@bladebarber.com", color = BladeTextSecondary, fontSize = 11.sp)
                            }
                        }
                        IconButton(onClick = onLogout) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión", tint = BladeError)
                        }
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
                        Text(
                            text = currentSection.title,
                            color = BladeTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú", tint = BladeGold)
                        }
                    },
                    actions = {
                        IconButton(onClick = { currentSection = AdminSection.Notificaciones }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = BladeTextSecondary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = BladeCardBg)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentSection) {
                    AdminSection.Welcome -> WelcomePanel(onNavigate = { sec -> currentSection = sec })
                    AdminSection.ProfileView -> ProfileViewPanel()
                    AdminSection.ProfileEdit -> ProfileEditPanel()
                    AdminSection.ChangePass -> ChangePasswordPanel()
                    AdminSection.PostsWall -> PostsWallPanel(onNewPost = { currentSection = AdminSection.PostCreate })
                    AdminSection.PostCreate -> PostCreatePanel(onDone = { currentSection = AdminSection.PostsWall })
                    AdminSection.BarbersList -> BarbersListPanel(onAddBarber = { currentSection = AdminSection.BarberRegister })
                    AdminSection.NovedadesAdmin -> NovedadesAdminPanel()
                    AdminSection.BarberRegister -> BarberRegisterPanel(onDone = { currentSection = AdminSection.BarbersList })
                    AdminSection.Agenda -> AgendaPanel()
                    AdminSection.Schedule -> SchedulePanel()
                    AdminSection.Barberia -> BarberiaPanel()
                    AdminSection.ServiceRegister -> ServiceRegisterPanel(onDone = { currentSection = AdminSection.ServicesList })
                    AdminSection.ServicesList -> ServicesListPanel(onAddService = { currentSection = AdminSection.ServiceRegister })
                    AdminSection.FilaConfig -> FilaConfigPanel()
                    AdminSection.Promociones -> PromocionesPanel()
                    AdminSection.HistorialBarberos -> HistorialBarberosPanel()
                    AdminSection.FidelidadConfig -> FidelidadConfigPanel()
                    AdminSection.Notificaciones -> NotificacionesPanel()
                    AdminSection.Comisiones -> ComisionesPanel()
                    AdminSection.Reportes -> ReportesPanel()
                    AdminSection.Ingresos -> IngresosPanel()
                    AdminSection.InventarioEgresos -> InventarioEgresosPanel()
                }
            }
        }
    }
}

@Preview(name = "Admin Dashboard Refactored Preview", showBackground = true, backgroundColor = 0xFF0E0E0E)
@Composable
fun AdminDashboardPreview() {
    BladeBarberTheme {
        AdminDashboardScreen(onLogout = {})
    }
}

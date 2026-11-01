package com.example.bladebarber.ui.admin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AdminSection(val title: String, val category: String, val icon: ImageVector) {
    object Welcome : AdminSection("Bienvenida", "Principal", Icons.Default.Dashboard)
    object ProfileView : AdminSection("Ver Perfil", "Cuenta", Icons.Default.Person)
    object ProfileEdit : AdminSection("Editar Perfil", "Cuenta", Icons.Default.Edit)
    object ChangePass : AdminSection("Cambiar Contraseña", "Cuenta", Icons.Default.Lock)
    object PostsWall : AdminSection("Muro", "Publicaciones", Icons.AutoMirrored.Filled.Article)
    object PostCreate : AdminSection("Nueva Publicación", "Publicaciones", Icons.Default.AddCircle)
    object BarbersList : AdminSection("Lista de Barberos", "Barberos", Icons.Default.Group)
    object NovedadesAdmin : AdminSection("Administrar novedades", "Barberos", Icons.Default.NotificationsActive)
    object BarberRegister : AdminSection("Registrar Barbero", "Barberos", Icons.Default.PersonAdd)
    object Agenda : AdminSection("Ver Agenda", "Agenda", Icons.Default.CalendarMonth)
    object Schedule : AdminSection("Asignar Horario", "Agenda", Icons.Default.Schedule)
    object Barberia : AdminSection("Estado Barbería", "Barbería", Icons.Default.Store)
    object ServiceRegister : AdminSection("Registrar Servicio", "Servicios", Icons.Default.PostAdd)
    object ServicesList : AdminSection("Ver Servicios", "Servicios", Icons.AutoMirrored.Filled.List)
    object FilaConfig : AdminSection("Fila Virtual", "Fila de Espera", Icons.Default.Queue)
    object Promociones : AdminSection("Gestionar Promociones", "Promociones", Icons.Default.LocalOffer)
    object HistorialBarberos : AdminSection("Historial Barberos", "Historial & Fidelidad", Icons.Default.History)
    object FidelidadConfig : AdminSection("Programa de Fidelidad", "Historial & Fidelidad", Icons.Default.CardGiftcard)
    object Notificaciones : AdminSection("Notificaciones", "Historial & Fidelidad", Icons.Default.Notifications)
    object Comisiones : AdminSection("Comisiones", "Finanzas", Icons.Default.Percent)
    object Reportes : AdminSection("Reportes", "Finanzas", Icons.Default.BarChart)
    object Ingresos : AdminSection("Ingresos", "Finanzas", Icons.Default.AttachMoney)
    object InventarioEgresos : AdminSection("Inventario & Egresos", "Finanzas", Icons.Default.Inventory)
}

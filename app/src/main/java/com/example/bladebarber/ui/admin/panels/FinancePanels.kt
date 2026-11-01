package com.example.bladebarber.ui.admin.panels

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*

@Composable
fun PromocionesPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Gestión de Promociones", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Crea y administra descuentos y ofertas vigentes", color = BladeTextSecondary, fontSize = 12.sp)
            }
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), shape = RoundedCornerShape(10.dp)) {
                Text("Nueva Promo", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        val promos = listOf(
            Triple("Combo Verano", "15% de descuento en corte y barba los miércoles.", "Vigente"),
            Triple("Especial Barba", "Afeitado tradicional con 20% off de 2pm a 5pm.", "Vigente"),
            Triple("Bono Cumpleaños", "Corte gratis presentando documento de identidad.", "Inactiva")
        )

        promos.forEach { (title, desc, state) ->
            val isVigente = state == "Vigente"
            Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(desc, color = BladeTextPrimary, fontSize = 13.sp)
                    }
                    Surface(color = (if (isVigente) BladeSuccess else BladeError).copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text(state, color = if (isVigente) BladeSuccess else BladeError, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HistorialBarberosPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Historial de Barberos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Consulta el rendimiento y servicios prestados por profesional", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Santiago Barber", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                ProfileDetailRow("Citas completadas", "45")
                ProfileDetailRow("Total recaudado", "$1.450.000")
                ProfileDetailRow("Calificación promedio", "4.9 / 5.0 ⭐")
            }
        }
    }
}

@Composable
fun FidelidadConfigPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Programa de Fidelidad", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Configura las reglas de puntos y beneficios para clientes frecuentes", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(value = "10", onValueChange = {}, label = { Text("Visitas requeridas para beneficio") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = "1 Corte Gratis", onValueChange = {}, label = { Text("Descripción del beneficio") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp)) {
                    Text("Guardar Configuración", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun NotificacionesPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Notificaciones", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Centro de alertas y avisos del sistema", color = BladeTextSecondary, fontSize = 12.sp)
            }
            TextButton(onClick = {}) {
                Text("Marcar todas leídas", color = BladeGold, fontSize = 12.sp)
            }
        }
        val notifs = listOf(
            Triple("Nueva cita agendada por Carlos Méndez para las 10:00 AM.", Icons.Default.CalendarToday, BladeGold),
            Triple("Santiago Barber solicitó aprobación de inasistencia por cita médica.", Icons.Default.Warning, BladeError),
            Triple("Cliente Juan Pérez alcanzó las 10 visitas en su tarjeta de fidelidad.", Icons.Default.CardGiftcard, BladeSuccess)
        )
        notifs.forEach { (msg, icon, iconColor) ->
            Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(12.dp))) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(msg, color = BladeTextPrimary, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun ComisionesPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Comisiones de Personal", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Cálculo y liquidación de comisiones por barbero", color = BladeTextSecondary, fontSize = 12.sp)
        }

        val commissions = listOf(
            Triple("Santiago Barber", "$1.200.000", "$120.000 (10%)"),
            Triple("Mateo El Pro", "$980.000", "$98.000 (10%)"),
            Triple("Lucas Blade", "$1.100.000", "$110.000 (10%)")
        )

        commissions.forEach { (barber, base, comision) ->
            Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(barber, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Base: $base", color = BladeTextSecondary, fontSize = 13.sp)
                        Text("Comisión: $comision", color = BladeSuccess, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportesPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Reportes Operativos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Resumen analítico de ingresos, citas y servicios", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(Modifier.weight(1f), "Ingresos Periodo", "$2.4M", Icons.Default.AttachMoney, BladeSuccess, "Este mes")
            StatCard(Modifier.weight(1f), "Citas Totales", "84", Icons.AutoMirrored.Filled.EventNote, BladeGold, "Completadas")
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Servicios más demandados", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                ProfileDetailRow("1. Corte Clásico + Barba", "42 servicios")
                ProfileDetailRow("2. Corte Fade", "28 servicios")
                ProfileDetailRow("3. Afeitado Tradicional", "14 servicios")
            }
        }
    }
}

@Composable
fun IngresosPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Gestión de Ingresos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Registro de cobros y transacciones diarias", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Total Ingresos del Mes", color = BladeTextSecondary, fontSize = 13.sp)
                Text("$2.400.000", color = BladeSuccess, fontWeight = FontWeight.Bold, fontSize = 26.sp)
                Text("Ticket Promedio: $28.500 · 84 transacciones", color = BladeTextPrimary, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun InventarioEgresosPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Inventario & Egresos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Control de stock de insumos y gastos operativos", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Insumos en Stock", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                ProfileDetailRow("Gel para Cabello", "12 unidades (Normal)")
                ProfileDetailRow("Cuchillas Afeitado", "8 paquetes (Bajo)")
                ProfileDetailRow("Loción Aftershave", "5 botellas (Normal)")
                Spacer(modifier = Modifier.height(6.dp))
                Text("Total Egresos Operativos del Mes: $450.000", color = BladeError, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

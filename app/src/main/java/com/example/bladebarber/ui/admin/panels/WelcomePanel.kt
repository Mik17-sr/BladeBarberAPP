package com.example.bladebarber.ui.admin.panels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.admin.AdminSection
import com.example.bladebarber.ui.theme.*

@Composable
fun WelcomePanel(onNavigate: (AdminSection) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Panel de Administración", color = BladeGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Bienvenido, Administrador", color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Gestiona tu barbería digital, citas, personal y finanzas en tiempo real.", color = BladeTextSecondary, fontSize = 13.sp)
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(Modifier.weight(1f), "Barberos Activos", "4", Icons.Default.Group, BladeGold, "↑ 1 este mes")
                StatCard(Modifier.weight(1f), "Citas Hoy", "12", Icons.Default.CalendarToday, BladeSuccess, "↑ 3 vs ayer")
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(Modifier.weight(1f), "Clientes Totales", "87", Icons.Default.Person, Color(0xFF60A5FA), "↑ 5 esta semana")
                StatCard(Modifier.weight(1f), "Ingresos Mes", "$2.4M", Icons.Default.AttachMoney, Color(0xFFA78BFA), "Mes actual")
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Accesos Directos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { onNavigate(AdminSection.Agenda) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = BladeGold),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Ver Agenda", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { onNavigate(AdminSection.BarbersList) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = BladeCardBg),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BladeBorder)
                        ) {
                            Text("Barberos", color = BladeTextPrimary, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { onNavigate(AdminSection.Promociones) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = BladeCardBg),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BladeBorder)
                        ) {
                            Text("Promos", color = BladeTextPrimary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, icon: ImageVector, color: Color, subtitle: String) {
    Card(
        modifier = modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = BladeCardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, color = BladeTextSecondary, fontSize = 12.sp)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.sp.value.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, color = BladeTextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, color = BladeSuccess, fontSize = 11.sp)
        }
    }
}

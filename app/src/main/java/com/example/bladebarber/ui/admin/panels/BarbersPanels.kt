package com.example.bladebarber.ui.admin.panels

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun BarbersListPanel(onAddBarber: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Lista de Barberos", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Gestiona el equipo de profesionales", color = BladeTextSecondary, fontSize = 12.sp)
            }
            Button(onClick = onAddBarber, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), shape = RoundedCornerShape(10.dp)) {
                Text("Registrar", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        val barbers = listOf(
            Triple("Santiago Barber", "Especialista Fade & Clásico", "+57 300 111 2233"),
            Triple("Mateo El Pro", "Barba y Diseños Modernos", "+57 310 222 3344"),
            Triple("Lucas Blade", "Estilo Urbano y Color", "+57 320 333 4455"),
            Triple("Pedro Gómez", "Cortes Tradicionales", "+57 315 444 5566")
        )

        barbers.forEach { (name, spec, phone) ->
            Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(spec, color = BladeGold, fontSize = 12.sp)
                        Text("Tel: $phone", color = BladeTextSecondary, fontSize = 11.sp)
                    }
                    Surface(color = BladeSuccess.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text("Activo", color = BladeSuccess, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NovedadesAdminPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Administrar Novedades", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Aprueba o rechaza inasistencias y solicitudes del personal", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Santiago Barber", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Surface(color = BladeGold.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text("Pendiente", color = BladeGold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                    }
                }
                Text("Motivo: Inasistencia por cita médica de urgencia (Día completo).", color = BladeTextPrimary, fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeSuccess), shape = RoundedCornerShape(8.dp)) {
                        Text("Aprobar", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeError), shape = RoundedCornerShape(8.dp)) {
                        Text("Rechazar", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BarberRegisterPanel(onDone: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Registrar Barbero", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Agrega un nuevo integrante al equipo de la barbería", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = usuario, onValueChange = { usuario = it }, label = { Text("Nombre de Usuario") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Button(onClick = onDone, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                    Text("Registrar Barbero", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

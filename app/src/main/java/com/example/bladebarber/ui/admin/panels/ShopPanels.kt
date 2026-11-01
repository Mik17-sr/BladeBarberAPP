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
fun BarberiaPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Estado de la Barbería", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Controla la apertura y cierre general del local", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Estado Actual:", color = BladeTextSecondary, fontSize = 14.sp)
                    Text("ABIERTA", color = BladeSuccess, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Text("Horario general: 08:00 AM — 08:00 PM", color = BladeTextPrimary, fontSize = 13.sp)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeError), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp)) {
                    Text("Cerrar Barbería", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ServiceRegisterPanel(onDone: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Registrar Servicio", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Crea un nuevo servicio disponible para reservas", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre del Servicio") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio ($)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("Duración (minutos)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Button(onClick = onDone, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                    Text("Guardar Servicio", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun ServicesListPanel(onAddService: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Ver Servicios", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Listado de servicios y tarifas actuales", color = BladeTextSecondary, fontSize = 12.sp)
            }
            Button(onClick = onAddService, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), shape = RoundedCornerShape(10.dp)) {
                Text("Registrar", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        val services = listOf(
            Triple("Corte Clásico", "$25.000", "30 min"),
            Triple("Corte + Barba", "$35.000", "45 min"),
            Triple("Afeitado Tradicional", "$20.000", "25 min"),
            Triple("Diseño & Fade", "$30.000", "40 min")
        )

        services.forEach { (name, price, duration) ->
            Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(name, color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Duración: $duration", color = BladeTextSecondary, fontSize = 12.sp)
                    }
                    Text(price, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun FilaConfigPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Fila Virtual de Espera", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Configura el intervalo de atención de la fila virtual", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Estado:", color = BladeTextSecondary)
                    Text("FILA ACTIVA", color = BladeSuccess, fontWeight = FontWeight.Bold)
                }
                Text("Intervalo por turno: 30 minutos", color = BladeTextPrimary, fontSize = 13.sp)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp)) {
                    Text("Actualizar Fila", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

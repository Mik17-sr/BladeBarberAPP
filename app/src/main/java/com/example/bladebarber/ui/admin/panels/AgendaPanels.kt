package com.example.bladebarber.ui.admin.panels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
fun AgendaPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Agenda Semanal", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Semana del 01 Nov al 07 Nov 2026", color = BladeTextSecondary, fontSize = 12.sp)
            }
            Surface(color = BladeGold.copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp)) {
                Text("Filtrar fecha", color = BladeGold, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }
        }

        // Days summary header
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("Lun 1", "Mar 2", "Mié 3", "Jue 4", "Vie 5", "Sáb 6", "Dom 7").forEachIndexed { index, day ->
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = if (index == 0) BladeGold else BladeCardBg),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(day.substringBefore(" "), color = if (index == 0) Color.Black else BladeTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(day.substringAfter(" "), color = if (index == 0) Color.Black else BladeTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text("Citas del Día", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)

        val appointments = listOf(
            Triple("10:00 AM", "Carlos Méndez — Corte Clásico + Barba", "Confirmada"),
            Triple("11:30 AM", "Andrés Gómez — Corte Fade", "Pendiente"),
            Triple("01:00 PM", "Felipe Restrepo — Afeitado Tradicional", "Confirmada"),
            Triple("03:30 PM", "David Ospina — Diseño Moderno", "Cancelada")
        )

        appointments.forEach { (time, detail, status) ->
            val statusColor = when (status) {
                "Confirmada" -> BladeSuccess
                "Pendiente" -> BladeGold
                else -> BladeError
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = BladeCardBg),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(14.dp))
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(time, color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(detail, color = BladeTextPrimary, fontSize = 13.sp)
                    }
                    Surface(color = statusColor.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text(status, color = statusColor, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SchedulePanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Asignar Horario", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Define la disponibilidad horaria semanal por barbero", color = BladeTextSecondary, fontSize = 12.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Seleccionar Barbero", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(value = "Santiago Barber", onValueChange = {}, label = { Text("Barbero") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), readOnly = true)

                listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado").forEach { day ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(day, color = BladeTextPrimary, fontSize = 13.sp, modifier = Modifier.width(90.dp))
                        Text("08:00 AM — 06:00 PM", color = BladeTextSecondary, fontSize = 12.sp)
                        Surface(color = BladeSuccess.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
                            Text("Activo", color = BladeSuccess, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }

                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp)) {
                    Text("Guardar Horario", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

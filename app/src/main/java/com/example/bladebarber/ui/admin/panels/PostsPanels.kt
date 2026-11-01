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
fun PostsWallPanel(onNewPost: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Muro de Publicaciones", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Últimas publicaciones y anuncios de la barbería", color = BladeTextSecondary, fontSize = 12.sp)
            }
            Button(onClick = onNewPost, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), shape = RoundedCornerShape(10.dp)) {
                Text("Nueva", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = BladeCardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Admin BladeBarber", color = BladeGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Hace 2 horas", color = BladeTextSecondary, fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("¡Nuevos estilos y cortes disponibles esta semana en BladeBarber! Reserva tu cita con anticipación para asegurar tu horario.", color = BladeTextPrimary, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("♥ 14 Me gusta", color = BladeGold, fontSize = 12.sp)
                    Text("💬 3 Comentarios", color = BladeTextSecondary, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun PostCreatePanel(onDone: () -> Unit) {
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text("Nueva Publicación", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Comparte contenido, fotos o anuncios con tu comunidad", color = BladeTextSecondary, fontSize = 12.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("¿Qué quieres compartir hoy?") },
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                Button(onClick = onDone, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                    Text("Publicar Ahora", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

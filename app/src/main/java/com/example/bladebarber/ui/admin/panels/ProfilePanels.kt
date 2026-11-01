package com.example.bladebarber.ui.admin.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun ProfileViewPanel() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BladeCardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(80.dp).background(BladeGold, CircleShape), contentAlignment = Alignment.Center) {
                    Text("A", color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text("Administrador Principal", color = BladeTextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("admin@bladebarber.com", color = BladeTextSecondary, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = BladeGold.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text("Administrador", color = BladeGold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                    }
                    Surface(color = BladeSuccess.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                        Text("Activo", color = BladeSuccess, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = BladeCardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, BladeBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Información de Contacto", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                ProfileDetailRow("Teléfono", "+57 300 000 0000")
                ProfileDetailRow("Usuario", "admin")
                ProfileDetailRow("Miembro desde", "Enero 2024")
            }
        }
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = BladeTextSecondary, fontSize = 13.sp)
        Text(value, color = BladeTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ProfileEditPanel() {
    var nombre by remember { mutableStateOf("Administrador Principal") }
    var email by remember { mutableStateOf("admin@bladebarber.com") }
    var telefono by remember { mutableStateOf("+57 300 000 0000") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Editar Perfil", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                    Text("Guardar Cambios", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun ChangePasswordPanel() {
    var currentPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = BladeCardBg), shape = RoundedCornerShape(16.dp), modifier = Modifier.border(1.dp, BladeBorder, RoundedCornerShape(16.dp))) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Cambiar Contraseña", color = BladeTextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = currentPass, onValueChange = { currentPass = it }, label = { Text("Contraseña Actual") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = newPass, onValueChange = { newPass = it }, label = { Text("Nueva Contraseña") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = confirmPass, onValueChange = { confirmPass = it }, label = { Text("Confirmar Contraseña") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = BladeGold), modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)) {
                    Text("Actualizar Contraseña", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

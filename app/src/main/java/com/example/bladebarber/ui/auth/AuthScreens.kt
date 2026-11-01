package com.example.bladebarber.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bladebarber.ui.theme.*

@Composable
fun BladeLogo(modifier: Modifier = Modifier, size: Int = 32) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BladeGold),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageIconsCustomScissors(),
            contentDescription = "Logo",
            tint = Color.Black,
            modifier = Modifier.size((size * 0.6).dp)
        )
    }
}

@Composable
fun imageIconsCustomScissors() = Icons.Default.ContentCut

@Composable
fun AuthBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        BladeDarkBg,
                        Color(0xFF14120F),
                        BladeDarkBg
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-50).dp, y = (-50).dp)
                .background(BladeGold.copy(alpha = 0.05f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .background(BladeGold.copy(alpha = 0.04f), CircleShape)
        )
        content()
    }
}

@Composable
fun LoginScreen(
    onNavigateRegister: () -> Unit,
    onNavigateForgot: () -> Unit,
    onLoginSuccess: (String, String) -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    AuthBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BladeBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BladeCardBg)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BladeLogo(size = 48)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Bienvenido",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BladeTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ingresa tus credenciales para continuar",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BladeTextSecondary
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (loginError != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = BladeError.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = loginError!!,
                                color = BladeError,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BladeGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BladeGold) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle password",
                                    tint = BladeTextSecondary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (usuario.isBlank() || password.isBlank()) {
                                loginError = "Por favor ingresa usuario y contraseña"
                            } else {
                                loginError = null
                                onLoginSuccess(usuario, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BladeGold)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onNavigateForgot) {
                            Text("¿Olvidaste tu contraseña?", color = BladeTextSecondary, fontSize = 13.sp)
                        }
                        Text("·", color = BladeTextSecondary, modifier = Modifier.padding(horizontal = 4.dp))
                        TextButton(onClick = onNavigateRegister) {
                            Text("Crear cuenta", color = BladeGold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onNavigateLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    AuthBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BladeBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BladeCardBg)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BladeLogo(size = 48)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Crear cuenta",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BladeTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Únete a BladeBarber como cliente",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BladeTextSecondary
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    if (errorMsg != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = BladeError.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = errorMsg!!,
                                color = BladeError,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = BladeGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Nombre de usuario") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BladeGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BladeGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BladeGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña (mín 6 car.)") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BladeGold) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = BladeTextSecondary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = confirmar,
                        onValueChange = { confirmar = it },
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BladeGold) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (nombre.isBlank() || usuario.isBlank() || email.isBlank() || password.isBlank()) {
                                errorMsg = "Por favor completa todos los campos obligatorios"
                            } else if (password.length < 6) {
                                errorMsg = "La contraseña debe tener al menos 6 caracteres"
                            } else if (password != confirmar) {
                                errorMsg = "Las contraseñas no coinciden"
                            } else {
                                errorMsg = null
                                onRegisterSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BladeGold)
                    ) {
                        Text(
                            text = "Registrarme",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.PersonAdd, contentDescription = null, tint = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateLogin) {
                        Text("← Volver al inicio de sesión", color = BladeTextSecondary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    onNavigateLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var feedbackMsg by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }

    AuthBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BladeBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BladeCardBg)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BladeLogo(size = 48)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Recuperar acceso",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BladeTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Te enviaremos un enlace a tu correo",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BladeTextSecondary
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    if (feedbackMsg != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = if (isSuccess) BladeSuccess.copy(alpha = 0.15f) else BladeError.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = feedbackMsg!!,
                                color = if (isSuccess) BladeSuccess else BladeError,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo registrado") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BladeGold) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BladeGold,
                            unfocusedBorderColor = BladeBorder,
                            focusedLabelColor = BladeGold,
                            unfocusedLabelColor = BladeTextSecondary,
                            cursorColor = BladeGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.isBlank() || !email.contains("@")) {
                                feedbackMsg = "Por favor ingresa un correo válido"
                                isSuccess = false
                            } else {
                                feedbackMsg = "Te enviaremos un correo con las instrucciones si el usuario existe."
                                isSuccess = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BladeGold)
                    ) {
                        Text(
                            text = "Enviar enlace",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.Black)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateLogin) {
                        Text("← Volver al inicio de sesión", color = BladeTextSecondary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Preview(name = "Login Screen", showBackground = true, backgroundColor = 0xFF0E0E0E)
@Composable
fun LoginScreenPreview() {
    BladeBarberTheme {
        LoginScreen(
            onNavigateRegister = {},
            onNavigateForgot = {},
            onLoginSuccess = { _, _ -> }
        )
    }
}

@Preview(name = "Register Screen", showBackground = true, backgroundColor = 0xFF0E0E0E)
@Composable
fun RegisterScreenPreview() {
    BladeBarberTheme {
        RegisterScreen(
            onNavigateLogin = {},
            onRegisterSuccess = {}
        )
    }
}

@Preview(name = "Forgot Password Screen", showBackground = true, backgroundColor = 0xFF0E0E0E)
@Composable
fun ForgotPasswordScreenPreview() {
    BladeBarberTheme {
        ForgotPasswordScreen(
            onNavigateLogin = {}
        )
    }
}

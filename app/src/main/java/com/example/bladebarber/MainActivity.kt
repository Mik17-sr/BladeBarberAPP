package com.example.bladebarber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bladebarber.ui.admin.AdminDashboardScreen
import com.example.bladebarber.ui.auth.ForgotPasswordScreen
import com.example.bladebarber.ui.auth.LoginScreen
import com.example.bladebarber.ui.auth.RegisterScreen
import com.example.bladebarber.ui.barber.BarberDashboardScreen
import com.example.bladebarber.ui.client.ClientDashboardScreen
import com.example.bladebarber.ui.theme.BladeBarberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BladeBarberTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onNavigateRegister = { navController.navigate("register") },
                                onNavigateForgot = { navController.navigate("forgot") },
                                onLoginSuccess = { user, _, profile ->
                                    val destination = when (profile) {
                                        "Administrador" -> "admin_dashboard"
                                        "Barbero" -> "barber_dashboard"
                                        else -> "client_dashboard"
                                    }
                                    navController.navigate("$destination/${android.net.Uri.encode(user.trim())}") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onNavigateLogin = { navController.popBackStack() },
                                onRegisterSuccess = { navController.popBackStack() }
                            )
                        }
                        composable("forgot") {
                            ForgotPasswordScreen(
                                onNavigateLogin = { navController.popBackStack() }
                            )
                        }
                        composable("admin_dashboard/{name}") { backStackEntry ->
                            AdminDashboardScreen(
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("admin_dashboard/{name}") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("barber_dashboard/{name}") { backStackEntry ->
                            BarberDashboardScreen(
                                barberName = backStackEntry.arguments?.getString("name")?.ifBlank { "Barbero" } ?: "Barbero",
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("barber_dashboard/{name}") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("client_dashboard/{name}") { backStackEntry ->
                            ClientDashboardScreen(
                                clientName = backStackEntry.arguments?.getString("name")?.ifBlank { "Usuario" } ?: "Usuario",
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo("client_dashboard/{name}") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

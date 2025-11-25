package com.mario.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview // Para ver la vista previa sin ejecutar
import androidx.compose.ui.unit.dp
import com.mario.tipcalculator.ui.theme.TipCalculatorTheme // tema personalizado
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // MANTUVIMOS ESTO: El tema asegura que tu app se vea bien (modo oscuro/claro)
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí llamamos a nuestra pantalla
                    TipCalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorLayout() {
    // ESTADOS
    var amountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15f) }
    // 1. Nuevo estado Booleano (Verdadero/Falso)
    var roundUp by remember { mutableStateOf(false) }

    // CÁLCULOS
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tip = amount * (tipPercent / 100)

    // 2. Lógica condicional: Si el switch está activado, redondeamos la propina
    if (roundUp) {
        tip = ceil(tip)
    }

    val total = amount + tip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calculadora de Propinas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = amountInput,
            onValueChange = { input -> amountInput = input },
            label = { Text("Monto de la cuenta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // SECCIÓN DE CONTROL (Slider y Switch)
        Text(text = "Porcentaje: ${tipPercent.toInt()}%")

        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..30f,
            steps = 29,
            modifier = Modifier.fillMaxWidth()
        )

        // 3. ROW: Elementos lado a lado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            // Esto empuja el texto a la izquierda y el switch a la derecha
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Redondear propina")

            Switch(
                checked = roundUp, // El switch se enciende si roundUp es true
                onCheckedChange = { isChecked -> roundUp = isChecked }
            )
        }

        // RESULTADOS
        Text(
            text = "Propina: ${NumberFormat.getCurrencyInstance().format(tip)}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Total: ${NumberFormat.getCurrencyInstance().format(total)}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

// --- PREVIEW (Opcional, pero útil) ---
// Esto te permite ver cómo queda el diseño en Android Studio sin ejecutar la app en la tablet
//@Preview(showBackground = true)
//@Composable
//fun CalculatorPreview() {
//    TipCalculatorTheme {
//        TipCalculatorLayout()
//    }
//}
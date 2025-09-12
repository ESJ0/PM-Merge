package com.esjoprueba.lab8.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esjoprueba.lab8.R

@Composable
fun LoginScreen(
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo de Rick & Morty - IMAGEN PNG
        Image(
            painter = painterResource(id = R.drawable.ic_rick_and_morty),
            contentDescription = "Rick and Morty Logo",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(120.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "CHARACTER UNIVERSE",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón mejorado
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF42A5F5),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "EXPLORAR PERSONAJES",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer mejorado
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Jose Abril - 24585",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Rick and Morty App v1.0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center
            )
        }
    }
}
package com.example.examen2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estudio.data.Asignatura
import com.example.estudio.data.DataSource
import com.example.estudio.data.DataSource.loterias
import com.example.examen2.ui.theme.Examen2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Pantallas()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pantallas(
    modifier: Modifier = Modifier,
    asignaturasTotales: ArrayList<Asignatura> = DataSource.loterias
) {

    var textoUltimaAccion by remember { mutableStateOf("No has hecho ninguna acción") }
    var textoResumen by remember { mutableStateOf("No hay nada que mostrar(defecto)") }


    var horas by remember { mutableStateOf("") }

    var horasTotales by remember { mutableStateOf(0) }

    var textoEditor by remember { mutableStateOf("") }


    //FALTAN VARIABLES POR DECLARAR


    Column(
        modifier = Modifier
    ) {

        Text(
            text = "Bienvenido a la academia de Sergio /SMG",
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .size(30.dp)
                .padding(start = 10.dp, top = 10.dp)
        )


        Row(modifier = Modifier) {

            Column(
                modifier = modifier
                    .height(300.dp)

            ) {

                LazyVerticalGrid(
                    GridCells.Fixed(2)
                ) {

                    items(loterias) { asignatura ->

                        Card(
                            modifier = Modifier
                                .padding(10.dp)

                        ) {
                            Text(
                                text = "Asig: ${asignatura.nombre}",
                                modifier = Modifier
                                    .background(Color.Yellow)
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )

                            Text(
                                text = "€/hora: ${asignatura.precioHora}",
                                modifier = Modifier
                                    .background(Color.Cyan)
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )


                            //HAY QUE AÑADIRLE LA FUNCIONALIDAD AL BUTTON

                            Row(modifier = Modifier.padding(start = 25.dp)) {

                                Button(onClick = {


                                    //Controlamos los errores en el caso de que no pongamos nada
                                    try {
                                        horasTotales += horas.toInt()
                                    } catch (e: Exception) {
                                        textoUltimaAccion = "No puede estar vacio"

                                        return@Button;
                                    }



                                    textoUltimaAccion =
                                        "Se han añadido $horas de la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora}"


                            

                                    for (asignatura in asignaturasTotales) {

                                        if (horas.toInt() > 0) {

                                            textoResumen =
                                                "Asig: ${asignatura.nombre} precio/hora: ${asignatura.precioHora} total horas: $horas \n"
                                        }

                                    }


                                   // loterias.add(asignatura)
                                }) {
                                    Text(text = "+")
                                }



                                Button(onClick = {


                                    try {
                                        horasTotales += horas.toInt()
                                    } catch (e: Exception) {
                                        textoUltimaAccion = "No puede estar vacio"

                                        return@Button;
                                    }


                                    val horasARestar =
                                        if (textoEditor.isNotBlank()) {
                                            textoEditor.toInt()
                                        } else {
                                            0
                                        }

                                    var horasRestantes = horas.toInt() - horasARestar

                                    if (horasRestantes < 0) {
                                        horasRestantes = 0
                                    }

                                    var horasRestadas = horas.toInt() - horasRestantes
                                    horas = horasRestantes.toString()
                                    textoUltimaAccion =
                                        "Se han restado $horasRestadas horas de ${asignatura.nombre} con precio ${asignatura.precioHora}"
                                    textoResumen =
                                        "Asig: ${asignatura.nombre} precio/hora: ${asignatura.precioHora} total horas : ${horas}"


                                    //va quitando las aignaturas
                                    loterias.remove(asignatura)


                                }) {

                                    Text(text = "-")
                                }
                            }

                        }

                    }
                }
            }


        }


        TextField(
            value = horas,
            onValueChange = { horas = it },
            label = { Text(text = "Horas a contratar o a eliminar: ") },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )



        Column(
            modifier = Modifier
                .height(200.dp)
                .background(Color.LightGray)
                .fillMaxWidth()
        ) {


            Column(
                modifier = Modifier

                    .padding(10.dp)
            ) {

                Text(

                    text = "Ultima Acción: \n$textoUltimaAccion",
                    modifier = Modifier
                        .background(Color.Magenta)
                        .fillMaxWidth()
                )

                Text(

                    text = "Resumen: \n$textoResumen",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )

            }


        }
    }
}


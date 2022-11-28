package com.belajar.calculatortipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.belajar.calculatortipapp.ui.theme.CalculatorTipAppTheme
import com.belajar.calculatortipapp.ui.theme.RoundedButton
import com.belajar.calculatortipapp.ui.theme.TextInput
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTipAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipAppCalculate()
                }
            }
        }
    }
}

@Composable
fun TipAppCalculate() {

    val nominal = remember {
        mutableStateOf(0)
    }

    val sumPerson = remember {
        mutableStateOf(1)
    }

    val sliderValue = remember {
        mutableStateOf(0F)
    }


    if (sliderValue.value < 0.1F) sliderValue.value = 0.1F
    if (sumPerson.value < 1)  sumPerson.value = 1

    val percentageTip = ((nominal.value * sliderValue.value).toDouble() * 10.0).roundToInt() / 10.0
    val totalPerPerson = ((percentageTip / sumPerson.value) * 10.0).roundToInt() / 10.0
    val percentage = (sliderValue.value * 100).toInt()



    Scaffold {
        Column(modifier = Modifier
            .padding(it)
            .padding(16.dp)) {
             Header(nominal = totalPerPerson)
            Spacer(modifier = Modifier.height(26.dp))
            BodyTip(sumPerson = sumPerson,
                sliderValue = sliderValue,
                percentage = percentage,
                percentageTip = percentageTip) { ammount ->
                nominal.value = ammount.toInt()

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BodyTip(
    modifier:Modifier = Modifier,
    sumPerson:MutableState<Int>,
    sliderValue:MutableState<Float>,
    percentage:Int,
    percentageTip:Double,
    billAmmount:(String) -> Unit
) {

    val valueState = remember {
        mutableStateOf("")
    }

    val validateState = remember(valueState.value) {
        valueState.value.trim().isNotEmpty()
    }


    if (validateState)  billAmmount.invoke(valueState.value.trim())
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 12.dp,
        border = BorderStroke(4.dp,Color.LightGray),
        shape = RoundedCornerShape(12.dp)
    ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = modifier
                    .padding(12.dp)
            ) {
                TextInput(valueState = valueState ,
                    label = "Enter Bill" ,
                    singleLine = true,
                    keyboardActions = KeyboardActions {
                        if (!validateState) return@KeyboardActions

                        keyboardController?.hide()
                    })
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(visible = validateState) {
                   Column {
                       TotalPerson(modifier, sumPerson)
                       Spacer(modifier = Modifier.height(16.dp))
                       AmmounTip(modifier,percentageTip)
                       SliderTipPercentage(percentage, sliderValue)
                   }
                }
            }
    }


}

@Composable
private fun SliderTipPercentage(
    percentage: Int,
    sliderValue: MutableState<Float>
) {
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = "$percentage %")
        Spacer(modifier = Modifier.height(12.dp))
        Slider(value = sliderValue.value,
            onValueChange = {
                sliderValue.value = it
            },
            steps = 5)
    }
}

@Composable
private fun AmmounTip(
    modifier: Modifier,
    percentageTip:Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Tip")
        Spacer(modifier = modifier.weight(1F))
        Text(text = "$ $percentageTip")
    }
}

@Composable
private fun TotalPerson(
    modifier: Modifier,
    sumPerson: MutableState<Int>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Split")
        Spacer(modifier = modifier.weight(1F))

        RoundedButton(icon = Icons.Default.Remove) {
            sumPerson.value = sumPerson.value - 1
        }
        Spacer(modifier = modifier.width(12.dp))
        Text(text = "${sumPerson.value}")
        Spacer(modifier = modifier.width(12.dp))
        RoundedButton(icon = Icons.Default.Add) {
            sumPerson.value = sumPerson.value + 1
        }
    }
}


@Composable
fun Header(
    nominal:Double
) {
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
       Column (
           modifier = Modifier
               .padding(30.dp),
           horizontalAlignment = CenterHorizontally
               ) {
           Text(text = "Total Per Person",
               fontWeight = FontWeight.Bold,
               modifier = Modifier
                   .padding(top = 16.dp, bottom = 16.dp),
               textAlign = TextAlign.Center)

           Text(text = "$${nominal}",
               textAlign = TextAlign.Center)
       }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTipAppTheme {
        TipAppCalculate()
    }
}
package com.belajar.calculatortipapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    valueState:MutableState<String>,
    label:String,
    singleLine:Boolean,
    keyboardType:KeyboardType = KeyboardType.Number,
    imeAction:ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
        OutlinedTextField(value = valueState.value,
            onValueChange = { valueState.value = it},
            label = { Text(text = label)},
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
                imeAction = imeAction),
            keyboardActions = keyboardActions,
            modifier = modifier
                .padding(10.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "Money" )
            }
            )
}


@Composable
fun RoundedButton(
    modifier:Modifier = Modifier,
    icon:ImageVector,
    onClick:()->Unit
) {
    IconButton(onClick = { onClick.invoke() }) {
        Card(
            elevation = 5.dp,
            border = BorderStroke(2.dp, Color.LightGray),
            shape = CircleShape
        ) {
            Icon(imageVector = icon,
                contentDescription = null,
                modifier = modifier.padding(18.dp))
        }
    }
}
package com.drox7.myapplication.about_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drox7.myapplication.R
import com.drox7.myapplication.ui.theme.BlueLight

@Preview(showBackground = true)
@Composable
fun AboutListScreen() {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Icon(
            painter = painterResource(id = R.drawable.about_icon),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            tint = BlueLight
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "This app developed by Drox\n" +
                    "Version - 1.0.1\n" +
                    "To get information more information\n",
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    uriHandler.openUri("https://google.com")
                },
            text = ">>>Click here<<<",
            color = BlueLight
        )
    }
}
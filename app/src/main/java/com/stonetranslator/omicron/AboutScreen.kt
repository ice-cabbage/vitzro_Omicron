package com.stonetranslator.omicron

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stonetranslator.omicron.ui.theme.OmicronTheme

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .width(0.dp)
            .height(0.dp)
            .weight(1.0f, fill = true),
        ) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Launcher background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Launcher background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(0.dp)
            .weight(1.0f, fill = true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.app_name), fontSize = 25.sp)
            Text(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    LocalContext.current.packageManager?.getPackageInfo(
                        LocalContext.current.packageName ?: "",
                        PackageManager.PackageInfoFlags.of(0)
                    )?.versionName ?: ""
                } else {
                    LocalContext.current.packageManager?.getPackageInfo(
                        LocalContext.current.packageName ?: "",
                        0
                    )?.versionName ?: ""
                }
            )
        }
    }
}

@Preview
@Composable
fun AboutScreenPreivew() {
    OmicronTheme {
        AboutScreen()
    }
}
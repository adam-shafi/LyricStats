package com.virtuix.lyricstats

import com.virtuix.lyricstats.navigation.LyricStatsNavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.virtuix.lyricstats.ui.theme.LyricStatsTheme


class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			LyricStatsTheme {
				LyricStatsNavHost()
			}
		}
	}
}


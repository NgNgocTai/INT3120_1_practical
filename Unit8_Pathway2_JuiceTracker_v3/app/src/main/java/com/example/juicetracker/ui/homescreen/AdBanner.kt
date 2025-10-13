package com.example.juicetracker.ui.homescreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        // `factory`: Tạo AdView một lần
        factory = { context ->
            AdView(context).apply {
                // Thiết lập kích thước và ID (dùng ID thử nghiệm)
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
            }
        },
        // `update`: Tải quảng cáo
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}
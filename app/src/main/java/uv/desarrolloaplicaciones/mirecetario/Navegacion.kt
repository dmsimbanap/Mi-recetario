package uv.desarrolloaplicaciones.mirecetario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import java.lang.Exception

class Navegacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegacion)

        val salir = findViewById<Button>(R.id.btnSalirNavegador)
        salir.setOnClickListener{
            finish()
        }

        val bundle = intent.extras
        val url = bundle?.getString("direccion")

        val webview = findViewById<WebView>(R.id.WebView)

        try {
            webview.loadUrl("https://${url}")
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }
}
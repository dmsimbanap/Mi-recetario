package uv.desarrolloaplicaciones.mirecetario

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar

import uv.desarrolloaplicaciones.mirecetario.databinding.ActivityMain3Binding
import uv.desarrolloaplicaciones.mirecetario.databinding.ActivityMainBinding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initClass()
    }

    private fun initClass() {
        val txt = binding.txtUser
        binding.buttonLogin.setOnClickListener {
            val txtUser = binding.txtUser.text.toString()
            val txtPass = binding.txtPass.text.toString()

            if (txtUser == ("admin") && txtPass == "admin") {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Variables.nombreUsuario, "Bienvenido")
                startActivity(intent)

            }
            if (txtUser == ("Heema Khan") && txtPass == "633943") {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Variables.estadoUsuario, "Bienvenido")
            startActivity(intent)

            }
            if (txtUser == ("Krishnadas Guneta") && txtPass == "633942") {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Variables.estadoUsuario, "Bienvenido")
                startActivity(intent)

            }
            if (txtUser == ("Geetanjali Bhattacharya") && txtPass == "633940") {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Variables.estadoUsuario, "Bienvenido")
            startActivity(intent)

           }
            if (txtUser == ("Deeksha Ahuja") && txtPass == "633462") {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Variables.estadoUsuario, "Bienvenido")
                startActivity(intent)

            }
            if (txtUser == ("Dhanadeepa Deshpande") && txtPass == "633461") {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Variables.estadoUsuario, "Bienvenido")
                startActivity(intent)

            }
            else {
                Snackbar.make(
                    txt, "Usuario no Activo",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }


    object Variables {
        const val nombreUsuario = "Saludo"
        const val estadoUsuario = "Usuario Activo"

    }
}
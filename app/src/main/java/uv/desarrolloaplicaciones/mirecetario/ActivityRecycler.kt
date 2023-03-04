package uv.desarrolloaplicaciones.mirecetario

import AdminSQLite
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_recycler.*
import uv.desarrolloaplicaciones.mirecetario.databinding.ActivityRecyclerBinding

class ActivityRecycler : AppCompatActivity() {

//    private val adaptador : AdapterReceta = AdapterReceta()

    private lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)


        val bundle = intent.extras
        val categoriaReceta = bundle?.getString("titulo")
        val rvRecetas = findViewById<RecyclerView>(R.id.rvDatos)
        rvRecetas.setOnClickListener {
            val intento1 = Intent(this, DetallesReceta::class.java)
            startActivity(intento1)
        }



        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val admin = AdminSQLite (this, "administracion", null, 1)
        val db = admin.readableDatabase
        val cursor : Cursor = db.rawQuery("SELECT nombre, imagen FROM recetas WHERE categoria = '${categoriaReceta}'", null)

        val adaptador = RecyclerViewAdapterRecetas()
        adaptador.RecyclerViewAdapterRecetas(this, cursor)

        binding.rvDatos.setHasFixedSize(true)
        binding.rvDatos.layoutManager = LinearLayoutManager(this)
        binding.rvDatos.adapter = adaptador

        rvRecetas.setOnClickListener {
            val intento1 = Intent(this, DetallesReceta::class.java)
            startActivity(intento1)
        }



    }
}
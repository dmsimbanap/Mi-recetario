package uv.desarrolloaplicaciones.mirecetario

import AdminSQLite
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_nueva_receta.*

class NuevaReceta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_receta)

        val txtIngredientes =findViewById<EditText>(R.id.txtIngredientes)
        val txtInstrucciones = findViewById<EditText>(R.id.txtInstrucciones)
        val txtNombreReceta = findViewById<EditText>(R.id.txtNombreReceta)
        val spCategoria = findViewById<Spinner>(R.id.spCategorias)
        val txtURLImagen = findViewById<EditText>(R.id.txtURLimagen)
        val txtPorciones = findViewById<EditText>(R.id.txtPorciones)
        val r1 = findViewById<RadioButton>(R.id.rdOpcionVisible)
        val r2 = findViewById<RadioButton>(R.id.rdOpcionInvisible)
        val btnImagenVisible = findViewById<Button>(R.id.btnAceptarRadio)
        val btnIngredienteNuevo = findViewById<Button>(R.id.btnIngredienteNuevo)
        val btnRegistrarReceta = findViewById<Button>(R.id.btnRegistrarReceta)
        val lista = arrayOf("Ensaladas", "Tallarines", "Platos Fuertes", "Postres y Dulces")

        val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista)

        spCategoria.adapter = adaptador

        btnIngredienteNuevo.setOnClickListener{
            if(txtIngredientes.text.isNotEmpty()){
                txtMostrarIngredientes.append(txtIngredientes.text.toString() + ", ")
                txtIngredientes.setText("")
            }
        }

        btnImagenVisible.setOnClickListener{
            if(r1.isChecked){
                txtImagenReceta.visibility = View.VISIBLE
                txtURLImagen.visibility = View.VISIBLE
                Toast.makeText(this, "Imagen agregada con éxito ", Toast.LENGTH_LONG).show()
            }
            if(r2.isChecked){
                txtURLImagen.visibility = View.GONE
                txtImagenReceta.text = null
                txtURLImagen.text = null
                Toast.makeText(this, "Se ocultó agregar imagen", Toast.LENGTH_SHORT).show()
            }
        }


        btnRegistrarReceta.setOnClickListener{
            try {
                if(txtMostrarIngredientes.text.isNotEmpty() && txtInstrucciones.text.isNotEmpty()
                     && txtPorciones.text.isNotEmpty() && txtNombreReceta.text.isNotEmpty()){
                    val admin = AdminSQLite (this, "administracion", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombre", txtNombreReceta.getText().toString().lowercase())
                    registro.put("porciones", txtPorciones.text.toString().toInt())
                    registro.put("ingredientes", txtMostrarIngredientes.getText().toString().lowercase())
                    registro.put("instrucciones", txtInstrucciones.getText().toString().lowercase())
                    registro.put("categoria", spCategoria.selectedItem.toString().lowercase())
                    println(spCategoria.selectedItem.toString().lowercase())
                    registro.put("imagen", txtURLImagen.getText().toString().lowercase())


                    bd.insert("recetas", null, registro)
                    bd.close()

                    txtNombreReceta.setText("")
                    txtIngredientes.setText("")
                    txtInstrucciones.setText("")
                    txtURLImagen.setText("")
                    spCategoria.setSelection(0)

                    Toast.makeText(this, "Receta registrada con éxito ", Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(this, "Debes ingresar todos los campos para registrar una receta", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        btnSalirRegistro.setOnClickListener{
            finish()
        }
    }
}
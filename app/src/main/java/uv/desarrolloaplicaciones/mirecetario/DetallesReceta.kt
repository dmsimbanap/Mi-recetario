package uv.desarrolloaplicaciones.mirecetario

import AdminSQLite
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detalles_receta.*
import kotlinx.android.synthetic.main.activity_nueva_receta.*
import java.lang.Exception
import java.lang.NumberFormatException

class DetallesReceta: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_receta)

        val etReceta = findViewById < EditText > (R.id.etReceta)
        val etIngredientes = findViewById < EditText > (R.id.etIngredientes)
        val etInstrucciones = findViewById < EditText > (R.id.etPreparacion)
        val imgReceta = findViewById < ImageView > (R.id.imgReceta)
        val txtPorciones = findViewById < EditText > (R.id.txtPorciones)
        val btnCalcularPorciones = findViewById < Button > (R.id.btnCalcularPorciones)
        val btnModificarReceta = findViewById<Button>(R.id.btnModificarReceta)
        val btnEliminarReceta = findViewById<Button>(R.id.btnEliminarReceta)
        val txtCalcularPorciones = findViewById < EditText > (R.id.txtCalcularPorciones)
        val txtURLImagen = findViewById<EditText>(R.id.txtURLimagen)
        val bundle = intent.extras
        val tituloReceta = bundle?.getString("receta")
        var codigo = 0
        val lstSitios = findViewById< ListView >(R.id.lstSitios)

        val sitiosInternet = arrayOf("Youtube", "Google", "Cookpad", "No Solo Dúlces", "Cocinatis")
        val adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sitiosInternet)

        lstSitios.adapter = adaptador1
        lstSitios.setOnItemClickListener { adapterView, view, i, l ->

            when (sitiosInternet[i]){
                "Youtube" -> { Toast.makeText(this, "${sitiosInternet[i]}", Toast.LENGTH_SHORT).show()
                                val intento1 = Intent(this, Navegacion::class.java)
                                val busquedaYT = "www.youtube.com/results?search_query="+"${etReceta.text.toString().replace(" ", "+")}"
                                intento1.putExtra("direccion", busquedaYT) //pasar parámetros a otro activity
                                startActivity(intento1)}
                "Google" -> {
                    Toast.makeText(this, "${sitiosInternet[i]}", Toast.LENGTH_SHORT).show()
                    val intento1 = Intent(this, Navegacion::class.java)
                    val busquedaGoogle = "www.google.com/search?q="+"${etReceta.text.toString().replace(" ", "+")}"
                    intento1.putExtra("direccion", busquedaGoogle) //pasar parámetros a otro activity
                    startActivity(intento1)
                }

                "Cookpad" -> {
                    Toast.makeText(this, "${sitiosInternet[i]}", Toast.LENGTH_SHORT).show()
                    val intento1 = Intent(this, Navegacion::class.java)
                    val busquedaCookpad = "cookpad.com/es/buscar/"+"\"${
                        etReceta.text.toString()
                    }\""+"?event=search.typed_query"
                    intento1.putExtra("direccion", busquedaCookpad) //pasar parámetros a otro activity
                    startActivity(intento1)
                }

                "No Solo Dúlces" -> {
                    Toast.makeText(this, "${sitiosInternet[i]}", Toast.LENGTH_SHORT).show()
                    val intento1 = Intent(this, Navegacion::class.java)
                    val busquedaNoDulces = "nosolodulces.es/?s="+"${etReceta.text.toString().replace(" ", "+")}"
                    intento1.putExtra("direccion", busquedaNoDulces) //pasar parámetros a otro activity
                    startActivity(intento1)
                }

                "Cocinatis" -> {
                    Toast.makeText(this, "${sitiosInternet[i]}", Toast.LENGTH_SHORT).show()
                    val intento1 = Intent(this, Navegacion::class.java)
                    val busquedaCocinatis = "www.cocinatis.com/que-cocino-hoy/busqueda?q="+"${etReceta.text.toString().replace(" ", "+")}"
                    intento1.putExtra("direccion", busquedaCocinatis) //pasar parámetros a otro activity
                    startActivity(intento1)
                }

            }


        }

        etReceta.setText(tituloReceta.toString().replaceFirstChar {
            it.uppercase()
        })

        val admin = AdminSQLite(this, "administracion", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT porciones, ingredientes, instrucciones, sitios, imagen, codigo FROM recetas WHERE nombre = '${etReceta.text.toString().lowercase()}'", null)

        if (fila.moveToFirst()) {
            txtPorciones.setText(fila.getString(0).toString())
            etIngredientes.setText(fila.getString(1).replaceFirstChar {
                it.uppercase()
            })
            etInstrucciones.setText(fila.getString(2).replaceFirstChar {
                it.uppercase()
            })
            try {
                Glide.with(this)
                    .load(fila.getString(4))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgReceta)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            txtURLImagen.setText(fila.getString(4).replaceFirstChar {
                it.uppercase()
            })

            codigo = fila.getString(5).toInt()

            btnCalcularPorciones.setOnClickListener {
                try {
                    if (txtPorciones.text.toString().toFloat() > txtCalcularPorciones.text.toString().toFloat()) {
                        val cantidadIngredientes = etIngredientes.text.toString()
                        val arrayIngredientes = cantidadIngredientes.split(" ").toMutableList()
                        for(element in arrayIngredientes){
                            val indice = arrayIngredientes.indexOf(element)
                            if ( element.toFloatOrNull() != null){
                                arrayIngredientes[indice] = arrayIngredientes[indice].replace(element, (element.toFloat()/(txtPorciones.text.toString().toFloat() / txtCalcularPorciones.text.toString().toFloat() )).toString())
                            }
                        }
                        etIngredientes.setText(arrayIngredientes.joinToString().replace(",", ""))
                        txtPorciones.setText(txtCalcularPorciones.text.toString())
                    }

                    if (txtPorciones.text.toString().toFloat() < txtCalcularPorciones.text.toString().toFloat()) {
                        val cantidadIngredientes = etIngredientes.text.toString()
                        val arrayIngredientes = cantidadIngredientes.split(" ").toMutableList()
                        for(element in arrayIngredientes){
                            val indice = arrayIngredientes.indexOf(element)
                            if ( element.toFloatOrNull() != null){
                                    arrayIngredientes[indice] = arrayIngredientes[indice].replace(element, (element.toFloat()*(txtCalcularPorciones.text.toString().toFloat() / txtPorciones.text.toString().toFloat() )).toString())
                            }
                        }
                        etIngredientes.setText(arrayIngredientes.joinToString().replace(",", ""))
                        txtPorciones.setText(txtCalcularPorciones.text.toString())
                    }
                }catch (numException : NumberFormatException){
                    numException.printStackTrace()
                }

            }

            btnModificarReceta.setOnClickListener {
                try{
                    if(etIngredientes.text.isNotEmpty() && etReceta.text.isNotEmpty()
                        && txtPorciones.text.isNotEmpty() && etInstrucciones.text.isNotEmpty()){
                        val admin = AdminSQLite(this, "administracion", null, 1)
                        val bd = admin.writableDatabase
                        val registro = ContentValues()
                        registro.put("nombre", etReceta.getText().toString().lowercase())
                        registro.put("porciones", txtPorciones.text.toString().toInt())
                        registro.put("ingredientes", etIngredientes.getText().toString().lowercase())
                        registro.put("instrucciones", etInstrucciones.getText().toString().lowercase())
                        if(txtURLImagen.getText().isNotEmpty()){
                            registro.put("imagen", txtURLImagen.getText().toString().lowercase())
                        }

                        val cantidad = bd.update("recetas", registro, "codigo = '${codigo}'", null)
                        bd.close()

                        if(cantidad == 1)
                            Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "No se encontró la receta", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Debes llenar todos los campos para actualizar una receta", Toast.LENGTH_SHORT).show()
                    }
                }catch (ex : Exception){
                    ex.printStackTrace()
                }
            }

            btnEliminarReceta.setOnClickListener {

                val mensajeConfirmacionEliminar = AlertDialog.Builder(this)

                mensajeConfirmacionEliminar.setTitle("Eliminación de receta")
                mensajeConfirmacionEliminar.setMessage("¿Está seguro de eliminar la receta ${etReceta.text.toString()}?")

                mensajeConfirmacionEliminar.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT).show()
                    try{
                        if(etReceta.text.isNotEmpty() && etIngredientes.text.isNotEmpty() && etInstrucciones.text.isNotEmpty() &&
                            txtPorciones.text.toString().isNotEmpty()){
                            val admin = AdminSQLite (this, "administracion", null, 1)
                            val bd = admin.writableDatabase
                            val cantidad = bd.delete("recetas", "codigo = '${codigo}'", null )

                            bd.close()

                            txtCalcularPorciones.setText("")
                            txtPorciones.setText("")
                            txtURLImagen.setText("")
                            etIngredientes.setText("")
                            etInstrucciones.setText("")
                            etReceta.setText("")

                            if(cantidad == 1){
                                Toast.makeText(this, "Eliminacion exitosa del producto", Toast.LENGTH_SHORT).show()
                                val intento1 = Intent(this, MainActivity::class.java)
                                startActivity(intento1)
                            }
                            else
                                Toast.makeText(this, "No se encontró el elemento", Toast.LENGTH_SHORT).show()
                        }
                    }catch (ex : Exception){
                        ex.printStackTrace()
                    }
                }

                mensajeConfirmacionEliminar.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
                }

                mensajeConfirmacionEliminar.show()

            }
            btnSalirDetalles.setOnClickListener{
                    finish()
            }
            }
        }
    }


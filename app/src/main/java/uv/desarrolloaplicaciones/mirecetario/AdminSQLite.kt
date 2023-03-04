import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory

class AdminSQLite (context: Context, name: String, factory: CursorFactory?, version: Int): SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table recetas (codigo integer primary key autoincrement, nombre text, porciones integer, ingredientes text, instrucciones text, categoria text, sitios text, imagen text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldversion:Int, newversion: Int) {

    }
}
package com.alexandru.vladut.gabriel97.basketeam

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

private lateinit var usernameEditText: EditText
private lateinit var passwordEditText: EditText
private lateinit var checkBoxLog: CheckBox

private lateinit var lastLoginDateKey: String
private lateinit var rememberCheckboxKey: String
private var firstTimeOpened by Delegates.notNull<Boolean>()

private lateinit var sharedPreferences: SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editPassword)
        checkBoxLog = findViewById(R.id.checkBoxRemember)
        lastLoginDateKey = "last_login_date"
        rememberCheckboxKey = "remember_checkbox"

        // Inicializar sharedPreferences
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)


        firstTimeOpened = isFirstTimeOpen()

        // Restaurar el estado del CheckBox desde las preferencias compartidas
        checkBoxLog.isChecked = sharedPreferences.getBoolean(rememberCheckboxKey, false)



        // Verificar si se recuerdan las credenciales y han pasado más de 2 días
        if (checkBoxLog.isChecked && isLastLoginExpired()) {
            mostrarVentanaDialogo()
        }else if(firstTimeOpened != true){
            if(checkBoxLog.isChecked.equals(false)){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            }else{
            val intent = Intent(this, Opciones::class.java)
            finish()
            startActivity(intent)
            }
        }else{
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
        }


    }

    fun onLogin(view: View) {
        val comprobar = usernameEditText.text.toString() + "1"
        // Crear un Intent para ir de MainActivity a Opciones
        val intent = Intent(this, Opciones::class.java)

        if (passwordEditText.text.toString().equals(comprobar)) {
            // Iniciar la nueva Activity
            markAppOpened()
            if(checkBoxLog.isChecked){
                saveRememberCheckboxState(checked = true)
            }else{
                saveRememberCheckboxState(checked = false)
            }
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = sdf.format(Date())
            saveLastLoginDate(currentDate)
            finish()
            startActivity(intent)

        }else{
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
        }


    }

    private fun saveLastLoginDate(date: String) {
        with(sharedPreferences.edit()) {
            putString(lastLoginDateKey, date)
            apply()
        }
    }

    private fun saveRememberCheckboxState(checked: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(rememberCheckboxKey, checked)
            apply()
        }
    }

    private fun daysBetween(startDate: String, endDate: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date1 = sdf.parse(startDate)
        val date2 = sdf.parse(endDate)

        return ((date2.time - date1.time) / (1000 * 60 * 60 * 24))
    }
    private fun isLastLoginExpired(): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date())

        val lastLoginDate = sharedPreferences.getString(lastLoginDateKey, "")

        return lastLoginDate.isNullOrEmpty() || daysBetween(lastLoginDate, currentDate) > 2
    }

    private fun mostrarVentanaDialogo() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sesión expirada")
            .setMessage("Por favor, introduzca nuevamente las credenciales.")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                // Aquí puedes agregar acciones adicionales si es necesario
            })
            .show()
    }

    private fun isFirstTimeOpen(): Boolean {
        // Obtener el valor almacenado en SharedPreferences
        return sharedPreferences.getBoolean("isFirstTimeOpen", true)
    }
    private fun markAppOpened() {
        // Marcar que la aplicación ya se ha abierto
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTimeOpen", false)
        editor.apply()
    }
}

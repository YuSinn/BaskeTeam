package com.alexandru.vladut.gabriel97.basketeam.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.alexandru.vladut.gabriel97.basketeam.AltaEquipo
import com.alexandru.vladut.gabriel97.basketeam.R

class AltaEquipoDialog : DialogFragment() {

    var altaEquipo:AltaEquipo? = null;

    lateinit var editTextNombreEquipo: EditText;
    lateinit var editTextCiudad: EditText;
    lateinit var editTextPabellon:EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        var viewDialog = inflater.inflate(R.layout.activity_alta_equipo_dialog, container);

        editTextNombreEquipo = viewDialog.findViewById(R.id.editTextNombreEquipo);
        editTextCiudad = viewDialog.findViewById(R.id.editTextCiudad);
        editTextPabellon = viewDialog.findViewById(R.id.editTextPabellon)

        var botonCancelar = viewDialog.findViewById<Button>(R.id.buttonCancelar);
        botonCancelar.setOnClickListener { view -> cancelar() };

        var botonAceptar = viewDialog.findViewById<Button>(R.id.buttonAceptar);
        botonAceptar.setOnClickListener { view -> aceptar() };

        return  viewDialog;
    }

    fun cancelar(){
        dismiss();
    }
    interface OnDialogResultListener {
        fun onResult(nombre: String, ciudad: String, pabellon: String)
    }

    private var listener: OnDialogResultListener? = null

    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }

    // Método para enviar el resultado al listener
    private fun sendResult(nombre: String, ciudad: String, pabellon: String) {
        listener?.onResult(nombre, ciudad, pabellon)
    }
    fun aceptar(){
        var nombre = editTextNombreEquipo.text.toString();
        var ciudad = editTextCiudad.text.toString();
        var pabellon = editTextPabellon.text.toString()
        listener?.onResult(nombre, ciudad, pabellon)

        dismiss();
    }
}
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

class ListaJugadorDialog : DialogFragment() {

    var altaEquipo: AltaEquipo? = null

    lateinit var editTextNombreEquipo: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        var viewDialog = inflater.inflate(R.layout.activity_lista_jugador_dialog, container)

        editTextNombreEquipo = viewDialog.findViewById(R.id.editTextNombreEquipo)

        var botonCancelar = viewDialog.findViewById<Button>(R.id.buttonCancelar)
        botonCancelar.setOnClickListener { view -> cancelar() }

        var botonAceptar = viewDialog.findViewById<Button>(R.id.buttonAceptar)
        botonAceptar.setOnClickListener { view -> aceptar() }

        return  viewDialog
    }

    fun cancelar(){
        dismiss()
    }
    interface OnDialogResultListener {
        fun onResult(nombre: String)
    }

    private var listener: OnDialogResultListener? = null

    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }

    // Método para enviar el resultado al listener
    private fun sendResult(nombre: String) {
        listener?.onResult(nombre)
    }
    fun aceptar(){
        var nombre = editTextNombreEquipo.text.toString()

        listener?.onResult(nombre)

        dismiss()
    }
}
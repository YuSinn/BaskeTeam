package com.alexandru.vladut.gabriel97.basketeam.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.alexandru.vladut.gabriel97.basketeam.AltaEquipo
import com.alexandru.vladut.gabriel97.basketeam.EditarEquipo
import com.alexandru.vladut.gabriel97.basketeam.R

class EditarJugadorDialog : DialogFragment() {

    var editarEquipo: EditarEquipo? = null


    lateinit var editTextDorsal: EditText
    lateinit var editTextPosicion: EditText
    lateinit var editTextNombre: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        var viewDialog = inflater.inflate(R.layout.activity_editar_jugador_dialog, container)

        editTextDorsal = viewDialog.findViewById(R.id.editarTextDorsal)
        editTextPosicion = viewDialog.findViewById(R.id.editarTextPosicion)
        editTextNombre = viewDialog.findViewById(R.id.editarTextNombre)

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
        fun onResult(nombre:String,dorsal:Int,posicio:String)
    }

    private var listener: OnDialogResultListener? = null

    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }

    // Método para enviar el resultado al listener
    private fun sendResult(nombre: String,dorsal:Int,posicio:String) {
        listener?.onResult(nombre,dorsal,posicio)
    }
    fun aceptar(){
        var nombre = editTextNombre.text.toString()
        var dorsal = editTextDorsal.text.toString().toInt()
        var posicio = editTextPosicion.text.toString()

        listener?.onResult(nombre ,dorsal,posicio)

        dismiss()
    }
}
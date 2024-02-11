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

class AltaJugadorDialog :DialogFragment() {

    var altaEquipo: AltaEquipo? = null
    var editarEquipo: EditarEquipo? = null
    lateinit var editTextNombreJugador: EditText
    lateinit var editTextDorsal: EditText
    lateinit var editTextPosicion: EditText
    lateinit var editTextNombreEquipoFK: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        var viewDialog = inflater.inflate(R.layout.activity_alta_jugador_dialog, container)

        editTextNombreJugador = viewDialog.findViewById(R.id.editTextNombreJugador)
        editTextDorsal = viewDialog.findViewById(R.id.editTextDorsal)
        editTextPosicion = viewDialog.findViewById(R.id.editTextPosicion)
        editTextNombreEquipoFK = viewDialog.findViewById(R.id.editTextNombreEquipoFK)

        var botonCancelar = viewDialog.findViewById<Button>(R.id.buttonCancelar)
        botonCancelar.setOnClickListener { view -> cancelar() }

        var botonAceptar = viewDialog.findViewById<Button>(R.id.buttonAceptar);
        botonAceptar.setOnClickListener { view -> aceptar() }

        return  viewDialog
    }

    fun cancelar(){
        dismiss()
    }
    interface OnDialogJugadorResultListener {
        fun onResult(nombre: String, dorsal: Int, posicion: String, nombreEquipoFK:String)
    }

    private var listener: OnDialogJugadorResultListener? = null

    fun setOnDialogResultListener(listener: OnDialogJugadorResultListener) {
        this.listener = listener
    }

    // Método para enviar el resultado al listener
    private fun sendResult(nombre: String, dorsal: Int, posicion: String, nombreEquipoFK:String) {
        listener?.onResult(nombre, dorsal, posicion, nombreEquipoFK)
    }
    fun aceptar(){
        var nombre = editTextNombreJugador.text.toString()
        var dorsal = Integer.parseInt(editTextDorsal.text.toString())
        var posicion = editTextPosicion.text.toString()
        var equipo = editTextNombreEquipoFK.text.toString()
        listener?.onResult(nombre, dorsal, posicion,equipo)

        dismiss()
    }
}
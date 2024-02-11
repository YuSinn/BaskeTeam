package com.alexandru.vladut.gabriel97.basketeam.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.alexandru.vladut.gabriel97.basketeam.AltaResultado
import com.alexandru.vladut.gabriel97.basketeam.R

class AltaResultadoDialog:DialogFragment() {

    var altaResultado : AltaResultado? = null
    lateinit var editTextEquipoLocal: EditText
    lateinit var editTextEquipoVisitante: EditText
    lateinit var editTextGolesLocal: EditText
    lateinit var editTextGolesVisitante: EditText
    lateinit var editTextFecha: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        var viewDialog = inflater.inflate(R.layout.activity_alta_resultado_dialog, container)

        editTextEquipoLocal = viewDialog.findViewById(R.id.editTextEquipoLocal)
        editTextEquipoVisitante = viewDialog.findViewById(R.id.editTextEquipoVisitante)
        editTextGolesLocal = viewDialog.findViewById(R.id.editTextGolesLocal)
        editTextGolesVisitante = viewDialog.findViewById(R.id.editTextGolesVisitante)
        editTextFecha = viewDialog.findViewById(R.id.editTextFecha)

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
        fun onResult(equipoLocal:String,equipoVisitante:String,golesLocal:Int, golesVisitante:Int,fecha:String)
    }

    private var listener: OnDialogResultListener? = null

    fun setOnDialogResultListener(listener: OnDialogResultListener) {
        this.listener = listener
    }

    // Método para enviar el resultado al listener
    private fun sendResult(equipoLocal:String,equipoVisitante:String,golesLocal:Int, golesVisitante:Int,fecha:String) {
        listener?.onResult(equipoLocal,equipoVisitante,golesLocal,golesVisitante,fecha)
    }
    fun aceptar(){
        var equipoLocal = editTextEquipoLocal.text.toString()
        var equipoVisitante = editTextEquipoVisitante.text.toString()
        var golesLocal = editTextGolesLocal.text.toString().toInt()
        var golesVisitante = editTextGolesVisitante.text.toString().toInt()
        var fecha = editTextFecha.text.toString()
        listener?.onResult(equipoLocal,equipoVisitante,golesLocal,golesVisitante,fecha)

        dismiss()
    }
}
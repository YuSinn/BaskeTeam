package com.alexandru.vladut.gabriel97.basketeam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexandru.vladut.gabriel97.basketeam.R
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Clasificacion
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo

class CustomerAdapterClasificacion(private val clasificacionList:List<Clasificacion>):RecyclerView.Adapter<CustomerAdapterClasificacion.ClasificacionViewHolder>() {
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(clasifacion: Clasificacion)
    }
    class ClasificacionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val textViewEquipoClasificacion: TextView
        val textViewPuntos: TextView

        init {

            textViewEquipoClasificacion = itemView.findViewById(R.id.card_clasificacion_equipo)
            textViewPuntos = itemView.findViewById(R.id.card_clasificacion_puntos)
        }
        fun bind(clasifacion: Clasificacion, onClickListener: OnClickListener?, ) {
            textViewEquipoClasificacion.text = textViewEquipoClasificacion.text.toString() + clasifacion.nombreEquipoFK
            textViewPuntos.text = textViewPuntos.text.toString()+clasifacion.puntos.toString()


            itemView.setOnClickListener {
                onClickListener?.onClick(clasifacion)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClasificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_clasificacion,parent,false)
        return ClasificacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClasificacionViewHolder, position: Int) {
        holder.bind(clasificacionList[position], onClickListener)
    }

    override fun getItemCount()= clasificacionList.size

    fun setOnItemClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
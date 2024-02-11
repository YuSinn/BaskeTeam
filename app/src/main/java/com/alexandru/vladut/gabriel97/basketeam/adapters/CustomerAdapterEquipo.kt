package com.alexandru.vladut.gabriel97.basketeam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexandru.vladut.gabriel97.basketeam.R
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador

class CustomerAdapterEquipo(private val equipoList:List<Equipo>):RecyclerView.Adapter<CustomerAdapterEquipo.EquipoViewHolder>() {

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(equipo: Equipo)
    }
    class EquipoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewNombre: TextView
        val textViewCiudad: TextView
        val textViewPabellon: TextView

        init {
            textViewNombre = itemView.findViewById(R.id.card_nombreEquipo)
            textViewCiudad = itemView.findViewById(R.id.card_nombreCiudad)
            textViewPabellon = itemView.findViewById(R.id.card_nombrePabellon)
        }
        fun bind(equipo: Equipo, onClickListener: OnClickListener?, ) {
            textViewNombre.text = textViewNombre.text.toString() + equipo.nombreEquipo
            textViewCiudad.text = textViewCiudad.text.toString() + equipo.ciudad
            textViewPabellon.text = textViewPabellon.text.toString()+equipo.nombrePabellon


            itemView.setOnClickListener {
                onClickListener?.onClick(equipo)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_equipo,parent,false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        holder.bind(equipoList[position], onClickListener)
    }

    override fun getItemCount()= equipoList.size

    fun setOnItemClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
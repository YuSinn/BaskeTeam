package com.alexandru.vladut.gabriel97.basketeam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexandru.vladut.gabriel97.basketeam.R
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador

class CustomerAdapterJugador(private val jugadorList:List<Jugador>):RecyclerView.Adapter< CustomerAdapterJugador.JugadorViewHolder>() {


    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(jugador: Jugador)
    }

    class JugadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView
        val textViewDorsal: TextView
        val textViewPosicion: TextView
        val textViewEquipo: TextView

        init {
            textViewNombre = itemView.findViewById(R.id.nombreJugador)
            textViewDorsal = itemView.findViewById(R.id.dorsalJugador)
            textViewPosicion = itemView.findViewById(R.id.posicionJugador)
            textViewEquipo = itemView.findViewById(R.id.equipoJugador)
        }

        fun bind(jugador: Jugador, onClickListener: OnClickListener?, ) {
            textViewNombre.text = textViewNombre.text.toString() + jugador.nombre
            textViewDorsal.text = textViewDorsal.text.toString() +jugador.dorsal.toString()
            textViewPosicion.text = textViewPosicion.text.toString()+jugador.posicion
            textViewEquipo.text = textViewEquipo.text.toString()+ jugador.nombreEquipoFK

            itemView.setOnClickListener {
                onClickListener?.onClick(jugador)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_jugador, parent, false)
        return JugadorViewHolder(view)
    }

    override fun getItemCount() = jugadorList.size


    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        //holder.textViewName.text = planetList[position].name
        //holder.textViewType.text = planetList[position].type
        //holder.imageViewPlanet.setImageResource(planetList[position].image)
        holder.bind(jugadorList[position], onClickListener)
    }

    fun setOnItemClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

}
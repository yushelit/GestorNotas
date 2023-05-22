package Adaptadores


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestornotas.R
import com.notes.Modelo.Nota

class NotasAdaptadorRecycler(var  context: Context, var notas : ArrayList<Nota>) : RecyclerView.Adapter<NotasAdaptadorRecycler.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notas[position]

        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.notas_view, parent, false))
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById(R.id.tituloNota) as TextView
        val fecha = view.findViewById(R.id.fechaNota) as TextView

        @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
        fun bind(note: Nota, context: Context, pos: Int, miAdaptadorRecycler: NotasAdaptadorRecycler) {
            titulo.text = note.titulo
            fecha.text = note.fecha

            if (pos == seleccionado) {
                with(titulo) {
                    this.setTextColor(resources.getColor(R.color.red))
                }
            }else {
                with(titulo) {
                    this.setTextColor(resources.getColor(R.color.white))
                }
            }
            itemView.setOnClickListener {
                seleccionado = if (pos == seleccionado) {
                    -1
                } else {
                    pos
                }
                miAdaptadorRecycler.notifyDataSetChanged()
            }
        }
    }

}
package adaptadores


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Modelo.ListaNota
import com.example.gestornotas.R


class ListasAdaptadorRecycler(var  context: Context, var listas : ArrayList<ListaNota>) : RecyclerView.Adapter<ListasAdaptadorRecycler.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listas[position]

        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.notas_view, parent, false))
    }

    override fun getItemCount(): Int {
        return listas.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val elemento = view.findViewById(R.id.elementoLista) as TextView
        val foto = view.findViewById(R.id.imageViewCheck) as ImageView

        @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
        fun bind(noteList: ListaNota, context: Context, pos: Int, miAdaptadorRecycler: ListasAdaptadorRecycler) {
           elemento.text = noteList.elemento
            val urli = "@drawable/check"
            val imageResource: Int = context.resources.getIdentifier(urli, null, context.packageName)
            val res: Drawable = context.resources.getDrawable(imageResource)
            foto.setImageDrawable(res)

            if(noteList.completado){
                foto.visibility = View.VISIBLE
            }else{
                foto.visibility = View.INVISIBLE
            }

            if (pos == seleccionado) {
                with(elemento) {
                    this.setTextColor(resources.getColor(R.color.blue))
                }
            }else {
                with(elemento) {
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
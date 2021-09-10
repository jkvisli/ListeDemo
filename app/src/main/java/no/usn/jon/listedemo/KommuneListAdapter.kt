package no.usn.jon.listedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import no.usn.jon.listedemo.KommuneListAdapter.KommuneViewHolder
import java.util.*

/**
 * Created by Jon on 30.01.2018.
 */
class KommuneListAdapter(private val context: Context, var kommuneListen: ArrayList<Kommune>)
        : RecyclerView.Adapter<KommuneViewHolder>()
{
    private val mInflater: LayoutInflater  // Objekt som kan lage View-objekter fra XML

    // Tillegg til primær konstruktør
    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KommuneViewHolder {
        // Demo versjon 1: Enkel layout
        //val minListItemLayout : Int = R.layout.kommune_list_item
        // Demo versjon 2: Med ImageView
        //val minListItemLayout : Int = R.layout.kommune_list_item2
        // Demo versjon 3: Med CardView
        val minListItemLayout : Int = R.layout.kommune_list_item_card
        val mittItemView = mInflater.inflate(minListItemLayout, parent, false)
        return KommuneViewHolder(mittItemView, this)
    }

    override fun onBindViewHolder(holder: KommuneViewHolder, position: Int) {
        val denneKommunen = kommuneListen[position]
        holder.kommuneNavnView.text = denneKommunen.kommuneNavn
        holder.kommunenrView.text   = "K.nr: " + denneKommunen.kommuneNummer
        holder.folketallView.text   = "Folketall: " + denneKommunen.folkeTall.toString()
        holder.fylkeView.text       = denneKommunen.fylke + " fylke"
        // Demo versjon 2: Med ImageView
        Glide.with(context).load(getBildeRessurs(denneKommunen.kommuneVaapen)).into(holder.kommuneVaapen)
    }

    override fun getItemCount(): Int = kommuneListen.size

    fun getBildeRessurs(imageName: String): Int {
        return context.resources.getIdentifier(
                imageName, "drawable", context.packageName)
    }

    // Indre ViewHolder-klasse som holder View-objekter for en rad
    inner class KommuneViewHolder
        (itemView: View, adapter: KommuneListAdapter)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val kommunenrView: TextView
        val kommuneNavnView: TextView
        val folketallView: TextView
        val fylkeView: TextView
        // Demo versjon 2: Med ImageView
        val kommuneVaapen: ImageView

        //val komAdapter: KommuneListAdapter

        init {
            kommunenrView   = itemView.findViewById<View>(R.id.txtKommunenr)   as TextView
            kommuneNavnView = itemView.findViewById<View>(R.id.txtKommuneNavn) as TextView
            folketallView   = itemView.findViewById<View>(R.id.txtFolketall)   as TextView
            fylkeView       = itemView.findViewById<View>(R.id.txtFylke)       as TextView
            // Demo versjon 2: Med ImageView
            kommuneVaapen   = itemView.findViewById<View>(R.id.kommmuneVaapen) as ImageView

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val kommunen = kommuneListen[layoutPosition]
            val aktivitet = context as KommuneListeActivity
            aktivitet.visKommuneWeb(kommunen)
            // Demo: Med SnackBar
            //MainActivity.visSnackbar(view, "Du valgte: " + kommunen.kommuneNavn)
        } // End of onClick()

    } // End of class KommuneViewHolder

}
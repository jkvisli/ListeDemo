package no.usn.jon.listedemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


class KommuneListeActivity : AppCompatActivity() {
    private lateinit var kommuneListe : ArrayList<Kommune>
    private lateinit var linLayoutMgr: RecyclerView.LayoutManager
    private lateinit var kommuneAdapter: RecyclerView.Adapter<*>
    private lateinit var kommuneRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kommune_liste)

        kommuneListe = Kommune.lagKommuneListe(resources)

        // Demo: SnackBar
        // visSnackbar(rotlayout, "OK: Kommuneliste lest!")

        if ( kommuneListe.size == 0)
            MainActivity.visSnackbar(findViewById<CoordinatorLayout>(R.id.kommuneLayout),
                "FEIL: Kan ikke lage kommuneliste!")
        else {
            // Lag layoutmanager med lineær layout
            linLayoutMgr = LinearLayoutManager(applicationContext)

            // Lag adapter med data som skal vises i listen
            kommuneAdapter = KommuneListAdapter(this, kommuneListe)

            // Get a handle to the RecyclerView.
            kommuneRecyclerView = findViewById<RecyclerView>(R.id.recyclerKommuneView).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)
                // Give the RecyclerView a default layout manager.
                layoutManager = linLayoutMgr
                // Connect the adapter with the RecyclerView.
                adapter = kommuneAdapter

            }

            // Lytter for sveipebevegelser
            val helper = ItemTouchHelper(
                    object : ItemTouchHelper.SimpleCallback(
                            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                            ItemTouchHelper.LEFT ) {
                        override fun onMove(recyclerView: RecyclerView,
                                            viewHolder: ViewHolder, target: ViewHolder): Boolean {
                            val fraPos = viewHolder.adapterPosition
                            val tilPos = target.adapterPosition
                            Collections.swap(kommuneListe, fraPos, tilPos);
                            kommuneAdapter.notifyItemMoved(fraPos, tilPos);
                            return true // true if moved, false otherwise
                        }

                        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                            if (direction == ItemTouchHelper.LEFT) {
                                kommuneListe.removeAt(viewHolder.getAdapterPosition());
                                kommuneAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                            }
                        }
                    }
            )
            helper.attachToRecyclerView(kommuneRecyclerView);
        }
    } // End of onCreate()

    fun visKommuneWeb(kommunen: Kommune) {
        val wikiURL = "https://no.wikipedia.org/wiki/" + kommunen.kommuneNavn.replace(" ", "_")
        val uri: Uri = Uri.parse(wikiURL)
        val webIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(webIntent)
    }

} // End of class KommuneListeActivity

package no.usn.jon.listedemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class FylkeListeActivity : AppCompatActivity() {

    // Strengtabell for å holde alle fylkene, mottas fra MainActivity
    private lateinit var fylkeTabell: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fylke_liste)

        fylkeTabell = resources.getStringArray(R.array.norske_fylker)

        // Bygg ArrayAdapter for ListView'et og knytt det til ListView'et

        // Standard-utseende på hver rad i listen
        // Første demoversjon med enkel standard layout
        val fylkeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fylkeTabell)
        // Andre demoversjon med egendefinert utseende på hver rad i listen: fylke_liste_item.xml
        // val fylkeAdapter = ArrayAdapter(this, R.layout.fylke_liste_item, fylkeTabell)

        val fylkeListen: ListView = findViewById(R.id.fylke_liste)
        fylkeListen.adapter = fylkeAdapter

        // Lyttemetode for ListView'et -  fyrer når bruker velger en rad i listen
        fylkeListen.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            // Første demoversjon: Vis webside om valgt fylke
            visFylkeWeb(fylkeTabell[position])
            // Andre demoversjon: Vise en snackbar med valgt fylke
            //MainActivity.visSnackbar(findViewById<View>(R.id.fylkeListeLayout), "Du valgte: " +fylkeTabell[position])
        }
    }

    private fun visFylkeWeb(fylke: String) {
        val urlFylke = "https://no.wikipedia.org/wiki/" + fylke.replace(" ", "_")
        val uri = Uri.parse(urlFylke)
        val webIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(webIntent)
    }

}
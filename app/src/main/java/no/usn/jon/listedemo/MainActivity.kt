package no.usn.jon.listedemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //setSupportActionBar(toolbar)
    }

    // Lyttemetode for knapp som viser liste med fylker
    fun onFylkerClicked(v: View?) {
        val fylkeIntent = Intent(applicationContext, FylkeListeActivity::class.java)
        startActivity(fylkeIntent)
    }

    fun onBilderClicked(view: android.view.View) {}

    // Lyttemetode for knapp som viser liste med kommuner
    fun onKommunerClicked(view: View?) {
        val kommuneIntent = Intent(applicationContext, KommuneListeActivity::class.java)
        startActivity(kommuneIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // Demo: Statisk metode for å vise SnackBar
    companion object {
        @JvmStatic
        fun visSnackbar(view: View?, melding: String?) {
            Snackbar.make(view!!, melding!!, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }
    }

}
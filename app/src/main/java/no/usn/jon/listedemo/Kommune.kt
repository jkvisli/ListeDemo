package no.usn.jon.listedemo

import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.json.JSONObject
import java.util.*

// "Modellklasse" for å representere informasjon om kommuner
class Kommune {
    var kommuneNummer: String
    var kommuneNavn: String
    var fylke: String
    var folkeTall: Int
    var areal: Double
    var kommuneVaapen: String

    override fun toString() : String = kommuneNavn

    // Sekundær konstruktør som bygger et Kommune-objekt basert på et JSONObject
    constructor(jsonKommune: JSONObject)  {
        kommuneNummer   = jsonKommune.optString("Kommunenr")
        kommuneNavn     = jsonKommune.optString("Kommunenavn")
        fylke           = jsonKommune.optString("Fylke")
        folkeTall       = jsonKommune.optInt("Folketall")
        areal           = jsonKommune.optDouble("Areal")
        kommuneVaapen   = jsonKommune.optString("Kommunevaapen")
    }

    // "Vanlig" sekundær konstruktør (brukes ikke i eksempelet)
    constructor(kommuneNr: String, kommuneNavn: String, fylke: String,
                folkeTall: Int, areal: Double, kommuneVaapen: String) {
        this.kommuneNummer  = kommuneNr
        this.kommuneNavn    = kommuneNavn
        this.fylke          = fylke
        this.folkeTall      = folkeTall
        this.areal          = areal
        this.kommuneVaapen  = kommuneVaapen
    }

    companion object {
        // Klassemetode som bygger en ArrayList av Fylke objekter basert på en tekststreng i JSON-format
        @JvmStatic
        fun lagKommuneListe(resources: Resources): ArrayList<Kommune> {
            // Bygg en ArrayList av Kommune-objekter
            val kommuneListe = ArrayList<Kommune>()

            try {
                // Les kommunedata i JSON-format fra ressursfil
                val jsonKommuner : String = lesKommuneDataFraFil(resources)
                // Bygg et JSONObject basert på hele strengen med JSON-data
                val jsonAlleKomm = JSONObject(jsonKommuner)

                // Hent ut JSON-tabellen "entries" med alle JSON-objektene
                val jsonKommuneTabell = jsonAlleKomm.optJSONArray("kommuner")
                for (i in 0 until jsonKommuneTabell.length()) {
                    // Hent ut JSON-objekt nr i fra JSONArray'en
                    val jsonKommune = jsonKommuneTabell[i] as JSONObject
                    // Bygg et Kommune-objekt basert på JSON-objektet
                    val denneKommunen = Kommune(jsonKommune)
                    // Legg Kommune-objektet inn i ArrayList'en av Kommune
                    kommuneListe.add(denneKommunen)
                }
                // returner ArrayList-objektet med alle kommunene
                return kommuneListe
            } catch (e: Exception) {
                return kommuneListe
            }
        } // End of lagKommuneListe

        @JvmStatic
        @Throws(IOException::class, NullPointerException::class)
        private fun lesKommuneDataFraFil(resources: Resources): String {
            var enLinje: String?
            var heleFilen = StringBuilder()
            try {
                // Bygg inputstrøm for lesing av datafil
                val inputStream = resources.openRawResource(R.raw.kommunedata)
                val reader = BufferedReader(InputStreamReader(inputStream))

                // Les alle rader over i et StringBuilder objekt
                while (reader.readLine().also { enLinje = it } != null) {
                    heleFilen = heleFilen.append(enLinje)
                }
                // Her inneholder variabelen heleFilen alt innholdet i JSON-filen som tekst
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return heleFilen.toString()
        }

    } // End of companion object

} // End of class Kommune

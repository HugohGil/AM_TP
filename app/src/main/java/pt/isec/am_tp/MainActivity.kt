package pt.isec.am_tp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import pt.isec.am_tp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var appLanguage = "en"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appLanguage = intent.getStringExtra(currentLang).toString()
        if (appLanguage == "null") {                              // first time
            setDefaultLanguage()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    private fun setDefaultLanguage() {
        if (Locale.getDefault().language == "pt")
            setLocale("pt")
        else
            setLocale("en")
    }

    private fun changeLanguage() {
        val languages = arrayOf("English", "Português")

        val dlgLanguage = AlertDialog.Builder(this)
            .setTitle(R.string.msg_choose_language)
            .setSingleChoiceItems(languages, -1) { _, which ->
                if (which == 0) {
                    setLocale("en")
                } else if (which == 1) {
                    setLocale("pt")
                }
            }
            .setNegativeButton(R.string.msg_cancel, null)
            .create()
        dlgLanguage.show()
    }

    private fun setLocale(l: String) {
        if (appLanguage != l) {
            val locale = Locale(l)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)

            val intent = Intent(
                this,
                MainActivity::class.java
            )
            intent.putExtra(currentLang, l)
            startActivity(intent)
        } else
            Toast.makeText(this, R.string.msg_error_language, Toast.LENGTH_LONG).show()
    }
}
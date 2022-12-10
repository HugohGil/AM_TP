package pt.isec.am_tp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import pt.isec.am_tp.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var appLanguage = "en"
    private var currentLanguage: String? = null
    private var requestCode = 0
    private var SECOND_ACTIVITY_REQUEST_CODE = 12
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appLanguage = intent.getStringExtra(currentLanguage).toString()
        if (appLanguage == "null") {                            // first time
            setDefaultLanguage()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, GameModeActivity::class.java)
            startActivity(intent)
        }

        binding.btnProfile?.setOnClickListener {
            val intent = Intent(this, ConfigImageActivity::class.java)
            startActivityForResult(intent,requestCode);
        }
        binding.btnTop5.setOnClickListener {
            val intent = Intent(this, Top5Activity::class.java)
            startActivity(intent)
        }

        binding.btnLanguage.setOnClickListener {
            changeLanguage()
        }

        binding.btnCredits.setOnClickListener {
            val intent = Intent(this, CreditsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            } else {
                // handle cancellation
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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
            intent.putExtra(currentLanguage, l)
            startActivity(intent)
        } else
            Toast.makeText(this, R.string.msg_error_language, Toast.LENGTH_LONG).show()
    }
}
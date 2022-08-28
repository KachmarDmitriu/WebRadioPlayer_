package com.example.webradioplayer.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.webradioplayer.R

class AddNewRadioActivity: AppCompatActivity() {

   public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_radio_activity)

        val editNameView = findViewById<EditText>(R.id.edit_name)
       val editUrlView = findViewById<EditText>(R.id.edit_url)

        val button = findViewById<Button>(R.id.button_save)

       // только для поля name, что делать для url
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editNameView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"

}
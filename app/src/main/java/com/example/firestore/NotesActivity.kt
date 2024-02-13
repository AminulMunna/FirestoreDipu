package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firestore.databinding.ActivityNotesBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class NotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotesBinding
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        binding.saveNotesBtn.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.titleEt.text.toString().trim()
        val description = binding.descriptionEt.text.toString().trim()
        if (title.isNotEmpty() && description.isNotEmpty()) {
            val collection = firestore.collection("Notes").document()
            val notes = Notes(
                collection.id,
                title = title,
                description = description,
                timeStamp = Timestamp.now()
            )
            collection.set(notes)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@NotesActivity,
                        "Notes saved successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@NotesActivity, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                .addOnFailureListener {
                    Toast.makeText(this@NotesActivity, "Failed to saved notes.", Toast.LENGTH_SHORT)
                        .show()
                }

        } else {
            Toast.makeText(this@NotesActivity, "Please fill all the fields.", Toast.LENGTH_SHORT)
                .show()
        }


    }
}

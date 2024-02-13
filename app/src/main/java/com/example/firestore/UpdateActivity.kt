package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firestore.databinding.ActivityUpdateBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateBinding
    private lateinit var firestore: FirebaseFirestore
    private var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        getIntentData()
        binding.updateNotesBtn.setOnClickListener {
            val title = binding.titleEt.text.toString()
            val description = binding.descriptionEt.text.toString()
            updateData(title,description)

        }


    }

    private fun getIntentData() {
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("description")
        id = intent.getStringExtra("id").toString()
        binding.titleEt.setText(title)
        binding.descriptionEt.setText(desc)
    }

    private fun updateData(title: String, description: String) {
        if (title.isNotEmpty() && description.isNotEmpty()){
            val collection = firestore.collection("Notes")
            val notes = Notes(id = id,title = title, description = description, Timestamp.now())
            collection.document(notes.id!!).set(notes)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@UpdateActivity, "Update note.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@UpdateActivity,MainActivity::class.java))
                        finishAffinity()
                    }
                    else{
                        Toast.makeText(this@UpdateActivity, "Failed to update note.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
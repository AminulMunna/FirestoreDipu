package com.example.firestore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firestore.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private lateinit var noteList: ArrayList<Notes>
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.logoutNoteBtn.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                firebaseAuth.signOut()
                val intent = Intent(
                    this@MainActivity, LoginActivity::class.java
                )
                startActivity(intent)
                finishAffinity()
            }

        }
        binding.addNoteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotesActivity::class.java))
        }
        firestore = FirebaseFirestore.getInstance()
        noteList = arrayListOf()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()

        val verification = firebaseAuth.currentUser?.isEmailVerified
        if (verification == true) {

        }


    }

    private fun fetchData() {
        val collection = firestore.collection("Notes")

        collection.orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnSuccessListener {
                noteList.clear()
                for (notes in it) {
                    val notes = notes.toObject(Notes::class.java)
                    noteList.add(notes)
                }
                notesAdapter = NotesAdapter(noteList, this@MainActivity)
                binding.recyclerView.adapter = notesAdapter
                notesAdapter.notifyDataSetChanged()

            }

    }

    override fun onEditClickListener(position: Int, notes: Notes) {
        val intent = Intent(this@MainActivity, UpdateActivity::class.java)
        intent.putExtra("id", notes.id)
        intent.putExtra("title", notes.title)
        intent.putExtra("description", notes.description)
        startActivity(intent)

    }

    override fun onDeleteClickListener(position: Int, notes: Notes) {
        val collection = firestore.collection("Notes")
        collection.document(notes.id!!).delete().addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Notes deleted successfully.", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "Failed to delete notes.", Toast.LENGTH_SHORT)
                    .show()
            }
        notesAdapter.notifyItemRemoved(position)
        noteList.removeAt(position)

    }
}
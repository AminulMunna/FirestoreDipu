package com.example.firestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter (private val notesList: List<Notes>,private val itemClickListener : ItemClickListener)
    : RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val title = view.findViewById<TextView>(R.id.titleTv)
            val desc = view.findViewById<TextView>(R.id.descriptionTv)
            val editBtn = view.findViewById<ImageButton>(R.id.editBtn)
            val delBtn = view.findViewById<ImageButton>(R.id.delBtn)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return notesList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes : Notes = notesList[position]
        holder.apply {
            title.text = notes.title
            desc.text = notes.description
        }
       holder.apply {
           editBtn.setOnClickListener {
               itemClickListener.onEditClickListener(position,notes)
           }
           delBtn.setOnClickListener {
               itemClickListener.onDeleteClickListener(position,notes)
           }
       }
    }
}

interface ItemClickListener {
    fun onEditClickListener(position: Int,notes: Notes)
    fun onDeleteClickListener(position: Int,notes: Notes)
}

package com.example.notes_cm.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_cm.R
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel

class AddFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        view.findViewById<Button>(R.id.save).setOnClickListener {
            addNote()
        }

        view.findViewById<Button>(R.id.backToList).setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        return view
    }

    private fun addNote() {
        val noteText = view?.findViewById<EditText>(R.id.addNote)?.text.toString()
        val descriptionText = view?.findViewById<EditText>(R.id.addDescription)?.text.toString()
        val dataText = view?.findViewById<EditText>(R.id.addData)?.text.toString()

        if (noteText.isEmpty()) {
            Toast.makeText(view?.context, "Não pode uma nota vazia!", Toast.LENGTH_LONG).show()
        } else if (descriptionText.isEmpty() || descriptionText.length < 5) {
            Toast.makeText(view?.context, "A descrição não pode estar vazia e tem que ter mais que 5 carateres!", Toast.LENGTH_LONG).show()
        } else if (dataText.isEmpty()) {
            Toast.makeText(view?.context, "A data não pode estar vazia!", Toast.LENGTH_LONG).show()
        } else {
            val note = Note(0, noteText, descriptionText, dataText)

            mNoteViewModel.addNote(note)

            Toast.makeText(requireContext(), "Gravado com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

}
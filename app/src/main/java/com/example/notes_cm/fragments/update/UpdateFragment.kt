package com.example.notes_cm.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_cm.R
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val updateNoteEditText = view.findViewById<EditText>(R.id.updateNote)
        val updateDescriptionEditText = view.findViewById<EditText>(R.id.updateDescription)

        val updateDateEditText = view.findViewById<EditText>(R.id.updateData)

        updateNoteEditText.setText(args.currentNote.note)
        updateDescriptionEditText.setText(args.currentNote.description)

        updateDateEditText.setText(args.currentNote.date)

        view.findViewById<Button>(R.id.update).setOnClickListener {
            updateNote()
        }

        view.findViewById<Button>(R.id.delete).setOnClickListener {
            deleteNote()
        }

        view.findViewById<Button>(R.id.backToListFromUpdate).setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return view
    }

    private fun updateNote() {
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()
        val descriptionText = view?.findViewById<EditText>(R.id.updateDescription)?.text.toString()
        val dataText = view?.findViewById<EditText>(R.id.updateData)?.text.toString()

        if (noteText.isEmpty()) {
            Toast.makeText(view?.context, "Não pode uma nota vazia!", Toast.LENGTH_LONG).show()
        } else if (descriptionText.isEmpty() || descriptionText.length < 5) { // Correção aqui
            Toast.makeText(view?.context, "A descrição não pode estar vazia e tem que ter mais que 5 caracteres!", Toast.LENGTH_LONG).show()
        } else if (dataText.isEmpty()) {
            Toast.makeText(view?.context, "A data não pode estar vazia!", Toast.LENGTH_LONG).show()
        } else {
            val noteId = args.currentNote.id // Correção aqui
            val note = Note(noteId, noteText, descriptionText, dataText)

            mNoteViewModel.updateNote(note)

            Toast.makeText(requireContext(), "Nota atualizada com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }



    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            makeText(
                requireContext(),
                "Nota apagada com sucesso!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar a Nota?")
        builder.create().show()
    }
}
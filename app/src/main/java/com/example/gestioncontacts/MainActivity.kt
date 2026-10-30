package com.example.gestioncontacts

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Le ViewModel garde les données pendant les rotations
    private val viewModel: ContactsViewModel by viewModels()

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var etContact: EditText
    private lateinit var btnModifier: Button
    private lateinit var btnAjouter: Button
    private lateinit var lvContacts: ListView

    private val contacts get() = viewModel.contacts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etContact = findViewById(R.id.etContact)
        btnModifier = findViewById(R.id.btnModifier)
        btnAjouter = findViewById(R.id.btnAjouter)
        lvContacts = findViewById(R.id.lvContacts)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            contacts
        )
        lvContacts.adapter = adapter

        // Restaure la sélection après rotation
        val pos = viewModel.positionSelectionnee
        if (pos >= 0 && pos < contacts.size) {
            lvContacts.setItemChecked(pos, true)
        } else {
            viewModel.positionSelectionnee = -1
        }

        lvContacts.setOnItemClickListener { _, _, position, _ ->
            viewModel.positionSelectionnee = position
            lvContacts.setItemChecked(position, true)
            etContact.setText(contacts[position])
            etContact.setSelection(etContact.text.length)
            etContact.requestFocus()
        }

        lvContacts.setOnItemLongClickListener { _, _, position, _ ->
            confirmerSuppression(position)
            true
        }

        btnModifier.setOnClickListener { modifierContact() }
        btnAjouter.setOnClickListener { ajouterContact() }

        etContact.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.positionSelectionnee == -1) ajouterContact() else modifierContact()
                true
            } else {
                false
            }
        }
    }

    private fun ajouterContact() {
        val nom = etContact.text.toString().trim()
        if (nom.isEmpty()) {
            etContact.error = getString(R.string.msg_nom_vide)
            return
        }
        if (contacts.any { c -> c.equals(nom, ignoreCase = true) }) {
            etContact.error = getString(R.string.msg_doublon)
            return
        }

        contacts.add(nom)
        contacts.sort()
        adapter.notifyDataSetChanged()

        viewModel.positionSelectionnee = contacts.indexOf(nom)
        lvContacts.setItemChecked(viewModel.positionSelectionnee, true)
        lvContacts.smoothScrollToPosition(viewModel.positionSelectionnee)

        Toast.makeText(this, getString(R.string.msg_ajoute, nom), Toast.LENGTH_SHORT).show()
    }

    private fun modifierContact() {
        val position = viewModel.positionSelectionnee
        if (position == -1) {
            Toast.makeText(this, R.string.msg_aucune_selection, Toast.LENGTH_SHORT).show()
            return
        }

        val nouveauNom = etContact.text.toString().trim()
        if (nouveauNom.isEmpty()) {
            etContact.error = getString(R.string.msg_nom_vide)
            return
        }

        var doublon = false
        for (i in contacts.indices) {
            if (i != position && contacts[i].equals(nouveauNom, ignoreCase = true)) {
                doublon = true
            }
        }
        if (doublon) {
            etContact.error = getString(R.string.msg_doublon)
            return
        }

        contacts[position] = nouveauNom
        contacts.sort()
        adapter.notifyDataSetChanged()

        viewModel.positionSelectionnee = contacts.indexOf(nouveauNom)
        lvContacts.setItemChecked(viewModel.positionSelectionnee, true)
        lvContacts.smoothScrollToPosition(viewModel.positionSelectionnee)

        Toast.makeText(this, getString(R.string.msg_modifie, nouveauNom), Toast.LENGTH_SHORT).show()
    }

    private fun confirmerSuppression(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.titre_supprimer)
        builder.setMessage(getString(R.string.msg_confirm_supprimer, contacts[position]))
        builder.setPositiveButton(R.string.oui) { _, _ ->
            contacts.removeAt(position)
            adapter.notifyDataSetChanged()

            lvContacts.clearChoices()
            viewModel.positionSelectionnee = -1
            etContact.text.clear()

            Toast.makeText(this, R.string.msg_supprime, Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(R.string.non, null)
        builder.show()
    }
}
package com.example.gestioncontacts

import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {

    // Ces données survivent à la rotation de l'écran
    val contacts = mutableListOf(
        "Ahmed Ben Ali",
        "Sarra Trabelsi",
        "Mohamed Gharbi",
        "Ines Jaziri",
        "Youssef Mansouri",
        "Amira Chaabane",
        "Karim Bouazizi",
        "Nour Hammami"
    ).apply { sort() }

    var positionSelectionnee = -1
}
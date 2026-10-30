# Gestion des contacts

Application Android qui affiche une liste de contacts et permet de les ajouter, les modifier et les supprimer. Projet réalisé dans le cadre d'un TP sur la `ListView` en Kotlin.

## Fonctionnalités

- Affichage des contacts dans une `ListView`, triés par ordre alphabétique
- Sélection d'un contact : son nom est copié dans le champ de saisie
- Ajout d'un nouveau contact (bouton **Ajouter**)
- Modification du contact sélectionné (bouton **Modifier**)
- Suppression par appui long, avec confirmation (`AlertDialog`)
- Refus des doublons et des noms vides
- Conservation des données lors de la rotation de l'écran (`ViewModel`)

## Technologies

- Kotlin, layouts XML (Views), `ListView` + `ArrayAdapter`
- AndroidX AppCompat, `ViewModel`
- Min SDK 24 / Target SDK 35
- Package : `com.example.gestioncontacts`

## Installation

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/gestion-contacts.git
   ```
2. Ouvrir le dossier avec Android Studio
3. Laisser Gradle synchroniser le projet (aucune clé API n'est nécessaire)
4. Lancer sur un émulateur ou un appareil (bouton ▶ **Run**)

## Utilisation

| Action | Geste |
|---|---|
| Sélectionner un contact | Toucher une ligne de la liste |
| Modifier | Changer le texte, puis **Modifier** |
| Ajouter | Saisir un nom, puis **Ajouter** |
| Supprimer | Appui long sur un contact, puis **Oui** |

## Structure du projet

```
app/src/main/
├── java/com/example/gestioncontacts/
│   ├── MainActivity.kt
│   └── ContactsViewModel.kt
└── res/
    ├── layout/activity_main.xml
    └── values/strings.xml
```


## Limites et pistes d'amélioration

- Les contacts ne sont pas enregistrés durablement : ils sont perdus à la fermeture de l'application. Piste : `SharedPreferences` ou Room.
- Migration possible vers `RecyclerView` pour de longues listes.

## Auteur

Votre nom : projet de TP Android Kotlin.
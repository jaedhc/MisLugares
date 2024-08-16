package com.example.mislugares.presentacion

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.mislugares.R

class PreferenciasFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferencias, rootKey)
    }
}
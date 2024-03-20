package com.example.note.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface Repository {
    val allModeldb:LiveData<List<Modeldb>>
    val allSelected: LiveData<List<Modeldb>>
    val allSelectedFlow: Flow<List<Modeldb>>
    suspend fun insert(modeldb: Modeldb)
    suspend fun delete(modeldb: Modeldb)
    suspend fun update(modeldb: Modeldb)
}
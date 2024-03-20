package com.example.note.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryRealization(private var daodb: Daodb):Repository{
    override val allModeldb: LiveData<List<Modeldb>>
        get() = daodb.getAllModelsdb()

    override val allSelected: LiveData<List<Modeldb>>
        get() = daodb.getAllSelected()
    override val allSelectedFlow: Flow<List<Modeldb>>
        get() = daodb.getAllSelectedFlow()

    override suspend fun insert(modeldb: Modeldb) {
        return daodb.insert(modeldb)
    }

    override suspend fun delete(modeldb: Modeldb) {
        return daodb.delete(modeldb)
    }

    override suspend fun update(modeldb: Modeldb) {
        return daodb.update(modeldb)
    }

}
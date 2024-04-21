package com.neperix.advalidation.materials

import java.io.File
import java.util.UUID

class LocalMaterialsStorage(
    private val path: String
) : MaterialsStorage {

    private val parentDir = File(path)

    override fun fetchMaterial(uuid: UUID): AdMaterial {
        val file = File(parentDir, uuid.toString())
        if (!file.exists()) {
            throw IllegalArgumentException("Could not find file $uuid in $path")
        }

        return AdMaterial(
            size = file.length()
        )
    }
}

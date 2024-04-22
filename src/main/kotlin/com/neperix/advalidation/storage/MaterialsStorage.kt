package com.neperix.advalidation.storage

import java.util.UUID

interface MaterialsStorage {

    fun fetchMaterial(uuid: UUID): AdMaterial
}

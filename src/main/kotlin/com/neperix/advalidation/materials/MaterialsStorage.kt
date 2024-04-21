package com.neperix.advalidation.materials

import java.util.UUID

interface MaterialsStorage {

    fun fetchMaterial(uuid: UUID): AdMaterial
}
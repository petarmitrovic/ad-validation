package com.neperix.advalidation.rules

import java.util.UUID

class DummyRulesService : RulesService {

    override fun getRules(productUUID: UUID): List<Rule> {
        return listOf(
            FileSizeRule(100_000),
            ResolutionRule(widthRange = 600..800, heightRange = 100..200, dpiRange = 200..300)
        )
    }
}

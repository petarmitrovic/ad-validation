package com.neperix.advalidation.rules

import java.util.UUID

/**
 * Represents an abstraction which is used to fetch rules, which can be either from other service (prod) or dummy implementation for local development.
 */
interface RulesService {

    fun getRules(productUUID: UUID): List<Rule>
}

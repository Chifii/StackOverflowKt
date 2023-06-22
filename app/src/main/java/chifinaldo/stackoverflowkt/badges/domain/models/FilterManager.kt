package chifinaldo.stackoverflowkt.badges.domain.models

class FilterManager {
    private var currentOrder: Boolean? = null
    private var currentSort: String? = null

    fun applyFilters(order: Boolean? = null, sort: String? = null) {
        order?.let { currentOrder = it }
        sort?.let { currentSort = it }
    }

    fun createCombinedFilter(): Pair<Boolean?, String?> {
        val order = currentOrder ?: true
        val sort = currentSort ?: "Default"
        return Pair(order, sort)
    }
}
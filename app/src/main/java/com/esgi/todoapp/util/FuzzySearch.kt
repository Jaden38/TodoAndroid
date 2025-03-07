package com.esgi.todoapp.util

import com.esgi.todoapp.domain.model.Task

/**
 * Utility class for performing fuzzy search on task items
 */
object FuzzySearch {

    /**
     * Perform a fuzzy search on a list of tasks
     *
     * @param tasks The list of tasks to search through
     * @param query The search query
     * @return A list of tasks that match the query, with scores for sorting
     */
    fun searchTasks(tasks: List<Task>, query: String): List<Pair<Task, Int>> {
        if (query.isBlank()) {
            return tasks.map { it to 100 }
        }

        val lowercaseQuery = query.lowercase()

        return tasks.mapNotNull { task ->
            val titleScore = calculateMatchScore(task.title.lowercase(), lowercaseQuery)
            val descriptionScore = calculateMatchScore(task.description.lowercase(), lowercaseQuery) / 2
            val tagScore = task.tags.maxOfOrNull { calculateMatchScore(it.lowercase(), lowercaseQuery) } ?: 0

            val totalScore = maxOf(titleScore, descriptionScore, tagScore)

            if (totalScore > 0) {
                task to totalScore
            } else {
                null
            }
        }.sortedByDescending { it.second }
    }

    /**
     * Calculate a match score for fuzzy matching
     * A higher score means a better match
     *
     * @param text The text to search in
     * @param query The query to search for
     * @return A score from 0-100, where 0 means no match and 100 is a perfect match
     */
    private fun calculateMatchScore(text: String, query: String): Int {

        if (text == query) {
            return 100
        }


        if (text.contains(query)) {

            return if (text.startsWith(query)) {
                90
            } else {
                70
            }
        }


        if (text.length < 2 || query.length > text.length) {
            return 0
        }


        var consecutiveMatches = 0
        var totalMatches = 0
        var textIndex = 0
        var queryIndex = 0

        while (textIndex < text.length && queryIndex < query.length) {
            if (text[textIndex] == query[queryIndex]) {
                totalMatches++
                consecutiveMatches++
                queryIndex++
            } else {
                consecutiveMatches = 0
            }
            textIndex++
        }


        if (queryIndex < query.length) {
            return 0
        }


        val matchPercentage = (totalMatches.toDouble() / query.length) * 50
        val consecutiveBonus = (consecutiveMatches.toDouble() / query.length) * 20

        return (matchPercentage + consecutiveBonus).toInt()
    }

    /**
     * Check if a string matches a query using fuzzy matching
     *
     * @param text The text to search in
     * @param query The query to search for
     * @return True if there's a match, false otherwise
     */
    private fun fuzzyMatch(text: String, query: String): Boolean {
        if (query.length > text.length) {
            return false
        }


        if (query.length <= 2) {
            return text.contains(query)
        }


        var textIndex = 0
        var queryIndex = 0

        while (textIndex < text.length && queryIndex < query.length) {
            if (text[textIndex] == query[queryIndex]) {
                queryIndex++
            }
            textIndex++
        }


        return queryIndex == query.length
    }
}
package com.esfimus.model.repository

import com.esfimus.database.SearchEntity
import com.esfimus.model.data.AppState
import com.esfimus.model.data.DataModel
import com.esfimus.model.data.Meanings
import com.esfimus.model.data.Translation
import com.esfimus.model.dto.SearchResultDto

fun mapHistoryEntityToSearchResult(list: List<SearchEntity>): List<SearchResultDto> {
    val searchResult = ArrayList<SearchResultDto>()
    if (list.isNotEmpty()) {
        for (entity in list) {
            searchResult.add(SearchResultDto(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): SearchEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isEmpty()) {
                null
            } else {
                SearchEntity(0, searchResult[0].text, null)
            }
        }
        else -> null
    }
}

fun mapSearchResultToResult(searchResults: List<SearchResultDto>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meanings> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meanings(
                    Translation(meaningsDto.translationDto?.translation ?: ""),
                    meaningsDto.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}

fun parseLocalSearchResults(data: AppState): AppState = AppState.Success(mapResult(data, false))

fun parseOnlineSearchResults(data: AppState): AppState = AppState.Success(mapResult(data, true))

private fun mapResult(data: AppState, isOnline: Boolean): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> { getSuccessResultData(data, isOnline, newSearchResults) }
        else -> {}
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newSearchDataModels: ArrayList<DataModel>
) {
    val searchDataModels: List<DataModel> = data.data as List<DataModel>
    if (searchDataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchDataModels) {
                parseOnlineResult(searchResult, newSearchDataModels)
            }
        } else {
            for (searchResult in searchDataModels) {
                newSearchDataModels.add(DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(searchDataModel: DataModel, newSearchDataModels: ArrayList<DataModel>) {
    if (searchDataModel.text.isNotBlank() && searchDataModel.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        newMeanings.addAll(searchDataModel.meanings.filter { it.translation.translatedMeaning.isNotBlank() })
        if (newMeanings.isNotEmpty()) {
            newSearchDataModels.add(DataModel(searchDataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToSingleString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation.translatedMeaning, ", ")
        } else {
            meaning.translation.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}
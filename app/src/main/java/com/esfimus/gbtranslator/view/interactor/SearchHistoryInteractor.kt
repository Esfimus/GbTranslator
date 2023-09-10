package com.esfimus.gbtranslator.view.interactor

import com.esfimus.model.data.AppState
import com.esfimus.model.dto.SearchResultDto
import com.esfimus.model.repository.Repository
import com.esfimus.model.repository.RepositoryLocal
import com.esfimus.model.repository.mapSearchResultToResult

class SearchHistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
): Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}
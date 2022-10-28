package com.erapps.moviesinfoapp.data.source

import com.erapps.moviesinfoapp.data.Result
import com.erapps.moviesinfoapp.data.api.NetworkResponse

suspend fun <R : Any, E : Any> mapResponse(
    request: suspend () -> NetworkResponse<R, E>
): Result<R, E> {
    Result.Loading
    return when (val result = request()) {
        is NetworkResponse.Success -> Result.Success(result.body)
        is NetworkResponse.ApiError -> Result.Error(result.body, result.code)
        is NetworkResponse.NetworkError -> Result.Error(exception = result.error)
        is NetworkResponse.UnknownError -> Result.Error(exception = result.error)
    }
}
package com.persons.finder.infrastructure

/**
 *  use for Paging query
 *  @param pageNumber current page number
 *  @param pageSize the amount of data in each page
 */
data class ApiRequest<T>(
    val pageNumber: Int,
    val pageSize: Int,
    val data: T? = null,
)

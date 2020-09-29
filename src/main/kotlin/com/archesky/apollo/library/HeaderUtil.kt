@file:Suppress("unused")

package com.archesky.apollo.library

import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.schema.DataFetchingEnvironment
import java.net.MalformedURLException
import java.net.URL
import javax.servlet.http.HttpServletRequest

object HeaderUtil {
    fun getHost(request: HttpServletRequest): String {
        try {
            return request.getHeader("hostname")
        } catch (e: IllegalStateException) {
            // Do nothing
        }
        try {
            return request.getHeader("Host")
        } catch (e: IllegalStateException) {
            // Do nothing
        }
        try {
            return URL(request.getHeader("Origin")).host
        } catch (e: IllegalStateException) {
            // Do nothing
        } catch (e: MalformedURLException) {
            // Do nothing
        }
        return "localhost"
    }

    fun getHost(dataFetchingEnvironment: DataFetchingEnvironment): String {
        val defaultContext = dataFetchingEnvironment.getContext<DefaultGraphQLServletContext>()
        val request = defaultContext.httpServletRequest;
        return getHost(request)
    }
}

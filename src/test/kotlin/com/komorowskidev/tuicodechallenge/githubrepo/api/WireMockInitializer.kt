package com.komorowskidev.tuicodechallenge.githubrepo.api

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class WireMockInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        val githubApiMock = GithubApiMock()
        configurableApplicationContext.beanFactory.registerSingleton("GithubApiMock", githubApiMock)
        TestPropertyValues.of("github.baseUrl=${githubApiMock.baseUrl}")
            .applyTo(configurableApplicationContext.environment)
    }
}

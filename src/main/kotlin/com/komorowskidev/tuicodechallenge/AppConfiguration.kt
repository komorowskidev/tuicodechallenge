package com.komorowskidev.tuicodechallenge

import com.komorowskidev.tuicodechallenge.githubrepo.GithubConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GithubConfiguration::class)
class AppConfiguration {
}
package com.example.goiapi

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Network::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
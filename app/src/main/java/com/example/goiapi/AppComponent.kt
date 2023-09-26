package com.example.goiapi

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
@Component(
    modules = [Network::class]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        //fun inject(activity: MainActivity)
    }
}
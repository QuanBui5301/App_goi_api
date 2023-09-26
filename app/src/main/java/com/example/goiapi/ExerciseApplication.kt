package com.example.goiapi

import android.app.Application

class ExerciseApplication : Application() {
    val appComponent = DaggerAppComponent.builder()
}
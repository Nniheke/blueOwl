package com.iheke.ispy

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * The `ISpyApplication` class is an Android `Application` subclass annotated with `@HiltAndroidApp`.
 * This annotation is part of the Hilt dependency injection framework, which integrates Dagger for Android
 * to simplify dependency injection in Android applications.
 *
 * The `ISpyApplication` class serves as the entry point of the application and represents the main application class.
 * It is responsible for initializing essential components and configurations when the application starts.
 * By annotating the class with `@HiltAndroidApp`, Hilt generates the necessary components and sets up
 * dependency injection for the application.
 *
 * The `ISpyApplication` class enables Hilt to create a base component that acts as the application-level component.
 * This component can be used to provide dependencies throughout the application.
 */
@HiltAndroidApp
class ISpyApplication : Application()
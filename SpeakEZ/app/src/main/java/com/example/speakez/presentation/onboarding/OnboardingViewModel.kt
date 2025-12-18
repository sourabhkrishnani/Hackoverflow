package com.example.speakez.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    // Ideally, save this to DataStore. For MVP, we pass it via Nav or just keep it simple.
    // Logic handled in UI for this simple pass-through.
}

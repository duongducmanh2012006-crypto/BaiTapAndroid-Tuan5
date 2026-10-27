package com.example.comuthtodo.model

import com.example.comuthtodo.ui.illustration.Illustration

/** Một trang Onboarding. Hình minh họa là một đối tượng Illustration (đa hình). */
data class OnboardingPage(
    val id: Int,
    val title: String,
    val description: String,
    val buttonText: String,
    val illustration: Illustration
)

package com.example.comuthtodo.model

/** Thông tin chung của ứng dụng (dữ liệu dạng đối tượng). */
data class AppInfo(
    val name: String,
    val tagline: String,
    val version: String
) {
    val versionLabel: String get() = "Phiên bản $version"
}

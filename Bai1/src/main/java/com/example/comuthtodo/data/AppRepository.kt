package com.example.comuthtodo.data

import com.example.comuthtodo.model.AppInfo
import com.example.comuthtodo.model.OnboardingPage
import com.example.comuthtodo.ui.illustration.CategoryIllustration
import com.example.comuthtodo.ui.illustration.ReminderIllustration
import com.example.comuthtodo.ui.illustration.TaskListIllustration

/** Nguồn dữ liệu duy nhất của app: toàn bộ là đối tượng, không hard-code trong UI. */
object AppRepository {

    val appInfo = AppInfo(
        name = "UTH Todo",
        tagline = "Việc học, việc nhà – gọn gàng mỗi ngày",
        version = "2.1.0"
    )

    val onboardingPages: List<OnboardingPage> = listOf(
        OnboardingPage(
            id = 1,
            title = "Mọi việc, gọn trong một chỗ",
            description = "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.",
            buttonText = "Tiếp tục",
            illustration = TaskListIllustration()
        ),
        OnboardingPage(
            id = 2,
            title = "Chia theo Học tập và Cuộc sống",
            description = "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì.",
            buttonText = "Tiếp tục",
            illustration = CategoryIllustration()
        ),
        OnboardingPage(
            id = 3,
            title = "Nhắc đúng lúc",
            description = "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
            buttonText = "Bắt đầu ngay",
            illustration = ReminderIllustration()
        )
    )
}

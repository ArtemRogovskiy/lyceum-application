package controllers.models

import java.util.*

data class NewsModel(
    val createdBy: UUID,
    val title: String?,
    val message: String?,
    val prover: UUID?,
    val isApproved: Boolean,
    val notificationTypeId: UUID
)

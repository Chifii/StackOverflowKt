package chifinaldo.stackoverflowkt.testutils

import chifinaldo.stackoverflowkt.badges.domain.models.BadgeType
import chifinaldo.stackoverflowkt.home.domain.models.User
import chifinaldo.stackoverflowkt.home.domain.models.UserList
import chifinaldo.stackoverflowkt.base.domain.domain.Result

val badgeTypeMock = BadgeType(
    bronze = 1,
    silver = 1,
    gold = 1
)

val homeUserMock = User(
    badgeType = badgeTypeMock,
    accountId = 1,
    isEmployee = true,
    lastModifiedDate = 1,
    lastAccessDate = 1,
    reputationChangeYear = 1,
    reputationChangeQuarter = 1,
    reputationChangeMonth = 1,
    reputationChangeWeek = 1,
    reputationChangeDay = 1,
    reputation = 1,
    creationDate = 1,
    userType = "userType",
    userId = 1,
    location = "location",
    websiteUrl = "websiteUrl",
    link = "link",
    profileImage = "profileImage",
    displayName = "displayName"
)

val userListMock = UserList(
    items = listOf(
        homeUserMock
    ),
    hasMore = false,
    backoff = 1,
    quotaMax = 1,
    quotaRemaining = 1
)

val genericResultSuccessMock = Result.Success(userListMock)
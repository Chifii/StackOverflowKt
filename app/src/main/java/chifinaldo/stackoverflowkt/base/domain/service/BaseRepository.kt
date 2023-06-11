package chifinaldo.stackoverflowkt.base.domain.service

import chifinaldo.stackoverflowkt.base.domain.service.RepositoryFactory.getRetrofit
import chifinaldo.stackoverflowkt.base.domain.service.RepositoryFactory.getRetrofitForLogin

open class BaseRepository<T>(
    service: Class<T>,
) {
    var service: T = getRetrofit()
        .create(service)

    var serviceForLogin: T = getRetrofitForLogin()
        .create(service)
}
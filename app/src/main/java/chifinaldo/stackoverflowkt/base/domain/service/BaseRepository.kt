package chifinaldo.stackoverflowkt.base.domain.service

import chifinaldo.stackoverflowkt.base.domain.service.RepositoryFactory.getRetrofit

open class BaseRepository<T>(
    service: Class<T>,
) {
    var service: T = getRetrofit()
        .create(service)
}
package com.example.recipemvvmandroidapp.domain.util

interface DTOMapper <T, DomainModel>{
    fun mapDomainModelToDTO(domainModel: DomainModel): T
    fun mapListDomainModelToListDTO(listDomainModel: List<DomainModel>): List<T>
}
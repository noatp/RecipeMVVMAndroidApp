package com.example.recipemvvmandroidapp.domain.util

interface DomainMapper <T, DomainModel>{
    fun mapToDomainModel(objectModel: T): DomainModel
    fun mapToListDomainModel(listObjectModel: List<T>): List<DomainModel>
}
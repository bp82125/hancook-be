package com.hancook.hancookbe.service

import com.hancook.hancookbe.model.DishType
import com.hancook.hancookbe.repository.DishTypeRepository
import jakarta.transaction.Transactional
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
@Transactional
class DishTypeServiceImpl (private val dishTypeRepository: DishTypeRepository) : DishTypeService {
    override fun getAllDishTypes(): List<DishType> {
        return dishTypeRepository.findAll()
    }

    override fun getDishTypeById(id: Long): DishType? {
        return dishTypeRepository.findById(id).orElse(null)
    }

    override fun createDishType(dishType: DishType): DishType {
        return dishTypeRepository.save(dishType)
    }

    override fun updateDishType(id: Long, updatedDishType: DishType): DishType? {
        return dishTypeRepository.findById(id).map {
            it.apply {
                dishTypeName = updatedDishType.dishTypeName
            }
        }.orElse(null)
    }

    override fun deleteDishType(id: Long): Boolean {
        return if (dishTypeRepository.existsById(id)) {
            dishTypeRepository.deleteById(id)
            true // Deletion successful
        } else {
            false // Dish with the given ID not found
        }
    }
}
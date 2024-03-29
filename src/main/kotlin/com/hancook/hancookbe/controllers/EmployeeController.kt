package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.RequestEmployeeDto
import com.hancook.hancookbe.dtos.ResponseEmployeeDto
import com.hancook.hancookbe.services.EmployeeService
import com.hancook.hancookbe.system.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("\${api.endpoint.base-url}/employees")
class EmployeeController(
    @Autowired private val employeeService: EmployeeService
) {

    @GetMapping("", "/")
    fun getAllEmployees(): ResponseEntity<ApiResponse<List<ResponseEmployeeDto>>> {
        val employees = employeeService.getAllEmployees()
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value(), data = employees, message = "Found employees"))
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: UUID): ResponseEntity<ApiResponse<ResponseEmployeeDto>> {
        val employee = employeeService.getEmployeeById(id)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = employee,
                message = "Found employee with ID: $id"
            )
        )
    }

    @PostMapping
    fun createEmployee(
        @Valid @RequestBody requestEmployee: RequestEmployeeDto
    ): ResponseEntity<ApiResponse<ResponseEmployeeDto>> {
        val employee = employeeService.createEmployee(requestEmployee)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    success = true,
                    statusCode = HttpStatus.CREATED.value(),
                    data = employee,
                    message = "Successfully created an employee"
                )
            )
    }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable id: UUID,
        @Valid @RequestBody requestEmployee: RequestEmployeeDto
    ): ResponseEntity<ApiResponse<ResponseEmployeeDto>> {
        val employee = employeeService.updateEmployee(id, requestEmployee)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = employee,
                message = "Updated success"
            )
        )
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(
        @PathVariable id: UUID,
    ): ResponseEntity<ApiResponse<Unit>> {
        employeeService.deleteEmployee(id)
        return ResponseEntity.ok(ApiResponse(success = true, statusCode = HttpStatus.OK.value(), message = "Employee has been deleted successfully"))
    }

    @PutMapping("/{employeeId}/accounts/{accountId}")
    fun assignAccountToEmployee(
        @PathVariable employeeId: UUID,
        @PathVariable accountId: UUID
    ): ResponseEntity<ApiResponse<ResponseEmployeeDto>> {
        val employee = employeeService.assignAccountToEmployee(employeeId, accountId)
        return ResponseEntity.ok(ApiResponse(success = true, data = employee, statusCode = HttpStatus.OK.value(), message = "Successfully assigned account $accountId to employee $employeeId"))
    }
}
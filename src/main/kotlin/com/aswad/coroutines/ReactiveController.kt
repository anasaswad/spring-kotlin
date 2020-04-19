package com.aswad.coroutines

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController("/")
class ReactiveController(private val inventoryRepository: InventoryRepository) {

    @GetMapping("/")
    fun findAll(): Flux<Inventory> = inventoryRepository.findAll()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): Mono<Inventory?> =
        inventoryRepository.findById(id)

}
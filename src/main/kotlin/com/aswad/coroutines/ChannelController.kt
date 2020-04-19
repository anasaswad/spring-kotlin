package com.aswad.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.collect
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

internal data class Request(val id: String, val callback: (Inventory) -> Unit) {
    suspend fun execute(inventoryRepository: InventoryRepository) {
        inventoryRepository.findById(id).collect { callback(it) }
    }
}

@RestController("/")
class ChannelController(private val inventoryRepository: InventoryRepository) {

    private val channel = Channel<Request>()
    private val fetchFlow: Flow<Request> = flow {
        for (request in channel) {
            emit(request)
        }
    }

    @PostConstruct
    fun postConstruct() {
        GlobalScope.launch {
            fetchFlow.collect { request -> request.execute(inventoryRepository) }
        }
        println("Fetch Collector started")
    }


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): Mono<Inventory> {
        return Mono.create { sink ->
            runBlocking {
                channel.send(Request(id) {
                    sink.success(it)
                })
            }
        }
    }

    @GetMapping("/")
    fun findAll(): Flux<Inventory> = inventoryRepository.findAll()


}
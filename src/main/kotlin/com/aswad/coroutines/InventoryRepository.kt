package com.aswad.coroutines

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface InventoryRepository : ReactiveMongoRepository<Inventory, String>
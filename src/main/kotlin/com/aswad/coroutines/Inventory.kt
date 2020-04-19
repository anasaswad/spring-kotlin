package com.aswad.coroutines

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Inventory(val _id: String, val item: String, val qty: Long, val tags: List<String>, val size: Size)

data class Size(val h: Int, val w: Int, val uom: String)
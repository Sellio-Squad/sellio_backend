package org.shangahi.sellio_backend.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "thrift_product")
@Entity
@DiscriminatorValue("THRIFT")
class ThriftProduct(
    val condition: String,
    val defects: String?,
    title: String,
    description: String?,
    mainImageURL: String?,
    price: Double,
    store: Store,

    ) : Product(
    title = title,
    description = description,
    mainImageURL = mainImageURL,
    price = price,
    store = store,
    isUsed = true,
    isFeatured = false,
)

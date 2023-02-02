package com.example.a1stzoomassignment.model.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository_db")
data class Repository(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var ownerName : String? = null,
    var repositoryName: String? = null,
    var repositoryUrl : String? = null,
    var description : String? = null
)

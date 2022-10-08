package com.flytbase.assignment.activity

import com.flytbase.assignment.supporting.DataModel
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class MemoryRef @Inject constructor() {
    var stack = ArrayList<DataModel>()
}
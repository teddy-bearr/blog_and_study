package com.example.chapter11.repository

import com.example.chapter11.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<Users, String> {
    fun findUserByUsername(username: String): Optional<Users>
}
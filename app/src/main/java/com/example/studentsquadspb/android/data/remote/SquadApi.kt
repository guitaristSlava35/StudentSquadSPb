package com.example.studentsquadspb.android.data.remote

import com.example.studentsquadspb.android.data.remote.dto.ProfileDto
import com.example.studentsquadspb.android.data.remote.dto.SquadDto
import com.example.studentsquadspb.android.data.remote.dto.SquadMemberDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SquadApi {

    @GET("squads")
    suspend fun getSquads(): List<SquadDto>

    @GET("squads/{id}")
    suspend fun getSquads(@Path("id") id: Int): SquadDto

    @GET("SquadMembers")
    suspend fun getSquadMembers(@Query("squadId") squadId: Int): List<SquadMemberDto>

    @GET("profiles/{id}")
    suspend fun getProfile(@Path("id") id: Int): ProfileDto
}
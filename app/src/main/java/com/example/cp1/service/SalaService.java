package com.example.cp1.service;

import com.example.cp1.Entity.Sala;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SalaService {

    @POST("sala")
    public Call<Sala> insertaSala(@Body Sala obj);


}


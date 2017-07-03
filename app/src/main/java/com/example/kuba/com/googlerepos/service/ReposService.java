package com.example.kuba.com.googlerepos.service;



import com.example.kuba.com.googlerepos.model.Repos;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public class ReposService {

    public interface ReposApi{

        @GET ("google/repos")
        Observable <List<Repos>> fetchRepos();

    }




}

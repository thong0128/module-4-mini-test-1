package com.codegym.service;

import com.codegym.model.Posts;


import java.util.List;

public interface IPostsService {
    List<Posts> findAll();

    void save(Posts posts);

    Posts findById(int id);

    void remove(int id);
    void update(Posts posts);
}

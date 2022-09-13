package com.example.wil.service;

import com.example.wil.model.Likes;
import com.example.wil.model.Post;
import com.example.wil.model.User;
import com.example.wil.repository.LikesRepository;
import com.example.wil.repository.PostRepository;
import com.example.wil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikesService {
    @Autowired
    private final LikesRepository likesRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;

    //좋아요 추가
    public boolean addLike(int userId, int postId) {
        System.out.println("Like Service >> addLike Method!!");
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        System.out.println("post: "+post);
        //좋아요 중복 체크
        if(isLike(user, post)){
            likesRepository.save(new Likes(user, post));
            return true;
        }
        return false;
    }

    //좋아요 중복 체크 로직
    private boolean isLike(User user, Post post) {
        System.out.println("isLike!!!");
        return likesRepository.findByUserIdAndPostId(user, post).isEmpty();
    }

    //좋아요 취소
    public void cancelLike(int userId, int postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Likes like = likesRepository.findByUserIdAndPostId(user, post).orElseThrow();
        likesRepository.delete(like);
    }

    //게시물당 좋아요 count
    public int countLike(int postId){
        Post post = postRepository.findById(postId).orElseThrow();
        System.out.println(postId);
        System.out.println(post);
        return likesRepository.countByPostId(post);
    }
}
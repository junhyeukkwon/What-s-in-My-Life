package com.example.wil.controller;

import com.example.wil.DTO.LikesDTO;
import com.example.wil.DTO.PostDTO;
import com.example.wil.config.jwt.TokenProvider;
import com.example.wil.service.LikesService;
import com.example.wil.service.PostService;
import com.example.wil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikesService likesService;

    private final TokenProvider tokenProvider;

    private final PostService postService;

    private final UserService userService;

//    // 좋아요 등록
//    @Transactional
//    @PostMapping("/like")
//    public boolean addLike(@RequestBody LikesDTO likesDTO)
//    {
//        System.out.println(">>Likes Controller ");
//        System.out.println(likesDTO.getPostId());
//        System.out.println(likesDTO.getUserId());
//        int userId = likesDTO.getUserId();
//        int postId = likesDTO.getPostId();
//
//        //if (user session 정보가 있을때 (로그인 했을때)){ // user의 세션을 가지고 와서 session 정보와 맞을때 수행할 수 있도록 변경 필요
//        boolean result = likesService.addLike(userId, postId);
//        //}
//        return result;
//    }

    // 좋아요 등록
    @Transactional
    @PostMapping("/like/{token}")
    public boolean addLike(@PathVariable String token, @RequestBody LikesDTO likesDTO)
    {
        System.out.println("/like/{token} postmapping");
        if (tokenProvider.validateToken(token)) {
            System.out.println("/like/{token} postmapping tokenProvider.validate = true");
            Integer userId = tokenProvider.getUserIdFromToken(token);
            System.out.println(">>Likes Controller ");
            System.out.println(likesDTO.getPostId());
            System.out.println(likesDTO.getUserId());
            int postId = likesDTO.getPostId();

            //if (user session 정보가 있을때 (로그인 했을때)){ // user의 세션을 가지고 와서 session 정보와 맞을때 수행할 수 있도록 변경 필요
            boolean result = likesService.addLike(userId, postId);
            //}
            return result;
        } else {
            return false;
        }
    }

    // 좋아요 취소
//    @DeleteMapping("/like/{token}")
//    public ResponseEntity<String> unLike(@PathVariable String token, @RequestBody LikesDTO likesDTO)
//    {
//        System.out.println("/like/{token} deletemapping");
//        if (tokenProvider.validateToken(token)) {
//            System.out.println("/like/{token} deletemapping tokenProvider.validate = true");
//            int userId = tokenProvider.getUserIdFromToken(token);
//            int postId = likesDTO.getPostId();
//            likesService.cancelLike(userId, postId);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    // axios에서
    // HttpMessageNotReadableException: Required request body is missing
    // 문제때문에 body 삭제하고 Pathvariable로 전달
    @DeleteMapping("/like/{token}/{postId}")
    public boolean unLike(@PathVariable String token, @PathVariable Integer postId)
    {
        System.out.println("/like/{token} deletemapping");
        if (tokenProvider.validateToken(token)) {
            System.out.println("/like/{token} deletemapping tokenProvider.validate = true");
            int userId = tokenProvider.getUserIdFromToken(token);
            likesService.cancelLike(userId, postId);
            return true;
        } else {
            return false;
        }
    }

    // 포스트당 좋아요 조회
    @GetMapping("/like/{postId}")
    public int countLike(@PathVariable int postId){
        return likesService.countLike(postId);
    }

    // 유저의 포스트당 좋아요 조회
    @GetMapping("/like/user/{token}")
    public int countLikesByUser(@PathVariable String token){

        System.out.println("/like/user/{token} getmapping");
        if (tokenProvider.validateToken(token)) {
            System.out.println("/like/user/{token} getmapping tokenProvider.validate = true");
            Integer userId = tokenProvider.getUserIdFromToken(token);
            return likesService.countLikesByUser(userId);
        } else {
            return 0;
        }
    }

    // 포스트당 좋아요를 누른 유저 id 리스트 조회
    @GetMapping("/like/users/{postId}")
    public List<Integer> getUserIdList(@PathVariable int postId){
        return likesService.getUserIdList(postId);
    }


    // 유저가 좋아요 누른 게시물 id 리스트
    @GetMapping("like/user/post/{token}")
    public List<Integer> getPostIdList(@PathVariable String token) {
        System.out.println("/like/user/post/{token} getmapping");
        if (tokenProvider.validateToken(token)) {
            System.out.println("/like/user/post/{token} getmapping tokenProvider.validate = true");
            Integer userId = tokenProvider.getUserIdFromToken(token);
            return likesService.getPostIdList(userId);
        } else {
            return null;
        }
    }

    //인기 게시물 Top 5 조회
    @GetMapping("/like/top_post")
    public List<PostDTO> topLike() {
        return postService.topLike();
    }
}
package com.example.activityserver.service;

import static com.example.activityserver.common.response.BaseResponseStatus.POST_ID_INVALID;

import com.example.activityserver.common.exceptions.BaseException;
import com.example.activityserver.domain.ActiveType;
import com.example.activityserver.domain.dto.request.CreatePostReq;
import com.example.activityserver.domain.dto.response.CreatePostRes;
import com.example.activityserver.domain.dto.response.PostDto;
import com.example.activityserver.domain.entity.LikePost;
import com.example.activityserver.domain.entity.Post;
import com.example.activityserver.domain.entity.UserLog;
import com.example.activityserver.repository.LikePostRepository;
import com.example.activityserver.repository.PostRepository;
import com.example.activityserver.repository.UserLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;
    private final UserLogRepository userLogRepository;

    public CreatePostRes createPost(String userId, CreatePostReq createPostReq) {

        Post buildPost = Post.builder()
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .userId(userId)
                .build();

        Post post = postRepository.save(buildPost);

        UserLog userLog = UserLog.builder()
                .actor(userId)
                .recipient(post.getUserId())
                .activeType(ActiveType.WRITE_POST)
                .build();

        userLogRepository.save(userLog);

        return CreatePostRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUserId())
                .build();

    }
    public String likePost(String userId, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->new BaseException(POST_ID_INVALID));


        Optional<LikePost> byUserAndPost = likePostRepository.findByUserIdAndPost(userId, post);

        if (!byUserAndPost.isPresent()) {

            LikePost likePost = LikePost.builder()
                    .userId(userId)
                    .post(post)
                    .build();

            likePostRepository.save(likePost);

            UserLog userLog = UserLog.builder()
                    .actor(userId)
                    .recipient(post.getUserId())
                    .activeType(ActiveType.LIKE_POST)
                    .build();

            userLogRepository.save(userLog);

            return "해당 글에 좋아요를 완료했습니다.";

        }
        // todo : 나중에 state 상태 변경으로 바꾸기.
        likePostRepository.delete(byUserAndPost.get());


        UserLog userLog = UserLog.builder()
                .actor(userId)
                .recipient(post.getUserId())
                .activeType(ActiveType.CANCEL_LIKE_POST)
                .build();

        userLogRepository.save(userLog);

        return "해당 글에 좋아요를 취소했습니다.";

    }

    public List<PostDto> getMyPostList(String userId){
        List<Post> posts = postRepository.findAllByUserId(userId);
        List<PostDto> postDtos = posts.stream().map(PostDto::of).collect(Collectors.toList());
        return postDtos;

    }
    public List<PostDto> getMyLikePostList(String userId) {

        List<LikePost> likePosts = likePostRepository.findAllByUserId(userId);
        List<Long> postIds = likePosts.stream().map(p -> p.getPost().getId()).collect(Collectors.toList());
        // 포스트 id 에 속하는 post 리스트 가져옴.
        List<Post> posts = postRepository.findAllById(postIds);

        return posts.stream().map(PostDto::of).collect(Collectors.toList());
    }


}

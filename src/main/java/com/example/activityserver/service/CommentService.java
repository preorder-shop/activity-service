package com.example.activityserver.service;

import static com.example.activityserver.common.response.BaseResponseStatus.COMMENT_ID_INVALID;
import static com.example.activityserver.common.response.BaseResponseStatus.POST_ID_INVALID;

import com.example.activityserver.common.exceptions.BaseException;
import com.example.activityserver.common.util.JwtUtil;
import com.example.activityserver.domain.ActiveType;
import com.example.activityserver.domain.dto.request.CreateCommentReq;
import com.example.activityserver.domain.dto.response.CommentDto;
import com.example.activityserver.domain.dto.response.CreateCommentRes;
import com.example.activityserver.domain.entity.Comment;
import com.example.activityserver.domain.entity.LikeComment;
import com.example.activityserver.domain.entity.Post;
import com.example.activityserver.domain.entity.UserLog;
import com.example.activityserver.repository.CommentRepository;
import com.example.activityserver.repository.LikeCommentRepository;
import com.example.activityserver.repository.PostRepository;
import com.example.activityserver.repository.UserLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final UserLogRepository userLogRepository;

    public CreateCommentRes createComment(String userId, CreateCommentReq createCommentReq, Long postId) {

        // 댓글을 작성할 포스트의 아이디가 제대로 된건지 검증.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        Comment build = Comment.builder()
                .content(createCommentReq.getContent()) // 댓글 내용
                .userId(userId)  // 댓글 작성자
                .post(post)
                .build();

        Comment comment = commentRepository.save(build);

        UserLog userLog = UserLog.builder()
                .actor(userId)
                .recipient(post.getUserId())
                .activeType(ActiveType.WRITE_COMMENT)
                .build();

        userLogRepository.save(userLog);

        return CreateCommentRes.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .writer(comment.getUserId())
                .postId(comment.getPost().getId())
                .build();

    }

    public String likeComment(String userId, Long commentId) {


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(COMMENT_ID_INVALID));


        Optional<LikeComment> byUserAndComment = likeCommentRepository.findByUserIdAndComment(userId, comment);

        if (!byUserAndComment.isPresent()) {

            LikeComment likeComment = LikeComment.builder()
                    .userId(userId)
                    .comment(comment)
                    .build();

            likeCommentRepository.save(likeComment);


            UserLog userLog = UserLog.builder()
                    .actor(userId)
                    .recipient(comment.getUserId())
                    .activeType(ActiveType.LIKE_COMMENT)
                    .build();

            userLogRepository.save(userLog);

            return "해당 댓글에 좋아요를 완료했습니다.";

        }

        // todo : 나중에 state 상태 변경으로 바꾸기.
        likeCommentRepository.delete(byUserAndComment.get());


        UserLog userLog = UserLog.builder()
                .actor(userId)
                .recipient(comment.getUserId())
                .activeType(ActiveType.CANCEL_LIKE_COMMENT)
                .build();

        userLogRepository.save(userLog);

        return "해당 댓글에 좋아요를 취소했습니다.";

    }


    public List<CommentDto> getMyCommentList(String userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        return comments.stream().map(CommentDto::of).collect(Collectors.toList());
    }

    public List<CommentDto> getMyLikeCommentList(String userId) {

        List<LikeComment> likeComments = likeCommentRepository.findAllByUserId(userId);
        List<Long> commentIds = likeComments.stream().map(lc -> lc.getComment().getId()).collect(Collectors.toList());

        List<Comment> comments = commentRepository.findAllById(commentIds);

        return comments.stream().map(CommentDto::of).collect(Collectors.toList());
    }
}

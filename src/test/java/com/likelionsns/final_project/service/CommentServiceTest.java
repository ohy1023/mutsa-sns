//package com.likelionsns.final_project.service;
//
//import com.likelionsns.final_project.domain.entity.Comment;
//import com.likelionsns.final_project.domain.entity.Post;
//import com.likelionsns.final_project.domain.entity.User;
//import com.likelionsns.final_project.domain.request.CommentUpdateRequest;
//import com.likelionsns.final_project.domain.response.CommentUpdateResponse;
//import com.likelionsns.final_project.exception.SnsAppException;
//import com.likelionsns.final_project.fixture.CommentInfoFixture;
//import com.likelionsns.final_project.fixture.PostInfoFixture;
//import com.likelionsns.final_project.fixture.UserInfoFixture;
//import com.likelionsns.final_project.repository.CommentRepository;
//import com.likelionsns.final_project.repository.PostRepository;
//import com.likelionsns.final_project.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static com.likelionsns.final_project.exception.ErrorCode.*;
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class CommentServiceTest {
//    @InjectMocks
//    private CommentService commentService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PostRepository postRepository;
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    User user = UserInfoFixture.get("user1", "password1");
//    User user2 = UserInfoFixture.get("user2", "password2");
//    Post post = PostInfoFixture.get(user.getUserName(), user.getPassword());
//    Comment comment = CommentInfoFixture.get(user.getUserName(), user.getPassword());
//
//    @Test
//    @DisplayName("댓글 수정 성공")
//    void updateComment() {
//        // given
//        CommentUpdateRequest updateRequest = new CommentUpdateRequest("updated comment");
//
//        given(userRepository.findByUserName(user.getUserName()))
//                .willReturn(Optional.of(user));
//
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        // when
//        CommentUpdateResponse updateResponse = commentService.updateComment(post.getId(), comment.getId(), updateRequest, user.getUserName());
//
//        // then
//        assertThat(updateResponse.getComment()).isEqualTo(updateRequest.getComment());
//
//    }
//
//    @Test
//    @DisplayName("댓글 수정 실패(1) : 포스트 존재하지 않음")
//    void updateCommentFail01() {
//        // given
//        CommentUpdateRequest updateRequest = new CommentUpdateRequest("updated comment");
//
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.empty());
//
//
//        // when & then
//        assertThatThrownBy(() -> commentService.updateComment(post.getId(), comment.getId(), updateRequest, user.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(POST_NOT_FOUND.getMessage());
//    }
//
//    @Test
//    @DisplayName("댓글 수정 실패(2) : 작성자!=유저")
//    void updateCommentFail02() {
//        // given
//        CommentUpdateRequest updateRequest = new CommentUpdateRequest("updated comment");
//
//        given(userRepository.findByUserName(user2.getUserName()))
//                .willReturn(Optional.of(user2));
//
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        // when & then
//        assertThatThrownBy(() -> commentService.updateComment(post.getId(), comment.getId(), updateRequest, user2.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(INVALID_PERMISSION.getMessage());
//    }
//
//    @Test
//    @DisplayName("댓글 수정 실패(3) : 유저 존재하지 않음")
//    void updateCommentFail03() {
//        // given
//        CommentUpdateRequest updateRequest = new CommentUpdateRequest("updated comment");
//
//        given(userRepository.findByUserName(user.getUserName()))
//                .willReturn(Optional.empty());
//
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        // when & then
//        assertThatThrownBy(() -> commentService.updateComment(post.getId(), comment.getId(), updateRequest, user.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(USERNAME_NOT_FOUND.getMessage());
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 성공")
//    void deleteComment() {
//        // given
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        given(userRepository.findByUserName(user.getUserName()))
//                .willReturn(Optional.of(user));
//
//        // when
//        boolean b = commentService.deleteComment(post.getId(), comment.getId(), user.getUserName());
//
//        // then
//        assertThat(b).isTrue();
//
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 실패 : 포스트 존재하지 않음")
//    void deleteCommentFail01() {
//        // given
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.empty());
//        // when & then
//        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), comment.getId(), user.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(POST_NOT_FOUND.getMessage());
//
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 실패 : 댓글 존재하지 않음")
//    void deleteCommentFail02() {
//        // given
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.empty());
//
//        // when & then
//        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), comment.getId(), user.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(COMMENT_NOT_FOUND.getMessage());
//
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 실패 : 유저 존재하지 않음")
//    void deleteCommentFail03() {
//        // given
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        given(userRepository.findByUserName(user.getUserName()))
//                .willReturn(Optional.empty());
//
//        // when & then
//        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), comment.getId(), user.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(USERNAME_NOT_FOUND.getMessage());
//
//    }
//
//    @Test
//    @DisplayName("댓글 삭제 실패 : 작성자!=유저")
//    void deleteCommentFail04() {
//        // given
//        given(postRepository.findById(post.getId()))
//                .willReturn(Optional.of(post));
//
//        given(commentRepository.findById(comment.getId()))
//                .willReturn(Optional.of(comment));
//
//        given(userRepository.findByUserName(user2.getUserName()))
//                .willReturn(Optional.of(user2));
//
//        // when & then
//        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), comment.getId(), user2.getUserName()))
//                .isExactlyInstanceOf(SnsAppException.class)
//                .hasMessage(INVALID_PERMISSION.getMessage());
//
//    }
//}
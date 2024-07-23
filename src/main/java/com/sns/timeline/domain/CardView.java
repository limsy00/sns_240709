package com.sns.timeline.domain;

import java.util.List;

import com.sns.comment.domain.CommentView;
import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

// 글 1개와 mapping 
// 화면에 뿌리기 위한 객체이기 때문에 이름에 view 붙임
@Data
@ToString
public class CardView { // 카드 1개는 글(PostEntity), 글쓴이(UserEntity), 댓글목록(CommentView), 좋아요가 필요함으로 아래와 같이 참조한다.

	// 글 1개
	private PostEntity post; 
	// 글쓴이 정보
	private UserEntity user; 
	
	// 댓글 N개
	private List<CommentView> commentList;
	
	// 좋아요 N개
	private int likeCount;
	// 좋아요 클릭 여부
	private boolean filledLike; // 채워졌으면 홀수
}

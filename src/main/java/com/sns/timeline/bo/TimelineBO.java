package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;

	// input:X (좋아요 여부 채우기)-> userId
	// output: List<CardView> *generate : 가공하는 메소드
	public List<CardView> generateCardViewList(Integer userId) { // 비로그인도 타임라인 보여지므로 null 가능
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록 가져오기 : List<PostEntity>
		List<PostEntity> postList = postBO.getPostEntityList(); // postList-빨간색
		
		// 글 목록 반복문 순회 : PostEntity -> CardView => cardViewList
		for (PostEntity post : postList) { // 글 각각을 순회
			
			CardView card = new CardView(); // card-보라색
			
			// 글을 cardViewList에 넣기
			card.setPost(post);
			
			// 글쓴이 
			UserEntity user = userBO.getUserEntityById(post.getUserId()); // card.getPost().getUserId(); (글 안에 글쓴이번호 존재) 
			card.setUser(user); 
			
			// 글1 : 댓글 N개 -> (모든 댓글X) 글번호에 해당하는 댓글들만 가져오기
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			// 댓글 카드에 넣기
			card.setCommentList(commentViewList);
			
			// 좋아요 개수
			int likeCount = likeBO.getLikeCountByPostId(post.getId()); // postId = post.getId()의 PK로 조회
			card.setLikeCount(likeCount);
			
			// 좋아요 여부 채우기
			/* likeMapper 부를 수 X, 메소드 새로 만들기
			 * userId는 session에 있음 */
			card.setFilledLike(likeBO.filledLikeByPostIdUserId(post.getId(), userId));
			
			// 반드시 (card-보라색) cardViewList 에 넣기★★★
			cardViewList.add(card);
		}
		
		return cardViewList;
	}
}

package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	// input: postId, userId, content
	// output: X
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
	
	// input:글번호    
	// output:List<CommentView>
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글들 가져오기
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회 : Comment -> CommentView   => list에 담기
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);
			
			// 댓글쓰니
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			// => list에 담기 ★★★
			commentViewList.add(commentView);
		}
		return commentViewList;
	}
	
	// 댓글 삭제
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}
}
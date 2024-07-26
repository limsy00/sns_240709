package com.sns.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sns.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	public int insertComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("content") String content);
	
	public List<Comment> selectCommentList();
	
	public List<Comment> selectCommentListByPostId(int postId); 
	
	public void deleteCommentById(int id); // 댓삭
	
	public void deleteCommentsByPostId(int postId); // 글 삭제시 (글 번호에 대하여) 모든 댓글 삭제
}





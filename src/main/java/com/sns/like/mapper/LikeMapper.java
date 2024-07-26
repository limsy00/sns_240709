package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

	/*
	 * public int selectLikeCountByPostIdUserId( // 사용하는 파일 보기:ctrl+alt+h
	 * 
	 * @Param("postId") int postId,
	 * 
	 * @Param("userId") int userId);
	 * 
	 * public int selectLikeCountByPostId(int postId);
	 */
	
	// => 카운트 쿼리 하나로 합치기
	public int selectLikeCountByPostIdOrUserId(
			@Param("postId") int postId,
			@Param("userId") Integer userId); // null 허용
	
	
	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void deleteLikeByPostId(int postId);
}
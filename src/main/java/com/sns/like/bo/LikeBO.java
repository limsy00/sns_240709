package com.sns.like.bo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

/* 내가 {postId}번 게시글에 좋아요를 눌렀는가 확인
 * -> 눌렀으면 삭제(1 -> 0) 
 * -> 안 눌렀으면 추가(0 -> 1) */
@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;

	// input: postId, userId
	// output: X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
		
		// 삭제/추가 여부
		if (count > 0) { // 채워져 있으면 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else { // 비워져 있으면 추가
			likeMapper.insertLike(postId, userId);
		}
	}

	// input: postId, userId
	// output: int
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}
	
	
	// input: postId
	// output: int(좋아요 개수)
	public int getLikeCountByPostId(int postId) { // 가공 필요없이 행 갯수 리턴
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// 좋아요 채움 여부 로직 
	// input: postId(필수), userId(로그인/비로그인)
	// output: boolean (채울지 여부)
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		// 1) 비로그인이면 DB 조회 없이 하트 채우지XX
		if (userId == null) {
			return false;
		}
		
		// 로그인이면  2) 행 존재(1) true  3) 없으면(0) false
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) == 1 ? true : false;
	}
	
	// 글번호 삭제 시, 글에 대한 모든 좋아요 삭제
	public void deleteLikeByPostId(int postId) {
		likeMapper.deleteLikeByPostId(postId);
	}
}

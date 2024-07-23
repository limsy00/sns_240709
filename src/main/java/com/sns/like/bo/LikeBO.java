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
		int count = likeMapper.selectLikeCountByPostIdUserId(postId, userId);
		
		// 삭제/추가 여부
		if (count > 0) { // 채워져 있으면 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else { // 비워져 있으면 추가
			likeMapper.insertLike(postId, userId);
		}
	}
}

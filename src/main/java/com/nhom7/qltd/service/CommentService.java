package com.nhom7.qltd.service;

import com.nhom7.qltd.model.Comment;
import com.nhom7.qltd.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public void save(Comment comment) {
        commentRepository.save(comment);
    }
    public  Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
    public List<Comment> getAllCommentByNewsId(Integer newsId) {
        return commentRepository.findAllByNewsIdOrderByTimeUploadDesc(newsId);
    }
}

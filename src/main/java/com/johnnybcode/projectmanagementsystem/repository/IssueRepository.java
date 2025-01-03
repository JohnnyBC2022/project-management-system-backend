package com.johnnybcode.projectmanagementsystem.repository;

import com.johnnybcode.projectmanagementsystem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> findByProjectID(Long id);
}

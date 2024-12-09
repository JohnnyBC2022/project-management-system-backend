package com.johnnybcode.projectmanagementsystem.service;

import com.johnnybcode.projectmanagementsystem.model.Issue;
import com.johnnybcode.projectmanagementsystem.model.User;
import com.johnnybcode.projectmanagementsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Optional<Issue> getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, Long userId) throws Exception;

    Optional<Issue> updateIssue(Long issueId, IssueRequest updatedIssue, Long userId) throws Exception;

    String deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;


}

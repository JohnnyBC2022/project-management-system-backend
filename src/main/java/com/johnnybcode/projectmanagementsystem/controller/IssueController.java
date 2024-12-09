package com.johnnybcode.projectmanagementsystem.controller;

import com.johnnybcode.projectmanagementsystem.model.Issue;
import com.johnnybcode.projectmanagementsystem.model.IssueDTO;
import com.johnnybcode.projectmanagementsystem.model.Project;
import com.johnnybcode.projectmanagementsystem.model.User;
import com.johnnybcode.projectmanagementsystem.request.IssueRequest;
import com.johnnybcode.projectmanagementsystem.response.AuthResponse;
import com.johnnybcode.projectmanagementsystem.response.MessageResponse;
import com.johnnybcode.projectmanagementsystem.service.IssueService;
import com.johnnybcode.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok((issueService.getIssueByProjectId(projectId)));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest,
                                                @RequestHeader("Authorization") String token)
            throws Exception {

        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issueRequest, tokenUser);
        IssueDTO issueDTO = new IssueDTO();

        issueDTO.setId(createdIssue.getId());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setProjectId(createdIssue.getProjectID());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                       @RequestHeader("Authorization") String token)
            throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Asunto borrado");

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                @PathVariable Long userId)
            throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long issueId,
                                                   @PathVariable String status
    )
            throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}

package com.trip.tripjava.controller;

import com.trip.tripjava.entity.ChecklistEntity;
import com.trip.tripjava.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Import statement added

@RestController
public class ChecklistController {
    @Autowired
    ChecklistService checklistService;

    // 체크리스트 확인
    @GetMapping("planner/checklist/select/{planner_no}")
    public List<ChecklistEntity> getAllChecklists(@PathVariable("planner_no") long planner_no) {
        return checklistService.getAllChecklists(planner_no);
    }

    // 체크리스트 저장
    @PostMapping("planner/checklist/add/{planner_no}")
    public ChecklistEntity createChecklistData(@PathVariable("planner_no") long planner_no ,@RequestBody ChecklistEntity checklist) {
        return checklistService.addChecklist(planner_no, checklist);
    }

    // 체크리스트 수정
    @PutMapping("planner/checklist/{checklistId}")
    public ChecklistEntity updateChecklistData(@PathVariable("checklistId") long checklistId, @RequestBody ChecklistEntity checklist) {
        checklist.setChecklist_no(checklistId);
        return checklistService.updateChecklist(checklist);
    }

    // 체크리스트 삭제
    @DeleteMapping("/planner/checklist/{checklistId}")
    public void deleteChecklistData(@PathVariable("checklistId") long checklistId) {
        checklistService.deleteChecklist(checklistId);
    }
}

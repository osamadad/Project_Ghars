package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.FieldDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.FieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/add")
    public ResponseEntity<?> addField(@AuthenticationPrincipal User user, @RequestBody @Valid FieldDTOIn fieldDTOIn) {
        fieldService.addField(user.getId(), fieldDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Field added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllFields() {
        return ResponseEntity.status(200).body(fieldService.getAllFields());
    }

    @GetMapping("/get-by-farm/{farmId}")
    public ResponseEntity<?> getFieldsByFarm(@AuthenticationPrincipal User user, @PathVariable Integer farmId) {
        return ResponseEntity.status(200).body(fieldService.getMyFieldsByFarm(user.getId(), farmId));
    }

    @PutMapping("/update/{fieldId}")
    public ResponseEntity<?> updateField(@AuthenticationPrincipal User user, @PathVariable Integer fieldId, @RequestBody @Valid FieldDTOIn fieldDTOIn) {
        fieldService.updateField(user.getId(), fieldId, fieldDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Field updated successfully"));
    }

    @DeleteMapping("/delete/{fieldId}")
    public ResponseEntity<?> deleteField(@AuthenticationPrincipal User user, @PathVariable Integer fieldId) {
        fieldService.deleteField(user.getId(), fieldId);
        return ResponseEntity.status(200).body(new ApiResponse("Field deleted successfully"));
    }
}

package com.vasd.medical_service.upload.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.upload.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
@Tag(name = "Upload controller", description = "APIs for uploading images to Cloudinary with folder organization")
public class UploadController {

    private final CloudinaryService cloudinaryService;

    @Operation(
            summary = "Upload an image file to a specified folder",
            description = "Upload a single image file to the given folder on Cloudinary and returns the uploaded image URL."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Image uploaded successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or missing file",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "999", description = "Internal server error during upload",
                    content = @Content)
    })
    @PostMapping("/{folder}")
    public ApiResponse<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("folder") String folder) {


        if (file.isEmpty()) {
            return ApiResponse.<Map<String, Object>>builder()
                    .code(400)
                    .message("Invalid input or missing file")
                    .build();
        }

        try {
            String url = cloudinaryService.uploadImage(file, folder);
            Map<String, Object> result = new HashMap<>();
            result.put("url", url);
            return ApiResponse.<Map<String, Object>>builder()
                    .result(result)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Map<String, Object>>builder()
                    .code(999)
                    .message(e.getMessage())
                    .build();
        }
    }
    @Operation(
            summary = "Upload multiple image files to a specified folder",
            description = "Upload multiple image files to the given folder on Cloudinary and return a list of uploaded image URLs."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Images uploaded successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or no files provided",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "999", description = "Internal server error during upload",
                    content = @Content)
    })
    @PostMapping("/multiple/{folder}")
    public ApiResponse<Map<String, Object>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @PathVariable("folder") String folder) {

        if (files == null || files.length == 0) {
            return ApiResponse.<Map<String, Object>>builder()
                    .code(400)
                    .message("No files provided")
                    .build();
        }

        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    urls.add(cloudinaryService.uploadImage(file, folder));
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("urls", urls);
            return ApiResponse.<Map<String, Object>>builder()
                    .result(result)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Map<String, Object>>builder()
                    .code(999)
                    .message(e.getMessage())
                    .build();
        }
    }

}

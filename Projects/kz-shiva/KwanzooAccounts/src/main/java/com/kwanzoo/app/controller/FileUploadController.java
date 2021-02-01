package com.kwanzoo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kwanzoo.app.service.StorageService;

@RestController
public class FileUploadController {

	@Autowired
	StorageService storageService;

	@PostMapping("/upload-accounts")
	public Object handleFileUpload(@RequestParam("file") MultipartFile file) {

		return storageService.store(file);
	}

}

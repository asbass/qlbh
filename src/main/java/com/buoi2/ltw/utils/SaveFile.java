package com.buoi2.ltw.utils;

import java.io.File;
import java.io.IOException;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SaveFile {
	
	@Autowired
	HttpServletRequest request;
	
	public File save(MultipartFile file, String path) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
			File dir = new File(request.getServletContext().getRealPath(path));
			if(!dir.exists()) {
				dir.mkdir();
			}
			File saveFile = new File(dir, file.getOriginalFilename());
			file.transferTo(saveFile);
			return saveFile;
		}
		return null;
	}
}

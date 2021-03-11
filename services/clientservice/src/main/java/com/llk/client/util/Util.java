package com.llk.client.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



public class Util {
	private Util() {};
	public static String imgDownloadUri(String fileName) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(Constants.API_VERSION+Constants.IMG_UPLOAD_PATH)
				.path(fileName).toUriString();
		return fileDownloadUri;
	}

	public static String saveProfileImage(MultipartFile profileImg) throws IOException {
		String avatarUrl = "";
		if (profileImg != null) {
			String fileName = com.llk.common.util.Util
					.getImageName(StringUtils.cleanPath(profileImg.getOriginalFilename()));
			Files.copy(profileImg.getInputStream(), Paths.get(Constants.IMG_UPLOAD_PATH + fileName),
					StandardCopyOption.REPLACE_EXISTING);
			avatarUrl = "/client"+Constants.API_VERSION+Constants.IMG_UPLOAD_PATH +fileName;
		}
		return avatarUrl;
	}
	public static ActiveMQQueue getMqueue() {
		return new ActiveMQQueue(com.llk.common.util.Constants.QUEUE_CLIENT);
	}
}

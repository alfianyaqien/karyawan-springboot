package com.alfian.test.controller.uploaddata;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Value("${app.uploadto.cdn}")
    private String UPLOADED_FOLDER;
    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/v1/upload", method = RequestMethod.POST,
            consumes = {"multipart/form-data", "application/json"})
    public UploadFileResponse uploadFile(@RequestParam("file")
                                         MultipartFile file) throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new
                SimpleDateFormat("ddMyyyyhhmmss");
        String strDate = formatter.format(date);
        String nameFormat =
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if (nameFormat.isEmpty()) {
            nameFormat = ".png";
        }
        String fileName = UPLOADED_FOLDER + strDate + nameFormat;
        String fileNameforDOwnload = strDate + nameFormat;
        Path TO = Paths.get(fileName);

        try {
            Files.copy(file.getInputStream(), TO);
        } catch (Exception e) {
            e.printStackTrace();
            return new UploadFileResponse(fileNameforDOwnload,
                    null,
                    file.getContentType(), file.getSize(),
                    e.getMessage());
        }
        String fileDownloadUri =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/v1/showFile/")
                        .path(fileNameforDOwnload)
                        .toUriString();

        return new UploadFileResponse(fileNameforDOwnload,
                fileDownloadUri,
                file.getContentType(), file.getSize(), "false");
    }

    @GetMapping("v1/showFile/{fileName:.+}")
    public ResponseEntity<Resource> showFile(@PathVariable String fileName, HttpServletRequest request) { // Load file as Resource :step 1 load path lokasi name file
        Resource resource =
                fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            System.out.println("resource.getFile().getAbsolutePath" +
                    resource.getFile().getAbsolutePath());
            contentType =
                    request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not  be determined
        if (contentType == null) {
            contentType = "application/octet-stream";// type .json
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private File multipartToFile(MultipartFile upload, String
            routeName) {
        String base = "";
        logger.info(String.format("Trying upload file: %s",
                upload.getOriginalFilename()));
        File file = new File(base + upload.getOriginalFilename());
        try {
            logger.info(String.format("Saving uploaded file to: '%s'", file.getAbsolutePath()));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(upload.getBytes());
            fos.close();
        } catch (IOException e) {
            logger.error(String.format("Error: POST|UPLOAD %s",
                    routeName), e);
        }
        return file;
    }

    private File multipartToFile(MultipartFile upload) {
        return multipartToFile(upload, UPLOADED_FOLDER);
    }
}

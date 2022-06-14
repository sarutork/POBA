package com.obu.tech.poba.utils.download;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Controller
@RequestMapping(path = "/download")
public class DownloadController {

    @Value("${poba.upload}")
    private String UPLOAD_PATH;

    private static String PDF_EXTENSION = ".pdf";
    @RequestMapping(path = "/download-pdf/{filePath}/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadPDFFile(@PathVariable String filePath, @PathVariable String fileName) throws IOException{
        /*String[] filenames = fileName.split("\\.");
        String extension = (filenames.length > 1 ? "."+filenames[1] : "");
        fileName = filenames[0];
        if(StringUtils.isNoneBlank(extension)) PDF_EXTENSION = extension;*/

        String filePathStr = UPLOAD_PATH+File.separator+filePath+File.separator+fileName;
        File file = new File(filePathStr);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

//    @RequestMapping(path = "/download-image/{filePath}/{fileName}", method = RequestMethod.GET)
//    public ResponseEntity<Resource> downloadImage(@PathVariable String filePath, @PathVariable String fileName) throws IOException {
//
//        String filePathStr = UPLOAD_PATH+File.separator+filePath+File.separator+fileName+PDF_EXTENSION;
//        File file = new File(filePathStr);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ fileName);
//
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//
//        Tika tika = new Tika();
//        String mimeType = tika.detect(file);
//
//        return ResponseEntity.ok().headers(headers).contentLength(file.length())
//                .contentType(MediaType.parseMediaType(mimeType)).body(resource);
//    }
}

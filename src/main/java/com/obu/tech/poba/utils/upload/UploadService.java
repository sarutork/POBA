package com.obu.tech.poba.utils.upload;

import com.obu.tech.poba.utils.exceptions.UploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UploadService {

    public static final String UPLOAD_GROUP_RESEARCHER = "researcher";
    public static final String UPLOAD_GROUP_THESIS = "thesis";
    public static final String UPLOAD_GROUP_RESOLUTION = "resolution";


    @Autowired
    private UploadRepository uploadRepository;

    public List<Upload> getByGroupAndReference(String group, Long reference) {
        return uploadRepository.getByGroupAndReference(group, reference);
    }

    public List<Upload> upload(MultipartFile[] files, String group, Long reference, String path) {
        List<Upload> uploadResult = new ArrayList<>();
        if (nonNull(files) && files.length > 0) {
            log.info("Uploading {} file(s):", files.length);
            for (MultipartFile file : files) {
                Upload upload = new Upload(group, reference);
                uploadRepository.saveAndFlush(upload); // generate upload_id
                Optional<Upload> result = moveFile(file, path, upload);
                result.ifPresent(uploadResult::add);
            }
            log.info("Uploaded {}/{} file(s)", uploadResult.size(), files.length);
        }
        return uploadResult;
    }

    public List<Upload> delete(Long[] filesToKeep, String group, Long reference) {
        List<Upload> uploadedFiles = Optional.ofNullable(
                uploadRepository.getByGroupAndReference(group, reference)
        ).orElse(new ArrayList<>());
        if (!uploadedFiles.isEmpty()) {
            List<Long> toKeepList = Arrays.asList(
                    Optional.ofNullable(filesToKeep).orElse(new Long[]{})
            ); // empty => delete all
            List<Upload> toRemoveList = new ArrayList<>();
            for (Upload file : uploadedFiles) {
                // uploadedFiles: [1, 2, 3]
                // filesToKeep: [1, 3]
                // => delete file 2
                if (!toKeepList.contains(file.getId())) {
                    log.info("Delete " + file);
                    deleteFile(file.getPath()); // from system path
                    uploadRepository.delete(file); // from DB
                    toRemoveList.add(file);
                }
            }
            uploadedFiles.removeAll(toRemoveList); // from UI
        }
        return uploadedFiles;
    }

    private Optional<Upload> moveFile(MultipartFile file, String path, Upload upload) {
        if (isNull(file) || isNull(file.getOriginalFilename())) {
            log.error("Upload file is null");
        } else {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String destination = path + File.separator + upload.getId() + "_" + filename;
            try {
                file.transferTo(new File(destination));
                upload.setFilename(filename);
                upload.setPath(destination);
                uploadRepository.save(upload);
                log.info("Moved file [{}] to path [{}]", filename, destination);
                return Optional.of(upload);
            } catch (IOException ex) {
                log.error("Failed to move file [{}] to path [{}], {}", filename, destination, ex.getMessage());
            } catch (DataAccessException ex) {
                log.error("Failed to save " + upload);
                deleteFile(destination);
            }
        }
        uploadRepository.delete(upload);
        return Optional.empty();
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (InvalidPathException | IOException ex) {
            throw new UploadException("Failed to delete file [" + path + "], " + ex.getMessage());
        }
    }
}

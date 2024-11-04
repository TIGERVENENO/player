package ru.tigran.player.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;
import ru.tigran.player.service.dto.VideoFileDto;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "Player";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = GoogleDriveService.class.getClassLoader()
            .getResource("static/client_secret.json").getPath();

    private final Drive driveService;

    public GoogleDriveService() throws Exception {
        this.driveService = initializeDriveService();
    }

    // Метод для инициализации Google Drive API
    private Drive initializeDriveService() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, List.of(DriveScopes.DRIVE))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = flow.loadCredential("user");
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    // Метод для получения списка файлов с Google Drive
    public List<VideoFileDto> listFiles() throws Exception {
        FileList result = driveService.files().list()
                .setPageSize(10)
                .setFields("files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
            return List.of();
        }
        // Преобразование в список DTO для упрощенной работы
        return files.stream()
                .map(file -> new VideoFileDto(file.getId(), file.getName()))
                .collect(Collectors.toList());
    }

    // Метод для получения ссылки на HLS-поток
    public String getHlsLink(String fileId) {
        // Создание ссылки для доступа к видеофайлу на Google Drive
        // Примечание: видеофайл должен быть публичным или у тебя должен быть доступ к файлу
        return "https://drive.google.com/uc?id=" + fileId + "&export=download";
    }
}

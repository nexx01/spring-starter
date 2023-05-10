package shadow.dev.spring.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
//import lombok.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:D:}\\IdeaProject\\spring-starter\\images")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        var fullImagePath = Path.of(bucket, imagePath);

        try(content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath)  {
        Path fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath) ?
                Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }
}

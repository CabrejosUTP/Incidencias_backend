package com.example.app_incidencias_backend_v2.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class CloudinaryUtil {

    private final Cloudinary cloudinary;

    public CloudinaryUtil(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String  guardarImagenEnNube(MultipartFile image, Integer idIncidencia) {
        try {
            //  GUARDAMOS LA IMAGEN A CLOUDINARY STORAGE
            Joiner joiner = Joiner.on("_").skipNulls();
            String publicId = joiner.join(Collections.singleton(idIncidencia));

            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    image.getBytes(), ObjectUtils.asMap("public_id", publicId)
            );
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String actualizarImagenEnLaNube(MultipartFile image, Integer idIncidencia) {
        try {
            Joiner joiner = Joiner.on("_").skipNulls();
            String publicId = joiner.join(Collections.singleton(idIncidencia));

            //  ELIMINAMOS LA IMAGEN POR SU PUBLIC ID
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            return guardarImagenEnNube(image, idIncidencia);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

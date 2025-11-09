package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.config.StorageProperties
import org.shangahi.sellio_backend.service.exception.ImageUploadFailedException
import org.shangahi.sellio_backend.service.exception.InvalidImageFormatException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime

@Service
class StorageService(
    private val sellioClient: S3Client,
    private val props: StorageProperties
) {

    fun uploadImage(
        file: MultipartFile,
        fileName: String,
        folderName: String
    ): String {
        val mime = file.contentType ?: throw InvalidImageFormatException()
        val ext = allowedMimeTypes[mime] ?: throw InvalidImageFormatException()

        val finalName = "${fileName}_${LocalDateTime.now()}.$ext"
        val key = "images/$folderName/$finalName"

        val putReq = PutObjectRequest.builder()
            .bucket(props.bucket)
            .key(key)
            .contentType(mime)
            .build()

        runCatching {
            sellioClient.putObject(putReq, RequestBody.fromBytes(file.bytes))
        }.onFailure { e ->
            throw ImageUploadFailedException()
        }

        return "${props.cdnEndpoint}/$key"
    }

    fun deleteImage(imageUrl: String): Boolean {
        val prefix = props.cdnEndpoint
        if (!imageUrl.startsWith(prefix)) {
            throw Exception()
        }
        val key = imageUrl.removePrefix("$prefix/")

        return sellioClient.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(props.bucket)
                .key(key).build()
        ).sdkHttpResponse().isSuccessful
    }

    private companion object {
        val allowedMimeTypes = mapOf(
            "image/jpeg" to "jpg",
            "image/jpg" to "jpg",
            "image/png" to "png",
            "image/webp" to "webp"
        )
    }
}
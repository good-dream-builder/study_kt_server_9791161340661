package com.songko.simpleshopserver.domain.product

import com.songko.simpleshopserver.common.CustomException
import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.geometry.Positions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Service
class ProductImageService @Autowired constructor(
        private val productImageRepository: ProductImageRepository
) {
    @Value("\${simple-shop.file-upload.default-dir:songko}")
    var uploadPath: String? = ""

    fun uploadImage(image: MultipartFile): ProductImageUploadResponse {
        val filePath = saveImageFile(image)
        val productImage = saveImageData(filePath)

        return productImage.id?.let {
            ProductImageUploadResponse(it, filePath)
        } ?: throw CustomException("이미지 저장 실패. 다시 시도 해 주세요.")
    }

    /**
     * 서버에 이미지를 저장
     */
    private fun saveImageFile(image: MultipartFile): String {
        val extension = image.originalFilename
                ?.takeLastWhile { it != '.' }
                ?: throw CustomException("다른 이미지로 다시 시도해주세요.")

        val uuid = UUID.randomUUID().toString()
        val date = SimpleDateFormat("yyyyMMdd").format(Date())

        val filePath = "/images/$date/$uuid.$extension"
        val targetFile = File("$uploadPath/$filePath")
        val thumbnail = targetFile.absolutePath
                .replace(uuid, "$uuid-thumb")
                .let(::File)

        targetFile.parentFile.mkdirs()
        image.transferTo(targetFile)

        Thumbnails.of(targetFile)
                .crop(Positions.CENTER)
                .size(300, 300)
                .outputFormat("jpg")
                .outputQuality(0.8f)
                .toFile(thumbnail)

        return filePath
    }

    /**
     * DB에 이미지 정보를 저장
     */
    private fun saveImageData(filePath: String): ProductImage {
        val productImage = ProductImage(filePath)
        return productImageRepository.save(productImage)
    }
}
package aicte

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional
class UtilityService {


    def convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename())
        convFile.createNewFile()
        FileOutputStream fos = new FileOutputStream(convFile)
        fos.write(file.getBytes())
        fos.close()
        return convFile
    }
}

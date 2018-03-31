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
    def getAvg(Rating rating){
        def num = 0
        for(ParamValues paramValues:rating.parameters){
            num = num + Double.parseDouble(paramValues.value)
        }
        if(rating.parameters.size()!=0)
        num = num/rating.parameters.size()

        return num
    }
}

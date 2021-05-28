package graduationproject.back.service.impl;

import graduationproject.back.exception.BusinessException;
import graduationproject.back.exception.enums.CommonEnum;
import graduationproject.back.mapper.CourseMapper;
import graduationproject.back.mapper.FilesMapper;
import graduationproject.back.model.entity.Course;
import graduationproject.back.model.entity.Files;
import graduationproject.back.model.vo.AllStudentUploadFileVo;
import graduationproject.back.model.vo.UploadFileVo;
import graduationproject.back.service.FileService;
import graduationproject.back.utils.FileUtil;
import graduationproject.back.utils.FileZipUtil;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.Date;
import java.util.List;

/**
 * @Author Pan
 * @Date 2020/12/12 11:36
 */
@Service
public class FileServiceImpl implements FileService {

    private final FilesMapper filesMapper;
    private final CourseMapper courseMapper;

    public FileServiceImpl(FilesMapper filesMapper, CourseMapper courseMapper) {
        this.filesMapper = filesMapper;
        this.courseMapper = courseMapper;
    }


    @Transactional
    @Override
    public int uploadFile(String userId, MultipartFile file, Integer courseId, Integer assignmentId) {

        Course course = courseMapper.selectByCourseId(courseId);
        if(course == null){
            throw new BusinessException(CommonEnum.COURSE_NOT_EXIXT_ERROR);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        //保存文件
        FileUtil.uploadFile(userId,assignmentId,file);

        //设置非压缩包文件路径
        String FilePath = FileUtil.getUploadPath() + "/"+userId + "/" + assignmentId;
        //设置压缩包文件路径
        String zipFilePath = FileUtil.getUploadPath() + "/" +userId + "/" + "ZipFile" + "/" + fileName;

        Files files = new Files();
        files.setAuthorId(userId);
        files.setCreateTime(new Date());
        files.setCourseId(courseId);
        files.setAssignmentId(assignmentId);

     
        int result = 1;
        //判断是否是压缩包格式
        if(!FileZipUtil.verify(fileName)){
            files.setFileName(fileName);
            files.setFilePath("/" +userId + "/" + assignmentId + "/" + fileName);
            filesMapper.saveUploadFile(files);
        }else{
            List<FileHeader> filesList =  FileZipUtil.unZip(zipFilePath,FilePath);

            for(FileHeader fileHeader : filesList){
                //如果不为文件夹
                if(!fileHeader.isDirectory())
                {
                    files.setFileName(fileHeader.getFileName().substring(fileHeader.getFileName().lastIndexOf("/") + 1));
                    files.setFilePath("/" +userId + "/" + assignmentId+ "/" + fileHeader.getFileName());
                    filesMapper.saveUploadFile(files);
                }
            }
        }
        return result;
    }

    @Transactional
    @Override
    public int batchUploadFile(String userId, HttpServletRequest request, Integer courseId, Integer assignmentId) {

        //判断课程是否存在
        Course course = courseMapper.selectByCourseId(courseId);
        if(course == null){
            throw new BusinessException(CommonEnum.COURSE_NOT_EXIXT_ERROR);
        }

        //读取多文件
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;

        //设置非压缩包文件路径
        String FilePath = FileUtil.getUploadPath() + "/"+userId + "/" + assignmentId;
        //传入数据
        Files files1 = new Files();
        files1.setAuthorId(userId);
        files1.setCreateTime(new Date());
        files1.setCourseId(courseId);
        files1.setAssignmentId(assignmentId);
        
        int result = 1;

        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            // 获取文件名
            String fileName = file.getOriginalFilename();
            //设置压缩包文件路径
            String zipFilePath = FileUtil.getUploadPath() + "/" +userId + "/" + "ZipFile" + "/" + fileName;
            //保存文件
            fileName = FileUtil.batchUploadFile(userId,assignmentId,file,i);
            
            //判断是否是压缩包格式
            if(!FileZipUtil.verify(fileName)){
                files1.setFileName(fileName);
                files1.setFilePath("/" +userId + "/" + assignmentId + "/" + fileName);
                filesMapper.saveUploadFile(files1);
            }else {
                List<FileHeader> filesList =  FileZipUtil.unZip(zipFilePath,FilePath);
                for(FileHeader fileHeader : filesList){
                    //如果不为文件夹
                    if(!fileHeader.isDirectory())
                    {
                        files1.setFileName(fileHeader.getFileName().substring(fileHeader.getFileName().lastIndexOf("/") + 1));
                        files1.setFilePath("/" +userId + "/" + assignmentId+ "/" + fileHeader.getFileName());
                        filesMapper.saveUploadFile(files1);
                    }
                }

            }
        }
        return result;
    }

    @Override
    public String downloadFile(HttpServletResponse response, Integer fileId) {
        Files file = filesMapper.selectFilePathByFileld(fileId);

        return FileUtil.downloadFile(response,file.getFilePath());
    }

    @Override
    public List<UploadFileVo> stuGetUploadFileByUserIdAndCourseId(String userId, Integer courseId) {


        return filesMapper.stuGetUploadFileByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<AllStudentUploadFileVo> getStudentFileListByUserId(String userId, Integer courseId) {
        return filesMapper.getStudentFileListByUserId(userId,courseId);
    }
}

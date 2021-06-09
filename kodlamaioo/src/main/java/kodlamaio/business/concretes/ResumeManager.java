package kodlamaio.business.concretes;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kodlamaio.business.abstracts.ResumeService;
import kodlamaio.core.utilities.DataResult;
import kodlamaio.core.utilities.Result;
import kodlamaio.core.utilities.SuccessDataResult;
import kodlamaio.core.utilities.SuccessResult;
import kodlamaio.dataAccess.abstracts.ResumeDao;
import kodlamaio.entities.concretes.Resume;
import kodlamaio.entities.concretes.dtos.ResumeAddDto;
import kodlamaio.entities.concretes.dtos.ResumeGetDto;
import kodlamaio.core.cloudinary.CloudinaryService;
import kodlamaio.core.dtoConverter.DtoConverterService;

@Service
public class ResumeManager implements ResumeService {

	private ResumeDao resumeDao;
	private CloudinaryService cloudinaryService;
	private DtoConverterService dtoConverterService;
	
	@Autowired
	public ResumeManager(ResumeDao resumeDao, CloudinaryService cloudinaryService, DtoConverterService dtoConverterService) {
		super();
		this.resumeDao = resumeDao;
		this.cloudinaryService = cloudinaryService;
		this.dtoConverterService = dtoConverterService;
				
	}


	@Override
	public Result add(ResumeAddDto resumeDto) {
//		if (resume.getLanguages() != null) {
//			resume.getLanguages().forEach(lang -> lang.setResume(resume));
//		}
//		if (resume.getEducation() != null) {
//			resume.getEducation().forEach(lang -> lang.setResume(resume));
//		}
//		if (resume.getTechnologies() != null) {
//			resume.getTechnologies().forEach(lang -> lang.setResume(resume));
//		}
//		if (resume.getJobExperiences() != null) {
//			resume.getJobExperiences().forEach(lang -> lang.setResume(resume));
//		}
				
		resumeDao.save((Resume) dtoConverterService.dtoClassConverter(resumeDto, Resume.class));
		return new SuccessResult("Kayıt Başarılı");
		
	}


	@Override
	public DataResult<List<ResumeGetDto>> getAll() {
		return new SuccessDataResult<List<ResumeGetDto>>
		(dtoConverterService.dtoConverter
				(resumeDao.findAll(), ResumeGetDto.class)
				,"Data Listelendi");
		
	}

	@Override
	public Result saveImage(MultipartFile file, int resumeId) {
		
		
		@SuppressWarnings("unchecked")
		Map<String, String> uploader = (Map<String, String>) 
				cloudinaryService.save(file).getData(); 
		String imageUrl= uploader.get("url");
		Resume Cv = resumeDao.getOne(resumeId);
		Cv.setPhoto(imageUrl);
		resumeDao.save(Cv);
		return new SuccessResult("Kayıt Başarılı");
		
	}


	@Override
	public DataResult<List<ResumeGetDto>> findAllByCandidateId(int id) {
		return new SuccessDataResult<List<ResumeGetDto>>
		(dtoConverterService.dtoConverter
				(resumeDao.findAllByCandidateId(id), ResumeGetDto.class)
				,"Data Listelendi");
	}
}

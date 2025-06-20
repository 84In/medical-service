package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.dto.*;
import com.vasd.medical_service.doctors.dto.request.CreateDoctorDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDoctorDto;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.doctors.dto.response.DoctorResponseDto;
import com.vasd.medical_service.doctors.dto.response.PositionResponseDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.entities.*;
import com.vasd.medical_service.doctors.repository.*;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.upload.service.ImageUsageProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final TitleRepository titleRepository;
    private final PositionRepository positionRepository;
    private final SpecialtyRepository specialtyRepository;
    private final ExperienceRepository experienceRepository;
    private final WorkingHourRepository workingHourRepository;
    private final AchievementRepository achievementRepository;
    private final EducationRepository educationRepository;
    private final ImageUsageProcessor imageUsageProcessor;

    @Transactional
    public DoctorResponseDto createDoctor(CreateDoctorDto dto) {
        try {
            Doctor doctor = new Doctor();
            doctor.setName(dto.getName());
            doctor.setAvatarUrl(dto.getAvatarUrl());
            doctor.setIntroduction(dto.getIntroduction());
            doctor.setExperienceYears(dto.getExperienceYears());
            doctor.setEmail(dto.getEmail());
            doctor.setPhone(dto.getPhone());
            doctor.setStatus(dto.getStatus());

            if (dto.getSpecialtyIds() != null && !dto.getSpecialtyIds().isEmpty()) {
                List<Specialty> specialties = specialtyRepository.findAllById(dto.getSpecialtyIds());
                if (specialties.size() != dto.getSpecialtyIds().size()) {
                    throw new AppException(ErrorCode.SPECIALTY_NOT_FOUND); // Hoặc tự kiểm tra id nào bị thiếu
                }
                doctor.setSpecialties(new HashSet<>(specialties));
            }

            doctor.setDepartment(
                    departmentRepository.findById(dto.getDepartmentId())
                            .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND))
            );
            doctor.setPosition(
                    positionRepository.findById(dto.getPositionId())
                            .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND))
            );
            doctor.setTitle(
                    titleRepository.findById(dto.getTitleId())
                            .orElseThrow(() -> new AppException(ErrorCode.TITLE_NOT_FOUND))
            );

            Doctor rsdoctor = doctorRepository.save(doctor);
            log.info("save doctor {}", rsdoctor);

            if (dto.getWorkingHours() != null) {

                List<WorkingHour> workingHours = dto.getWorkingHours().stream().map(whDto -> {
                    WorkingHour wh = new WorkingHour();
                    wh.setDoctor(rsdoctor);
                    wh.setDayOfWeek(whDto.getDayOfWeek());
                    wh.setStartTime(whDto.getStartTime());
                    wh.setEndTime(whDto.getEndTime());
                    wh.setIsAvailable(whDto.getIsAvailable());
                    return wh;
                }).toList();
                workingHourRepository.saveAll(workingHours);
                rsdoctor.setWorkingHours(new HashSet<>(workingHours));
                log.info("save workingHours");
            }

            if (dto.getEducation() != null) {
                List<Education> educations = dto.getEducation().stream().map(edDto -> {
                    Education ed = new Education();
                    ed.setDoctor(rsdoctor);
                    ed.setDegree(edDto.getDegree());
                    ed.setInstitution(edDto.getInstitution());
                    ed.setYear(edDto.getYear());
                    ed.setDescription(edDto.getDescription());
                    return ed;
                }).collect(Collectors.toList());
                educationRepository.saveAll(educations);
                rsdoctor.setEducation(new HashSet<>(educations));
                log.info("save education");
            }

            if (dto.getWorkExperience() != null) {
                List<Experience> experiences = dto.getWorkExperience().stream().map(exDto -> {
                    Experience ex = new Experience();
                    ex.setDoctor(rsdoctor);
                    ex.setPosition(exDto.getPosition());
                    ex.setOrganization(exDto.getOrganization());
                    ex.setStartYear(exDto.getStartYear());
                    ex.setEndYear(exDto.getEndYear());
                    ex.setDescription(exDto.getDescription());
                    return ex;
                }).collect(Collectors.toList());
                experienceRepository.saveAll(experiences);
                rsdoctor.setWorkExperience(new HashSet<>(experiences));
                log.info("save workExperience");
            }

            if (dto.getAchievements() != null) {
                List<Achievement> achievements = dto.getAchievements().stream().map(acDto -> {
                    Achievement ac = new Achievement();
                    ac.setDoctor(rsdoctor);
                    ac.setTitle(acDto.getTitle());
                    ac.setYear(acDto.getYear());
                    ac.setDescription(acDto.getDescription());
                    ac.setType(acDto.getType());
                    return ac;
                }).collect(Collectors.toList());
                achievementRepository.saveAll(achievements);
                rsdoctor.setAchievements(new HashSet<>(achievements));
                log.info("save achievements");
            }

            imageUsageProcessor.processImageUrl(dto.getAvatarUrl());
            log.info("process image");

            return mapDoctor(rsdoctor);
        } catch (Exception e) {
            log.error("❌ Lỗi khi tạo doctor, transaction sẽ rollback", e);
            throw e; // hoặc AppException
        }
    }

    @Transactional
    public DoctorResponseDto updateDoctor(Long doctorId, UpdateDoctorDto dto) {

        log.info("Doctor update {}", dto);

        try {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
        log.info("update doctor {}", doctor.getId());

        doctor.setName(dto.getName());
        doctor.setAvatarUrl(dto.getAvatarUrl());
        doctor.setIntroduction(dto.getIntroduction());
        doctor.setExperienceYears(dto.getExperienceYears());

        if(
                dto.getDepartmentId() != null
        ){
            Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
            doctor.setDepartment(department);
        }
        if(
                dto.getPositionId() != null
        ){
            Position position = positionRepository.findById(dto.getPositionId()).orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));
            doctor.setPosition(position);
        }
        if(
                dto.getTitleId() != null
        ){
            Title title = titleRepository.findById(dto.getTitleId()).orElseThrow(() -> new AppException(ErrorCode.TITLE_NOT_FOUND));
            doctor.setTitle(title);
        }

        doctor.setStatus(dto.getStatus());

        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());

       if(dto.getSpecialtyIds() != null) {
           updateSpecialties(doctor, dto.getSpecialtyIds());
       }

        if(dto.getWorkExperience() != null) {
            updateWorkingHours(doctor, dto.getWorkingHours());
        }
        if(dto.getEducation() != null) {
            updateEducation(doctor, dto.getEducation());
        }
        if(dto.getWorkExperience() != null) {
            updateExperience(doctor, dto.getWorkExperience());
        }
        if(dto.getAchievements() != null) {
            updateAchievements(doctor, dto.getAchievements());
        }

        imageUsageProcessor.processImageUrl(dto.getAvatarUrl());

        return mapDoctor(doctorRepository.save(doctor));
        } catch (Exception e) {
            log.error("❌ Lỗi khi cập nhật doctor, transaction sẽ rollback", e);
            throw e; // hoặc AppException
        }
    }

    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        log.info("Get all doctors");
        return doctors.stream().map(this::mapDoctor).collect(Collectors.toList());
    }

    public Page<DoctorResponseDto> getAllDoctors(Pageable pageable, String keyword, Status status, Long departmentId) {
//        Cách 1: ngốn tài nguyen vi fetch join nhieu
//        Page<Doctor> doctorPage = doctorRepository.searchDoctors(
//                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
//                status,
//                departmentId,
//                pageable
//        );
//
//        log.info("Get all doctors search by: {}, {} and {} ", keyword, status, departmentId);
//
//        return doctorPage.map(this::mapDoctor);

//        Cách 2: Lúc fetch đầu lấy đầy đủ thông tin doctors (vẫn cần fetch lấy detail) tạm chấp nhận
//        Page<Doctor> simplePage = doctorRepository.searchDoctorsSimple(
//                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
//                status,
//                departmentId,
//                pageable
//        );
//
//        List<DoctorResponseDto> detailedList = simplePage.stream()
//                .map(d -> doctorRepository.findByIdWithDetails(d.getId())
//                        .map(this::mapDoctor)
//                        .orElseGet(() -> this.mapDoctor(d)))
//                .toList();
//
//        return new PageImpl<>(detailedList, pageable, simplePage.getTotalElements());
//        Cách 3: sử dụng fetch ids phân trang (nhanh không tốn quá nhiều thời gian) sau đó lấy details tất cả các ids
        Page<Long> idPage = doctorRepository.searchDoctorIds(keyword, status, departmentId, pageable);
        List<Long> ids = idPage.getContent();

        List<Doctor> doctors = doctorRepository.findAllByIdsWithDetails(ids);
        Map<Long, Doctor> doctorMap = doctors.stream()
                .collect(Collectors.toMap(Doctor::getId, d -> d));

        List<DoctorResponseDto> dtos = ids.stream()
                .map(id -> mapDoctor(doctorMap.get(id)))
                .toList();

        return new PageImpl<>(dtos, pageable, idPage.getTotalElements());
    }


    public DoctorResponseDto getDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findByIdWithDetail(doctorId).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
        return mapDoctor(doctor);
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    private void updateSpecialties(Doctor doctor, List<Long> specialtyIds) {
        log.info("Updating specialties for doctor {}: {}", doctor.getId(), specialtyIds);
        List<Specialty> specialties = new ArrayList<>(specialtyRepository.findAllById(specialtyIds));
        doctor.setSpecialties(new HashSet<>(specialties));
    }

    private void updateWorkingHours(Doctor doctor, List<WorkingHourDTO> dtos) {
        if (dtos == null) {
            log.info("No working hours provided, deleting all for doctor {}", doctor.getId());
            workingHourRepository.deleteByDoctorId(doctor.getId());
            return;
        }

        log.info("Updating working hours for doctor {}: {}", doctor.getId(), dtos);
        List<WorkingHour> currentList = workingHourRepository.findByDoctorId(doctor.getId());
        Map<Long, WorkingHour> mapCurrent = currentList.stream()
                .filter(wh -> wh.getId() != null)
                .collect(Collectors.toMap(WorkingHour::getId, Function.identity()));

        List<WorkingHour> updatedList = new ArrayList<>();
        for (WorkingHourDTO dto : dtos) {
            WorkingHour entity;
            if (dto.getId() != null && mapCurrent.containsKey(dto.getId())) {
                entity = mapCurrent.get(dto.getId());
                entity.setDayOfWeek(dto.getDayOfWeek());
                entity.setStartTime(dto.getStartTime());
                entity.setEndTime(dto.getEndTime());
                entity.setIsAvailable(dto.getIsAvailable());
                mapCurrent.remove(dto.getId());
            } else {
                entity = new WorkingHour();
                entity.setDoctor(doctor);
                entity.setDayOfWeek(dto.getDayOfWeek());
                entity.setStartTime(dto.getStartTime());
                entity.setEndTime(dto.getEndTime());
                entity.setIsAvailable(dto.getIsAvailable());
            }
            updatedList.add(entity);
        }

        List<WorkingHour> toDelete = mapCurrent.values().stream()
                .filter(wh -> wh.getId() != null)
                .toList();

        log.info("Deleting old working hours: {}", toDelete.stream().map(WorkingHour::getId).toList());
        workingHourRepository.deleteAll(toDelete);
        workingHourRepository.saveAll(updatedList);
    }

    private void updateEducation(Doctor doctor, List<EducationDTO> dtos) {
        if (dtos == null) {
            log.info("No education info provided, deleting all for doctor {}", doctor.getId());
            educationRepository.deleteByDoctorId(doctor.getId());
            return;
        }

        log.info("Updating education for doctor {}: {}", doctor.getId(), dtos);
        List<Education> currentList = educationRepository.findByDoctorId(doctor.getId());
        Map<Long, Education> mapCurrent = currentList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Education::getId, Function.identity()));

        List<Education> updatedList = new ArrayList<>();
        for (EducationDTO dto : dtos) {
            Education entity;
            if (dto.getId() != null && mapCurrent.containsKey(dto.getId())) {
                entity = mapCurrent.get(dto.getId());
                entity.setDegree(dto.getDegree());
                entity.setInstitution(dto.getInstitution());
                entity.setYear(dto.getYear());
                entity.setDescription(dto.getDescription());
                mapCurrent.remove(dto.getId());
            } else {
                entity = new Education();
                entity.setDoctor(doctor);
                entity.setDegree(dto.getDegree());
                entity.setInstitution(dto.getInstitution());
                entity.setYear(dto.getYear());
                entity.setDescription(dto.getDescription());
            }
            updatedList.add(entity);
        }

        List<Education> toDelete = mapCurrent.values().stream()
                .filter(e -> e.getId() != null)
                .toList();

        log.info("Deleting old education entries: {}", toDelete.stream().map(Education::getId).toList());
        educationRepository.deleteAll(toDelete);
        educationRepository.saveAll(updatedList);
    }

    // Dùng chung cho mọi entity con như Education, Experience, Achievement...
//    private <ID, DTO, ENTITY> void syncChildEntities(
//            List<DTO> dtos,
//            Function<DTO, ID> getDtoIdFn,
//            Map<ID, ENTITY> currentEntityMap,
//            Function<DTO, ENTITY> mapDtoToEntity,
//            Consumer<Collection<ENTITY>> deleteFn,
//            Consumer<Collection<ENTITY>> saveFn
//    ) {
//        if (dtos == null || dtos.isEmpty()) {
//            deleteFn.accept(currentEntityMap.values());
//            return;
//        }
//
//        List<ENTITY> updatedList = new ArrayList<>();
//
//        for (DTO dto : dtos) {
//            ID id = getDtoIdFn.apply(dto);
//            ENTITY entity = (id != null && currentEntityMap.containsKey(id))
//                    ? currentEntityMap.remove(id)
//                    : mapDtoToEntity.apply(dto);
//
//            updatedList.add(entity);
//        }
//
//        deleteFn.accept(currentEntityMap.values());
//        saveFn.accept(updatedList);
//    }
//    // ---------------------------
//// Sử dụng cho Education:
//    private void updateEducation(Doctor doctor, List<EducationDTO> dtos) {
//        List<Education> currentList = educationRepository.findByDoctorId(doctor.getId());
//        Map<Long, Education> currentMap = currentList.stream()
//                .filter(e -> e.getId() != null)
//                .collect(Collectors.toMap(Education::getId, Function.identity()));
//
//        syncChildEntities(
//                dtos,
//                EducationDTO::getId,
//                currentMap,
//                dto -> {
//                    Education e = new Education();
//                    e.setDoctor(doctor);
//                    e.setDegree(dto.getDegree());
//                    e.setInstitution(dto.getInstitution());
//                    e.setYear(dto.getYear());
//                    e.setDescription(dto.getDescription());
//                    return e;
//                },
//                educationRepository::deleteAll,
//                educationRepository::saveAll
//        );
//    }
//
//    // ---------------------------
//// Sử dụng cho Experience:
//    private void updateExperience(Doctor doctor, List<ExperienceDTO> dtos) {
//        List<Experience> currentList = experienceRepository.findByDoctorId(doctor.getId());
//        Map<Long, Experience> currentMap = currentList.stream()
//                .filter(e -> e.getId() != null)
//                .collect(Collectors.toMap(Experience::getId, Function.identity()));
//
//        syncChildEntities(
//                dtos,
//                ExperienceDTO::getId,
//                currentMap,
//                dto -> {
//                    Experience e = new Experience();
//                    e.setDoctor(doctor);
//                    e.setPosition(dto.getPosition());
//                    e.setOrganization(dto.getOrganization());
//                    e.setStartYear(dto.getStartYear());
//                    e.setEndYear(dto.getEndYear());
//                    e.setDescription(dto.getDescription());
//                    return e;
//                },
//                experienceRepository::deleteAll,
//                experienceRepository::saveAll
//        );
//    }
//
//    // ---------------------------
//// Sử dụng cho Achievement:
//    private void updateAchievements(Doctor doctor, List<AchievementDTO> dtos) {
//        List<Achievement> currentList = achievementRepository.findByDoctorId(doctor.getId());
//        Map<Long, Achievement> currentMap = currentList.stream()
//                .filter(a -> a.getId() != null)
//                .collect(Collectors.toMap(Achievement::getId, Function.identity()));
//
//        syncChildEntities(
//                dtos,
//                AchievementDTO::getId,
//                currentMap,
//                dto -> {
//                    Achievement a = new Achievement();
//                    a.setDoctor(doctor);
//                    a.setTitle(dto.getTitle());
//                    a.setYear(dto.getYear());
//                    a.setDescription(dto.getDescription());
//                    a.setType(dto.getType());
//                    return a;
//                },
//                achievementRepository::deleteAll,
//                achievementRepository::saveAll
//        );
//    }




    private void updateExperience(Doctor doctor, List<ExperienceDTO> dtos) {
        if (dtos == null) {
            experienceRepository.deleteByDoctorId(doctor.getId());
            return;
        }
        List<Experience> currentList = experienceRepository.findByDoctorId(doctor.getId());
        Map<Long, Experience> mapCurrent = currentList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Experience::getId, Function.identity()));

        List<Experience> updatedList = new ArrayList<>();
        for (ExperienceDTO dto : dtos) {
            Experience entity;
            if (dto.getId() != null && mapCurrent.containsKey(dto.getId())) {
                entity = mapCurrent.get(dto.getId());
                entity.setPosition(dto.getPosition());
                entity.setOrganization(dto.getOrganization());
                entity.setStartYear(dto.getStartYear());
                entity.setEndYear(dto.getEndYear());
                entity.setDescription(dto.getDescription());
                mapCurrent.remove(dto.getId());
            } else {
                entity = new Experience();
                entity.setDoctor(doctor);
                entity.setPosition(dto.getPosition());
                entity.setOrganization(dto.getOrganization());
                entity.setStartYear(dto.getStartYear());
                entity.setEndYear(dto.getEndYear());
                entity.setDescription(dto.getDescription());
            }
            updatedList.add(entity);
        }
        experienceRepository.deleteAll(mapCurrent.values());
        experienceRepository.saveAll(updatedList);
    }

    private void updateAchievements(Doctor doctor, List<AchievementDTO> dtos) {
        if (dtos == null) {
            achievementRepository.deleteByDoctorId(doctor.getId());
            return;
        }
        List<Achievement> currentList = achievementRepository.findByDoctorId(doctor.getId());
        Map<Long, Achievement> mapCurrent = currentList.stream()
                .filter(a -> a.getId() != null)
                .collect(Collectors.toMap(Achievement::getId, Function.identity()));

        List<Achievement> updatedList = new ArrayList<>();
        for (AchievementDTO dto : dtos) {
            Achievement entity;
            if (dto.getId() != null && mapCurrent.containsKey(dto.getId())) {
                entity = mapCurrent.get(dto.getId());
                entity.setTitle(dto.getTitle());
                entity.setYear(dto.getYear());
                entity.setDescription(dto.getDescription());
                entity.setType(dto.getType());
                mapCurrent.remove(dto.getId());
            } else {
                entity = new Achievement();
                entity.setDoctor(doctor);
                entity.setTitle(dto.getTitle());
                entity.setYear(dto.getYear());
                entity.setDescription(dto.getDescription());
                entity.setType(dto.getType());
            }
            updatedList.add(entity);
        }
        achievementRepository.deleteAll(mapCurrent.values());
        achievementRepository.saveAll(updatedList);
    }


    private DoctorResponseDto mapDoctor(Doctor doctor) {
        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .avatarUrl(doctor.getAvatarUrl())
                .introduction(doctor.getIntroduction())
                .experienceYears(doctor.getExperienceYears())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .status(doctor.getStatus())
                .department(DepartmentResponseDto.builder()
                        .id(doctor.getDepartment().getId())
                        .name(doctor.getDepartment().getName())
                        .contentHtml(doctor.getDepartment().getContentHtml())
                        .status(doctor.getDepartment().getStatus())
                        .build())
                .position(PositionResponseDto.builder()
                        .id(doctor.getPosition().getId())
                        .name(doctor.getPosition().getName())
                        .description(doctor.getPosition().getDescription())
                        .status(doctor.getPosition().getStatus())
                        .build())
                .title(TitleResponseDto.builder()
                        .id(doctor.getTitle().getId())
                        .name(doctor.getTitle().getName())
                        .description(doctor.getTitle().getDescription())
                        .status(doctor.getTitle().getStatus())
                        .build())
                .specialties(
                        doctor.getSpecialties().stream().map(s -> new SpecialtyDto(
                                s.getId(),
                                s.getName(),
                                s.getDescription(),
                                s.getStatus()
                        )).collect(Collectors.toList())
                )
                .achievements(mapAchievement(new ArrayList<>(doctor.getAchievements())))
                .education(mapEducation(new ArrayList<>(doctor.getEducation())))
                .workExperience(mapExperience(new ArrayList<>(doctor.getWorkExperience())))
                .workingHours(mapWorkingHour(new ArrayList<>(doctor.getWorkingHours())))
                .build();
    }

    private List<AchievementDTO> mapAchievement(List<Achievement> achievements) {
        achievements.sort(Comparator.comparing(Achievement::getYear));
        return achievements.stream().map(achievement -> {

            AchievementDTO aDto = new AchievementDTO();
            aDto.setId(achievement.getId());
            aDto.setDescription(achievement.getDescription());
            aDto.setYear(achievement.getYear());
            aDto.setType(achievement.getType());
            aDto.setTitle(achievement.getTitle());
            return aDto;
        }).collect(Collectors.toList());
    }

    private List<EducationDTO> mapEducation(List<Education> educations) {
        List<Education> sortedList = new ArrayList<>(educations);
        sortedList.sort(Comparator.comparing(Education::getYear));
        return sortedList.stream().map(education -> {
            EducationDTO eDto = new EducationDTO();
            eDto.setId(education.getId());
            eDto.setDescription(education.getDescription());
            eDto.setYear(education.getYear());
            eDto.setDegree(education.getDegree());
            eDto.setInstitution(education.getInstitution());
            return eDto;
        }).collect(Collectors.toList());
    }


    private List<ExperienceDTO> mapExperience(List<Experience> experiences) {
        List<Experience> sortedList = new ArrayList<>(experiences);
        sortedList.sort(Comparator.comparing(Experience::getStartYear));
        return sortedList.stream().map(experience -> {
            ExperienceDTO eDto = new ExperienceDTO();
            eDto.setId(experience.getId());
            eDto.setStartYear(experience.getStartYear());
            eDto.setEndYear(experience.getEndYear());
            eDto.setDescription(experience.getDescription());
            eDto.setOrganization(experience.getOrganization());
            eDto.setPosition(experience.getPosition());
            return eDto;
        }).collect(Collectors.toList());
    }


    private List<WorkingHourDTO> mapWorkingHour(List<WorkingHour> workingHours) {
        List<WorkingHour> sortedList = new ArrayList<>(workingHours);
        sortedList.sort(Comparator.comparing((WorkingHour wh) -> wh.getDayOfWeek().ordinal())
                .thenComparing(w -> LocalTime.parse(w.getStartTime())));
        return sortedList.stream().map(workingHour -> {
            WorkingHourDTO whDto = new WorkingHourDTO();
            whDto.setId(workingHour.getId());
            whDto.setDayOfWeek(workingHour.getDayOfWeek());
            whDto.setStartTime(workingHour.getStartTime());
            whDto.setEndTime(workingHour.getEndTime());
            whDto.setIsAvailable(workingHour.getIsAvailable());
            return whDto;
        }).collect(Collectors.toList());
    }


}

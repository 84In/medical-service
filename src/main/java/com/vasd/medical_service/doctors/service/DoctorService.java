package com.vasd.medical_service.doctors.service;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @Transactional
    public DoctorResponseDto createDoctor(CreateDoctorDto dto){
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
            doctor.setSpecialties(specialties);
        }

        doctor.setDepartment(
                departmentRepository.findById(dto.getDepartmentId())
                        .orElseThrow(()->new AppException(ErrorCode.DEPARTMENT_NOT_FOUND))
        );
        doctor.setPosition(
                positionRepository.findById(dto.getPositionId())
                        .orElseThrow(()-> new AppException(ErrorCode.POSITION_NOT_FOUND))
        );
        doctor.setTitle(
                titleRepository.findById(dto.getTitleId())
                        .orElseThrow(()-> new AppException(ErrorCode.TITLE_NOT_FOUND))
        );

        Doctor rsdoctor = doctorRepository.save(doctor);

        if(dto.getWorkingHours() != null){

            List<WorkingHour> workingHours = dto.getWorkingHours().stream().map(whDto->{
                WorkingHour wh = new WorkingHour();
                wh.setDoctor(rsdoctor);
                wh.setDayOfWeek(whDto.getDayOfWeek());
                wh.setStartTime(whDto.getStartTime());
                wh.setEndTime(whDto.getEndTime());
                wh.setIsAvailable(whDto.getIsAvailable());
                return wh;
            }).toList();
            workingHourRepository.saveAll(workingHours);
            rsdoctor.setWorkingHours(workingHours);
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
            rsdoctor.setEducation(educations);
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
            rsdoctor.setWorkExperience(experiences);
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
            rsdoctor.setAchievements(achievements);
        }


        return mapDoctor(rsdoctor);
    }

    @Transactional
    public DoctorResponseDto updateDoctor(Long doctorId, UpdateDoctorDto dto) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        doctor.setName(dto.getName());
        doctor.setAvatarUrl(dto.getAvatarUrl());
        doctor.setIntroduction(dto.getIntroduction());
        doctor.setExperienceYears(dto.getExperienceYears());

        Department department = departmentRepository.findById(dto.getDepartmentId()).orElseThrow(()-> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Position position = positionRepository.findById(dto.getPositionId()).orElseThrow(()->new AppException(ErrorCode.POSITION_NOT_FOUND));
        Title title = titleRepository.findById(dto.getTitleId()).orElseThrow(()->new AppException(ErrorCode.TITLE_NOT_FOUND));

        doctor.setDepartment(department);
        doctor.setPosition(position);
        doctor.setTitle(title);

        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());

        updateSpecialties(doctor, dto.getSpecialtyIds());
        updateWorkingHours(doctor, dto.getWorkingHours());
        updateEducation(doctor, dto.getEducation());
        updateExperience(doctor, dto.getWorkExperience());
        updateAchievements(doctor, dto.getAchievements());

        return mapDoctor(doctorRepository.save(doctor));
    }

    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::mapDoctor).collect(Collectors.toList());
    }

    public Page<DoctorResponseDto> getAllDoctors(Pageable pageable, String keyword) {
        Page<Doctor> doctorPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            doctorPage = doctorRepository.findAll(pageable);
        } else {
            doctorPage = doctorRepository.searchDoctors(keyword.trim(), pageable);
        }

        return doctorPage.map(this::mapDoctor);
    }


    public DoctorResponseDto getDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
        return mapDoctor(doctor);
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    private void updateSpecialties(Doctor doctor, List<Long> specialtyIds) {
        if (specialtyIds == null) {
            doctor.setSpecialties(Collections.emptyList());
            return;
        }
        // Giả sử có specialtyRepository để lấy entity
        List<Specialty> specialties = new ArrayList<>(specialtyRepository.findAllById(specialtyIds));
        doctor.setSpecialties(specialties);
    }
    private void updateWorkingHours(Doctor doctor, List<WorkingHourDTO> dtos) {
        if (dtos == null) {
            workingHourRepository.deleteByDoctorId(doctor.getId());
            return;
        }
        List<WorkingHour> currentList = workingHourRepository.findByDoctorId((doctor.getId()));
        Map<Integer, WorkingHour> mapCurrent = currentList.stream()
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
        // Xóa workingHours cũ không còn trong request
        workingHourRepository.deleteAll(mapCurrent.values());
        workingHourRepository.saveAll(updatedList);
    }

    private void updateEducation(Doctor doctor, List<EducationDTO> dtos) {
        if (dtos == null) {
            educationRepository.deleteByDoctorId(doctor.getId());
            return;
        }
        List<Education> currentList = educationRepository.findByDoctorId(doctor.getId());
        Map<Integer, Education> mapCurrent = currentList.stream()
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
        educationRepository.deleteAll(mapCurrent.values());
        educationRepository.saveAll(updatedList);
    }

    private void updateExperience(Doctor doctor, List<ExperienceDTO> dtos) {
        if (dtos == null) {
            experienceRepository.deleteByDoctorId(doctor.getId());
            return;
        }
        List<Experience> currentList = experienceRepository.findByDoctorId(doctor.getId());
        Map<Integer, Experience> mapCurrent = currentList.stream()
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
        Map<Integer, Achievement> mapCurrent = currentList.stream()
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


    private DoctorResponseDto mapDoctor(Doctor doctor){
        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .avatarUrl(doctor.getAvatarUrl())
                .introduction(doctor.getIntroduction())
                .experienceYears(doctor.getExperienceYears())
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
                        doctor.getSpecialties().stream().map(
                                specialty -> {
                                    SpecialtyDto sDto = new SpecialtyDto();
                                    sDto.setId(specialty.getId());
                                    sDto.setName(specialty.getName());
                                    sDto.setDescription(specialty.getDescription());
                                    sDto.setStatus(specialty.getStatus());
                                    return sDto;
                                }
                        ).collect(Collectors.toList())
                )
                .achievements(mapAchievement(doctor.getAchievements()))
                .education(mapEducation(doctor.getEducation()))
                .workExperience(mapExperience(doctor.getWorkExperience()))
                .workingHours(mapWorkingHour(doctor.getWorkingHours()))
                .build();
    }
    private List<AchievementDTO> mapAchievement(List<Achievement> achievements){
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
    private List<EducationDTO> mapEducation(List<Education> educations){
        educations.sort(Comparator.comparing(Education::getYear));
        return educations.stream().map(education -> {
            EducationDTO eDto = new EducationDTO();
            eDto.setId(education.getId());
            eDto.setDescription(education.getDescription());
            eDto.setYear(education.getYear());
            eDto.setDegree(education.getDegree());
            eDto.setInstitution(education.getInstitution());
            return eDto;
        }).collect(Collectors.toList());
    }
    private List<ExperienceDTO> mapExperience(List<Experience> experiences){
        experiences.sort(Comparator.comparing(Experience::getStartYear));
        return experiences.stream().map(experience -> {
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
    private List<WorkingHourDTO> mapWorkingHour(List<WorkingHour> workingHours){
        workingHours.sort(Comparator.comparing((WorkingHour wh) -> wh.getDayOfWeek().ordinal())
                .thenComparing(w-> LocalTime.parse(w.getStartTime())));
        return workingHours.stream().map(workingHour -> {
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

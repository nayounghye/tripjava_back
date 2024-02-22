package com.trip.tripjava.service;

import com.trip.tripjava.dto.PlanDTO;
import com.trip.tripjava.dto.TripRouteDTO;
import com.trip.tripjava.entity.PlannerEntity;
import com.trip.tripjava.entity.TodayPlanEntity;
import com.trip.tripjava.repository.PlannerRepository;
import com.trip.tripjava.repository.TodayPlanRepository;
import com.trip.tripjava.repository.TouristRepository;
import com.trip.tripjava.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TripRouteService {
    @Autowired
    PlannerRepository plannerRepository;

    @Autowired
    TodayPlanRepository todayPlanRepository;

    @Autowired
    TouristRepository touristRepository;

    @Autowired
    UserRepository userRepository;

    // 여행 경로 저장
    public String createTripRoute(TripRouteDTO tripRouteDTO) {
        PlannerEntity createPlanner = PlannerEntity.builder()
                .planner_startday(tripRouteDTO.getStart_day())
                .planner_endday(tripRouteDTO.getEnd_day())
                .planner_title(tripRouteDTO.getPlanner_title())
                .user(userRepository.findById(tripRouteDTO.getUserid()).get())
                .build();
        PlannerEntity planner = plannerRepository.save(createPlanner);

        for (PlanDTO plan : tripRouteDTO.getPlans()) {
            TodayPlanEntity todayPlan = TodayPlanEntity.builder()
                    .contentid(touristRepository.findById(plan.getContentid()).get())
                    .planner_no(planner)
                    .today_type(plan.getType())
                    .build();

            // 확인 로그
            TodayPlanEntity savedTodayPlan = todayPlanRepository.save(todayPlan);
            log.warn("저장됨 {}", savedTodayPlan.getToday_no());
        }

        return "여행경로가 저장되었습니다.";
    }

    // 여행 경로 확인
    public TripRouteDTO viewTripRoute(long plannerId) {
        // 플래너 ID를 기반으로 해당 플래너를 조회합니다.
        PlannerEntity plannerEntity = plannerRepository.findById(plannerId).orElse(null);
        if (plannerEntity == null) {
            log.error("해당 플래너를 찾을 수 없습니다.");
            return null;
        }

        // Calculate the number of days
        LocalDate startDate = LocalDate.parse(plannerEntity.getPlanner_startday());
        LocalDate endDate = LocalDate.parse(plannerEntity.getPlanner_endday());
        int days = Period.between(startDate, endDate).getDays()+1;

        List<TodayPlanEntity> todayPlanEntities = todayPlanRepository.findByPlannerNo(plannerEntity.getPlanner_no());

        List<PlanDTO> planDTOList = todayPlanEntities.stream().map(todayPlanEntity -> {
            PlanDTO planDTO = new PlanDTO();
            planDTO.setContentid(todayPlanEntity.getContentid().getContentid());
            planDTO.setType(todayPlanEntity.getToday_type());
            return planDTO;
        }).collect(Collectors.toList());


        TripRouteDTO tripRouteDTO = new TripRouteDTO();
        tripRouteDTO.setPlanner_title(plannerEntity.getPlanner_title());
        tripRouteDTO.setStart_day(plannerEntity.getPlanner_startday());
        tripRouteDTO.setEnd_day(plannerEntity.getPlanner_endday());
        tripRouteDTO.setDays(days);
        tripRouteDTO.setUserid(plannerEntity.getUser().getId());
        tripRouteDTO.setPlans(planDTOList);

        return tripRouteDTO;
    }
}

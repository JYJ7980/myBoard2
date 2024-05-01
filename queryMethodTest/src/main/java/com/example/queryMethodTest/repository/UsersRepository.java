package com.example.queryMethodTest.repository;

import com.example.queryMethodTest.Constant.Gender;
import com.example.queryMethodTest.entity.Users;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // 이름으로 검색
    List<Users> findByName(String name);

    // Pink 색상 데이터중 상위 3개 데이터만 가져오기
    List<Users> findTop3ByLikeColor(String color);

    // gender와 color로 테이블 검색
    List<Users> findByGenderAndLikeColor(Gender gender, String color);

    // 범위로 검색(After, Before) --- 날짜/시간 데이터에 한정
    List<Users> findByCreatedAtAfter(LocalDateTime searchDate);

    // Sort 별도 처리하는 법
    // Orange 색상 검색해서 Gender 오름차순, CreatedAt 내림차순
    List<Users> findByLikeColor(String color, Sort sort);

    // 페이징 처리
    // 주어진 아이디보다 큰 데이터를 내림차순으로 검색한 후 페이징 처리
    // (id=200, 5번째 페이지, 한페이지 당 10개씩)
    Page<Users> findByIdGreaterThanEqualOrderByIdDesc(Long id, Pageable paging);

    // 문제 1. 여성의 이름 중 "w" 또는 "m"을 포함하는 자료를 검색하시오
    List<Users> findByNameContainsAndGenderOrNameContainsAndGender(String name1,Gender gender1,String name2, Gender gender2);

    // 문제 2. 이메일에 net을 포함하는 데이터 건수를 출력하시오
    List<Users> findByEmailContains(String email);

    // 문제 3. 가장 최근 한달이내에 업데이트된 자료 중 이름 첫자가 "J"인 자료를 출력하시오.
    List<Users> findByUpdatedAtAfterAndNameStartingWith(LocalDateTime searchDate,String name);

    // 문제 4. 가장 최근 생성된 자료 10건을 ID, 이름, 성별, 생성일 만 출력하시오.
    List<Users> findTop10ByCreatedAtBefore(LocalDateTime dateTime, Sort sort);

    // 문제 5. "Red"를 좋아하는 남성 이메일 계정 중 사이트를 제외한 계정만 출력하시오.
    List<Users> findByLikeColorAndGender(String color, Gender gender);

    // 문제 6. 갱신일이 생성일 이전인 잘못된 데이터를 출력하시오.
//    List<Users> findByUpdatedAtBeforeIn(List<LocalDateTime> createdAt);

    // 문제 7. 이메일에 edu를 갖는 여성 데이터를 가장 최근 데이터부터 보이도록 출력하시오.
    List<Users> findByGenderAndEmailContainsOrderByCreatedAtDesc(Gender gender, String email);

    // 문제 8. 좋아하는 색상별로 오름차순 정렬하고 같은 색상 데이터는 이름의 내림차순으로 출력하시오.
    List<Users> findAll(Sort sort);

    // 문제 9. 전체 자료를 가장 최근 입력한 자료 순으로 정렬 및 페이징 처리하고 한 페이지당 10건씩 출력하되, 그 중 1번째 페이지를 출력하시오.

    // 문제10. 남성 자료를 ID의 내림차순으로 정렬한 후 한페이당 3건을 출력하되 그 중 2번째 페이지 자료를  출력하시오.
    Page<Users> findByGenderOrderByIdDesc(Gender gender, Pageable pageable);

    // 문제11. 지난달의 모든 자료를 검색하여 출력하시오.
    List<Users> findByCreatedAtBetween(LocalDateTime start, LocalDateTime last);
}

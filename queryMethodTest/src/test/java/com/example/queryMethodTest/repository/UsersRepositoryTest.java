package com.example.queryMethodTest.repository;

import com.example.queryMethodTest.Constant.Gender;
import com.example.queryMethodTest.entity.Users;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;

    @Test
    void findByName테스트(){
        String findName = "Towney";
        usersRepository.findByName(findName).forEach(users -> System.out.println(users));
    }

    // 문제 1. 여성의 이름 중 "w" 또는 "m"을 포함하는 자료를 검색하시오
    @Test
    void question1(){
        usersRepository
                .findByNameContainsAndGenderOrNameContainsAndGender("m", Gender.Female,"w",Gender.Female)
                .forEach(users -> System.out.println(users));
    }

    // 문제 2. 이메일에 net을 포함하는 데이터 건수를 출력하시오
    @Test
    void question2(){
        System.out.println("count : "+usersRepository.findByEmailContains("net").stream().count());
    }

    // 문제 3. 가장 최근 한달이내에 업데이트된 자료 중 이름 첫자가 "J"인 자료를 출력하시오.
    @Test
    void question3(){
        usersRepository
                .findByUpdatedAtAfterAndNameStartingWith(LocalDateTime.now().minusMonths(1L),"J")
                .forEach(users -> System.out.println(users));
    }

    // 문제 4. 가장 최근 생성된 자료 10건을 ID, 이름, 성별, 생성일 만 출력하시오.
    @Test
    void question4(){
        usersRepository
                .findTop10ByCreatedAtBefore(LocalDateTime.now(), Sort.by(Sort.Order.desc("createdAt")))
                .forEach(users -> {
                    System.out.print("Id : "+users.getId()+" ");
                    System.out.print("Name : "+users.getName()+" ");
                    System.out.print("Gender : "+users.getGender()+" ");
                    System.out.print("Created_at : "+users.getCreatedAt()+" ");
                    System.out.println();
                });
    }

    // 문제 5. "Red"를 좋아하는 남성 이메일 계정 중 사이트를 제외한 계정만 출력하시오.
    @Test
    void question5(){
        usersRepository
                .findByLikeColorAndGender("Red", Gender.Male)
                .forEach(users -> System.out.println("Email 계정 : "+users.getEmail().substring(0,users.getEmail().indexOf("@"))));
    }

    // 문제 6. 갱신일이 생성일 이전인 잘못된 데이터를 출력하시오.
    @Test
    void question6(){
        List<Users> users = usersRepository.findAll();
        for (Users user : users) {
            if (user.getUpdatedAt().isBefore(user.getCreatedAt())) {
                System.out.println(user);
            }
        }
    }

    // 문제 7. 이메일에 edu를 갖는 여성 데이터를 가장 최근 데이터부터 보이도록 출력하시오.
    @Test
    void question7(){
        usersRepository
                .findByGenderAndEmailContainsOrderByCreatedAtDesc(Gender.Female,"edu")
                .forEach(users -> System.out.println(users));
    }
    // 문제 8. 좋아하는 색상별로 오름차순 정렬하고 같은 색상 데이터는 이름의 내림차순으로 출력하시오.
    @Test
    void question8(){
        usersRepository
                .findAll(Sort.by(Sort.Order.asc("likeColor"),Sort.Order.desc("name")))
                .forEach(users -> System.out.println(users));
    }

    // 문제 9. 전체 자료를 가장 최근 입력한 자료 순으로 정렬 및 페이징 처리하고 한 페이지당 10건씩 출력하되, 그 중 1번째 페이지를 출력하시오.
    @Test
    void question9(){
        usersRepository
                .findAll(PageRequest.of(0,10,Sort.by(Sort.Order.desc("createdAt"))))
                .getContent().forEach(users -> System.out.println(users));
    }

    // 문제10. 남성 자료를 ID의 내림차순으로 정렬한 후 한페이당 3건을 출력하되 그 중 2번째 페이지 자료를  출력하시오.
    @Test
    void question10(){
        Pageable pageable = PageRequest.of(1, 3);
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male, pageable);
        result.getContent().forEach(users -> System.out.println(users));

    }

    // 문제11. 지난달의 모든 자료를 검색하여 출력하시오.
    @Test
    void question11(){
        // 기준일
        LocalDateTime baseDate = LocalDateTime.now().minusMonths(1L);
        // 시작일
        LocalDateTime startDate = baseDate.withDayOfMonth(1);
        // 종료일
        LocalDateTime lastDate = baseDate.withDayOfMonth(baseDate.toLocalDate().lengthOfMonth());
        usersRepository.findByCreatedAtBetween(startDate, lastDate).forEach(users -> System.out.println(users));
    }



}
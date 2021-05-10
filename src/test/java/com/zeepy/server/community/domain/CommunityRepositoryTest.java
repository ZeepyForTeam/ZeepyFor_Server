package com.zeepy.server.community.domain;

import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommunityDomain_테스트_클래스")
@RunWith(SpringRunner.class)
@DataJpaTest
public class CommunityRepositoryTest {
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    CommunityLikeRepository communityLikeRepository;

    @DisplayName("커뮤니티 저장하기 테스트")
    @Test
    public void saveCommunity(){
        //given
        communityRepository.save(Community.builder()
                .communityCategory(CommunityCategory.FREESHARING)
                .productName("무료나눔물건")
                .productPrice(100000)
                .sharingMethod("만나서")
                .targetNumberOfPeople(3)
                .targetAmount(100000)
                .title("asadasda")
                .content("assssssss")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build());

        //when
        List<Community> result = communityRepository.findAll();

        //then
        Community communities = result.get(0);
        assertThat(communities.getTitle()).isEqualTo("asadasda");
        assertThat(communities.getProductName()).isEqualTo("무료나눔물건");
    }
}

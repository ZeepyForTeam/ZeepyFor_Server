package com.zeepy.server.community.domain;

import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.repository.CommunityLikeRepository;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    ParticipationRepository participationRepository;

    @AfterEach
    public void reset() {
        communityRepository.deleteAll();
        userRepository.deleteAll();
        participationRepository.deleteAll();
    }


    @DisplayName("커뮤니티 저장하기 테스트")
    @Test
    public void saveCommunity() {
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

    @DisplayName("참여하기 저장 테스트")
    @Test
    public void joinCommunity() {
        //given
        User user = userRepository.save(User.builder().name("작성자").build());
        Community saveCommunity = communityRepository.save(neighborhoodfriend(user));
        User joinUser = userRepository.save(User.builder().name("참여자").build());

        //when
        ParticipationDto participationDto = new ParticipationDto(saveCommunity, joinUser);
        Participation participation = participationRepository.save(participationDto.toEntity());

        //then
        assertThat(participation.getCommunity().getId()).isEqualTo(saveCommunity.getId());
        assertThat(participation.getUser().getId()).isEqualTo(joinUser.getId());
        assertThat(participation.getUser().getName()).isEqualTo("참여자");
    }

    @DisplayName("MyZip참여리스트 불러오기 테스트")
    @Test
    public void getZipList() {
        //given
        User writer = userRepository.save(User.builder().name("작성자2").build());
        User joinUser = userRepository.save(User.builder().name("참여자2").build());
        Community community = communityRepository.save(jointpurchaseEntity(writer));

        ParticipationDto requestDto = new ParticipationDto(community, joinUser);
        Participation participation = participationRepository.save(requestDto.toEntity());

        //when
        List<Participation> findParticipationList = participationRepository.findAllByUserId(joinUser.getId());

        //then
        assertThat(findParticipationList.get(0).getUser().getId()).isEqualTo(joinUser.getId());
        assertThat(findParticipationList.get(0).getUser().getName()).isEqualTo(joinUser.getName());
        assertThat(findParticipationList.get(0).getCommunity().getProductName()).isEqualTo(community.getProductName());
        assertThat(findParticipationList.get(0)).isEqualTo(participation);
    }

    public Community jointpurchaseEntity(User user) {
        return Community.builder()
                .communityCategory(CommunityCategory.JOINTPURCHASE)
                .productName("공동구매물건")
                .productPrice(10000)
                .sharingMethod("만나서")
                .targetNumberOfPeople(2)
                .targetAmount(10000)
                .user(user)
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }

    public Community freesharingEntity(User user) {
        return Community.builder()
                .communityCategory(CommunityCategory.FREESHARING)
                .productName("공동구매물건")
                .sharingMethod("만나서")
                .user(user)
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }

    public Community neighborhoodfriend(User user) {
        return Community.builder()
                .communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
                .user(user)
                .targetAmount(10000)
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }
}

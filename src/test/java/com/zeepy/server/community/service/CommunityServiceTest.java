package com.zeepy.server.community.service;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundParticipationException;
import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityCategory;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.community.dto.JoinCommunityRequestDto;
import com.zeepy.server.community.repository.CommunityRepository;
import com.zeepy.server.community.repository.ParticipationRepository;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("커뮤니티_서비스_테스트")
@ExtendWith(MockitoExtension.class)
public class CommunityServiceTest {
    @InjectMocks
    private CommunityService communityService;
    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private UserRepository userRepository;

    @DisplayName("참여하기_서비스로직_테스트")
    @Test
    public void joinCommunity() {
        //given
        long communityId = 1L;
        JoinCommunityRequestDto requestDto = new JoinCommunityRequestDto(null, 2L);

        Community community = createCommunity();
        User user = createJoinUser();
        Participation participation = createParticipation(community, user);

        when(communityRepository.findById(any(Long.class))).thenReturn(Optional.of(community));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(participationRepository.save(any(Participation.class))).thenReturn(participation);
        when(participationRepository.findById(any(Long.class))).thenReturn(Optional.of(participation));

        //when
        Long participationId = communityService.joinCommunity(communityId, requestDto);

        //then
        Participation newParticipation = participationRepository.findById(participationId).orElseThrow(NotFoundParticipationException::new);

        assertThat(newParticipation.getCommunity().getId()).isEqualTo(community.getId());
        assertThat(newParticipation.getUser().getId()).isEqualTo(user.getId());
    }

    public Community createCommunity() {
        return Community.builder()
                .id(1L)
                .communityCategory(CommunityCategory.JOINTPURCHASE)
                .productName("공동구매물건")
                .productPrice(10000)
                .sharingMethod("만나서")
                .targetNumberOfPeople(2)
                .targetAmount(10000)
                .user(User.builder().name("작성자").build())
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }

    public User createJoinUser() {
        return User.builder()
                .id(1L)
                .name("참여자")
                .build();
    }

    public Participation createParticipation(Community community, User user) {
        return Participation.builder()
                .id(1L)
                .community(community)
                .user(user)
                .build();
    }
}

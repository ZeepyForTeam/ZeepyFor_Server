package com.zeepy.server.community.domain;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundCommunityException;
import com.zeepy.server.community.dto.CommentDto;
import com.zeepy.server.community.dto.ParticipationDto;
import com.zeepy.server.community.repository.CommentRepository;
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
    @Autowired
    CommentRepository commentRepository;

    @AfterEach
    public void reset() {
        communityRepository.deleteAll();
        userRepository.deleteAll();
        participationRepository.deleteAll();
        commentRepository.deleteAll();
    }


    @DisplayName("커뮤니티 저장하기 테스트")
    @Test
    public void saveCommunity() {
        //given
        communityRepository.save(Community.builder()
                .communityCategory(CommunityCategory.FREESHARING)
                .productName("무료나눔물건")
                .productPrice(100000)
                .purchasePlace("매장")
                .sharingMethod("만나서")
                .targetNumberOfPeople(3)
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

    @DisplayName("댓글달기 테스트")
    @Test
    public void setComment() {
        //given
        User writer = User.builder().id(1L).name("작성자").build();
        User user = User.builder().id(2L).name("사용자2").build();
        User user2 = User.builder().id(3L).name("사용자2").build();

        Community community = jointpurchaseEntity(writer);

        Comment subComment1 = Comment.builder()
                .comment("댓글1")
                .user(user)
                .build();
        subComment1.setSuperComment(null);
        subComment1.setCommunity(community);

        Comment subComment2 = Comment.builder()
                .comment("댓글2")
                .user(user2)
                .build();
        subComment2.setSuperComment(null);
        subComment2.setCommunity(community);

        Comment subSubComment = Comment.builder()
                .comment("대댓글")
                .user(writer)
                .build();
        subSubComment.setSuperComment(subComment1);
        subSubComment.setCommunity(community);

        //when

        Comment saveSubComment1 = commentRepository.save(subComment1);

        Comment saveSubComment2 = commentRepository.save(subComment2);

        Comment saveSubSubComment = commentRepository.save(subSubComment);

        //then
        List<Comment> saveSubComment1sSubComments = saveSubComment1.getSubComments();
        Integer comment1sSubCommentsSize = saveSubComment1sSubComments.size();
        Comment comment1sSubComment = saveSubComment1sSubComments.get(0);
        assertThat(comment1sSubCommentsSize).isEqualTo(1);
        assertThat(comment1sSubComment).isEqualTo(saveSubSubComment);

        Comment comment1sSuperComment = saveSubComment1.getSuperComment();
        Community comment1sCommunity = saveSubComment1.getCommunity();
        assertThat(comment1sSuperComment).isNull();
        assertThat(comment1sCommunity).isEqualTo(community);

        List<Comment> communitysComments = community.getComments();
        Integer communitysCommentsSzie = communitysComments.size();
        Comment communitysComment = communitysComments.get(1);
        assertThat(communitysCommentsSzie).isEqualTo(2);
        assertThat(communitysComment).isEqualTo(saveSubComment2);

        Comment subSubCommentsSuperComment = saveSubSubComment.getSuperComment();
        Integer subSubCommentsSubCommentSize = saveSubSubComment.getSubComments().size();
        assertThat(subSubCommentsSuperComment).isEqualTo(saveSubComment1);
        assertThat(subSubCommentsSubCommentSize).isEqualTo(0);
    }

    @DisplayName("community의 commnets 테스트")
    @Test
    public void setSubComment() {
        //given
        //사용자3명(작성자, 사용자1, 사용자2)
        User writer = User.builder().id(1L).name("작성자").place("구월동").build();
        User user1 = User.builder().id(2L).name("사용자2").place("구월동").build();
        User user2 = User.builder().id(3L).name("사용자2").place("구월동").build();
        userRepository.save(writer);
        userRepository.save(user1);
        userRepository.save(user2);
        //커뮤니티1개
        Community community = jointpurchaseEntity(writer);
        //when
        Community saveCommunity = communityRepository.save(community);
        //사용자1,사용자2 참여
        ParticipationDto user1ParticipationDto = new ParticipationDto(saveCommunity, user1);
        Participation user1Participation = user1ParticipationDto.toEntity();
        participationRepository.save(user1Participation);
        CommentDto user1CommentDto = CommentDto.builder().comment("댓글1").writer(user1).superComment(null).community(saveCommunity).build();
        Comment user1Comment = user1CommentDto.toEntity();
        commentRepository.save(user1Comment);
        Community findCommunity1 = communityRepository.findById(1L).orElseThrow(NotFoundCommunityException::new);

        ParticipationDto user2ParticipationDto = new ParticipationDto(saveCommunity, user2);
        Participation user2Participation = user2ParticipationDto.toEntity();
        participationRepository.save(user2Participation);
        CommentDto user2CommentDto = CommentDto.builder().comment("댓글2").writer(user2).superComment(null).community(saveCommunity).build();
        Comment user2Comment = user2CommentDto.toEntity();
        commentRepository.save(user2Comment);
        Community findCommunity2 = communityRepository.findById(1L).orElseThrow(NotFoundCommunityException::new);

        //작성자가 사용자1 댓글에 대댓글
        CommentDto subCommentDto = CommentDto.builder().comment("대댓글").superComment(user1Comment).community(saveCommunity).writer(writer).build();
        commentRepository.save(subCommentDto.toEntity());
        Community findCommunity3 = communityRepository.findById(1L).orElseThrow(NotFoundCommunityException::new);

        //then
        //모든 영속성이 끝나고 community의 comments에는 대댓글이 포함되어있을까?
        assertThat(saveCommunity.getComments().size()).isEqualTo(2);
        //findById로 찾을때
        assertThat(findCommunity1.getComments().size()).isEqualTo(1);
        assertThat(findCommunity2.getComments().size()).isEqualTo(2);
        assertThat(findCommunity3.getComments().size()).isEqualTo(3);
    }

    @DisplayName("community_currentNumberOfPeople_create Deafault 0")
    @Test
    public void defaultCurrentNumberOfPeople_zero() {
        User writer = User.builder().id(1L).name("작성자").build();
        userRepository.save(writer);
        Community community = jointpurchaseEntity(writer);
        Community saveCommunity = communityRepository.save(community);

        assertThat(saveCommunity.getCurrentNumberOfPeople()).isEqualTo(0);
    }

    public Community jointpurchaseEntity(User user) {
        return Community.builder()
                .id(1L)
                .communityCategory(CommunityCategory.JOINTPURCHASE)
                .productName("공동구매물건")
                .productPrice(10000)
                .purchasePlace("매장")
                .sharingMethod("만나서")
                .targetNumberOfPeople(2)
                .currentNumberOfPeople(0)
                .user(user)
                .title("같이 살사람")
                .content("구해요")
                .place("구월동")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }

    public Community freesharingEntity(User user) {
        return Community.builder()
                .id(2L)
                .communityCategory(CommunityCategory.FREESHARING)
                .user(user)
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }

    public Community neighborhoodfriend(User user) {
        return Community.builder()
                .id(3L)
                .communityCategory(CommunityCategory.NEIGHBORHOODFRIEND)
                .user(user)
                .title("같이 살사람")
                .content("구해요")
                .imageUrls(Arrays.asList("1", "2", "3"))
                .build();
    }
}
